---
layout: default
title: Comparing Stat Weights - SimC vs. AMR
---

# How do Stat Weights work? What sources can I trust?

There's been some back and forth lately between the Simulationcraft committers
and the Ask Mr Robot developers on  "the correct way" to evaluate gear. 
I'd like to take some words to describe the difference between the two approaches, and provide
historical and mathematical context on why BOTH of the approaches
present a viable method for computing stat weights. As always, the devil is in the
details - both sources need to have a rock-solid implementation of
combat mechanics to generate robust weights, and historically, this has
been an area with a number of implementation bugs. As a professional
software engineer, I have found that this is an area that is very difficult to get right,
and the best that both teams can hope for is continual progress in the
accuracy dimension.

# History and simple example

ScaryTentacledEndBoss drops a piece of gear drops for you after a series of nail-biting 1% wipes.
Is it an upgrade? How much of an upgrade?

The notion of "Stat Weights" arose some time around TBC to answer such questions. 
In *simple* cases (when the gear does not include non-linearities like random procs,
or other interactive effects like set tier bonuses), we can answer these
ranking questions by comparing the two pieces of gear by their primary and
secondary stats. We do this by assigning a "DPS contribution" to each
piece of gear that is the dot product of their stats, and a vector
(computed and recomputed frequently for each character) called their
"stat weights":

    DPS value of gear = <stats> * <stat weights>

This is the computation that Pawn is doing every time it's showing you which gear is stronger.

Example: suppose my stat weights for `<haste, mastery, versatility, crit>` were  `<1.29, 1.68, 1.14, 1.22>`

A helmet drops that has `<haste, mastery, versatility, crit> = <400, 600, 800, 500>`.

What is its DPS value? We apply the dot product (fancy phrasing for
"multiple each stat by its weight, then add those terms up.")

    <400, 600, 800, 500> * <1.29, 1.68, 1.14, 1.22> = 400*1.29 + 600*1.68 + 800*1.14 + 500*1.22 = 3046

Another helmet drops - this one's stats vector looks like this: `<200, 600, 300, 700>`. Is it an upgrade?

    <200, 600, 300, 700> * <1.29, 1.68, 1.14, 1.22> = 2462

Looks like a downgrade to me. Pass this piece off to someone else on your team.

Assuming you understand the value of having a set of stat weights, the
rest of this post will walk you through the process of arriving at some
weights.



# Mathematical Underpinnings

(Note to the interested reader - this section contains some calculus!
Don't be too scared, I think it's pretty approachable.)

The fundamental idea behind both Simulationcraft and Ask
Mr. Robot is the notion of "a DPS simulator." A simulator is a tool that
allows you to "play through" a huge number of combat iterations and see
what happens, without requiring you to resort to complicated excel
spreadsheets. 

Both AMR and SIMC provide reasonably-well implemented combat models for all DPS specs in the game.

At the time of writing, Simulationcraft is *not* a good tool to use for
any sort of research on healers (and a pretty bad tool for tanks). 
Though there is no reason that it could not be used for these in theory, in practice, we (the committers) haven't
spent time working on those tanking and healing models, and they are functionally not
useful for Legion simulation. 

As far as I know, Ask Mr. Robot's simulator *has* been tuned well to work for these roles.

At a high level, a "DPS Simulator" is a tool that takes a character
definition as an input (specifically, all of the things associated with
that character: gear, talents, artifact choices, etc) as well as a
"policy" - a piece of foundational code or description of "how to play".
In simulationcraft, this policy is called an APL (Action Priority List).
In Ask Mr. Robot's simulator, this policy is called a Rotation.

The simulator also takes an "encounter" as an argument - a script to
represent that mobs and timings and movement of a given encounter.
Simulationcraft provides a few basic defaults - Patchwork, Helter Skelter, Hectic Add Cleave,
etc. Ask Mr. Robot provides some excellent encounter scripts that represent
various styles of raid encounter. Both simulators allow end users to
customize these scripts, though in practice, hardly anyone does. In
particular, it might make sense to model a mythic 10+ keystone run as a
25 minute long encounter with AOE mixed with short downtime mixed with
boss mobs. In practice, I've yet to see such a script emerge from SimC users.

Putting it all together: we can model the simulator as a BLACK BOX EVALUATOR of a function that takes three arguments:

1) character (gear, talents, artifact setup, race, etc)

2) encounter (knobs and dials that control number of enemy mobs, their health, etc)

3) policy (a mapping from "state" to "action" that the simulator uses to control the character actor during the encounter)

    DPS( character, encounter, policy ) = XXX,XXX

For the rest of this article, we'll choose to hold encounter and policy
fixed - please note that modelling both of those components is just as important as character-level
choices, if not more so: history has shown that playing your characters as
close to the optimal policy as possible is *much* more significant for
gaining real DPS than the amount of DPS you can gain or lose from making
character-level decisions (what gear, what talents, etc).

    DPS( character | encounter, policy ) = XXX,XXX

Let's unpack those character level choices:

    DPS( gear_set, talents, artifact_ranks | encounter, policy ) = XXX,XXX

When computing "stat weights", we'll frequently hold `talent_set` and
`artifact_ranks` constant - the weights are assumed to be conditional on
these factors (for example, assassination rogues have two pretty
reasonable builds right now - a "poison" build and a "bleed" build -
both of these builds will have a different set of stat weights,
naturally.

So now we're down to modelling DPS as a function of gear, given fixed talents, artifact ranks,
encounter specifics, and APL/rotation/policy:

    DPS( gear_set | talents, artifact_ranks, encounter, policy  ) = XXX,XXX

# Computing stat weights


