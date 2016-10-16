---
layout: default
title: Comparing Stat Weights - SimC vs. AMR
---

# How do Stat Weights work? What sources can I trust?

There's been some back and forth lately between the Simulationcraft committers
and the Ask Mr Robot developers on  "the correct way" to evaluate gear.
I'm writing this post to describe the difference between the two approaches, and provide
historical and mathematical context on why BOTH of the approaches
represent a viable method for computing stat weights. As always, the devil is in the
details - in order to generate meaningful stat weights, you need to have a rock-solid implementation of
combat mechanics, and historically, this has been the area in simcraft with the greatest number of implementation bugs. 
As a professional software engineer, I have found that this is something that is very difficult to get right, 
and the best that both teams can hope for is continual improvements to their accuracy.

# History and simple example

ScaryTentacleEndBoss drops a piece of gear after a series of nail-biting 1% wipes.
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

This is the computation done by the Pawn addon each time you use it to compare gear.

Example: suppose my stat weights for `<haste_weight, mastery_weight, versatility_weight, crit_weight>` are `<1.29, 1.68, 1.14, 1.22>`

A helmet drops that has `<haste, mastery, versatility, crit> = <400, 600, 800, 500>`.

What is its DPS value? We apply the dot product (fancy phrasing for
"multiple each stat by its weight, then add those terms up.")

    <400, 600, 800, 500> * <1.29, 1.68, 1.14, 1.22> = 400*1.29 + 600*1.68 + 800*1.14 + 500*1.22 = 3046

Another helmet drops - this one's stats vector looks like this: `<200, 600, 300, 700>`. Is it an upgrade?

    <200, 600, 300, 700> * <1.29, 1.68, 1.14, 1.22> = 2462

Looks like a downgrade. Pass this piece off to someone else on your team.

Assuming you understand the value of having a set of stat weights, the
rest of this post will walk you through the process of generating them.


# Mathematical Underpinnings

(Note to the interested reader - this section contains some calculus!
Don't be too scared, I think it's pretty approachable.)

The fundamental idea behind both Simulationcraft and Ask
Mr. Robot is the notion of "a combat simulator." A simulator is a tool that
allows you to "play through" a huge number of combat iterations and see
what happens, without requiring you to resort to complicated excel
spreadsheets. 

Both AMR and SIMC provide reasonably-well implemented combat models for all DPS specs in the game.

At the time of writing, Simulationcraft is *not* a good tool to use for
any sort of research on healers (and a pretty mediocre for tank
simulation). Though there is no reason that it could not be used for these in theory, 
in practice, we (the committers) haven't spent time working on those tanking and healing
models, and they are not functionally useful for Legion simulation.

As far as I know, Ask Mr. Robot's simulator *has* been tuned well to work for these roles.

At a high level, a "combat Simulator" is a tool that takes a character
definition as an input (specifically, all of the things associated with
that character: gear, talents, artifact choices, etc) as well as a
"policy" - a piece of code/logic acting as a description of "how to play".
In simulationcraft, this policy is called an APL (Action Priority List).
In Ask Mr. Robot's simulator, this policy is called a Rotation.
The policy representation chosen by both tools is a decision tree: the
simulator evaluates the next action to take by walking through the decision tree
and selecting the first action whose conditions are entirely satisfied
by the current simulation state (buffs up, procs, cooldown is ready,
etc).

These simulators also take an "encounter" as an argument - a script to
represent mobs and timings and movement for a given encounter.

Simulationcraft provides a few basic defaults - Patchwork, Helter Skelter, Hectic Add Cleave,
etc. Ask Mr. Robot provides some excellent encounter scripts that represent
various styles of raid encounter. Both simulators allow end users to
customize these scripts, though in practice, hardly anyone does. In
particular, it might make sense to model a mythic 10+ keystone run as a
25 minute long encounter with AOE mixed with short downtime mixed with
boss mobs. In practice, I've yet to see such a script emerge from users.

Putting it all together: we can model the simulator as a BLACK BOX EVALUATOR of a function that takes three arguments:

1) character (gear, talents, artifact setup, race, etc)

2) encounter (knobs and dials that control number of enemy mobs, their health, etc)

3) policy (a logical mapping from "state" to "action" that the simulator uses to control the character during the encounter)

    DPS( character, encounter, policy ) = XXX,XXX

For the rest of this post, we'll choose to hold encounter and policy
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
reasonable talent builds right now - a "poison" build and a "bleed" build -
both of these builds have a different set of stat weights, naturally.

So now we're down to modelling DPS as a function of gear, given fixed talents, artifact ranks,
encounter specifics, and APL/rotation/policy:

    DPS( gear_set | talents, artifact_ranks, encounter, policy  ) = XXX,XXX

# Computing stat weights

Aside from non-linearities (trinket procs, enchants, set bonuses, one-off static effects),
we can model a gear set as the sum of its primary and secondary
statistics. For example, a gear set might have stats like

    # Gear Summary
    # gear_agility=11083
    # gear_crit_rating=6220
    # gear_haste_rating=2416
    # gear_mastery_rating=5871
    # gear_versatility_rating=2251

Again, aside from non-linearities, we can further break down our DPS
function above:

    DPS( primary_stat, crit, haste, mastery, versatility ) = XXX,XXX

To compute "stat weights", we need to talk about optimization. The goal
of this *ENTIRE ENDEAVOUR* is to identify the gearset that maximizes
your dps for given talents, artifact ranks, encounters, and policies. 


Here's how SimulationCraft approaches this problem:
## Simulationcraft's Approach to Stat Weights
In a nutshell, Simulationcraft uses the ["Finite Difference Method"](https://en.wikipedia.org/wiki/Finite_difference_method) to compute stat weights:

You can see the code [here](https://github.com/simulationcraft/simc/blob/legion-dev/engine/sim/sc_scaling.cpp).

SimC takes your input gearset and computes its gear summary (see above).

It runs a number of iterations on that exact gearset. It spits out a DPS
value, and an error bound. 

    SimC_DPS( gearset_summary ) = DPS_BASE

Then, for each stat you want to compute weights over, it "perturbs"
those stats by a delta (both up, and down, given the use of the
"centered" option). Right now, the deltas default to 1333 points.

Effectively, simulationcraft represent stat weights as the partial
derivative of the DPS function with regard to each stat (this is known
as "The Jacobian".

## Ask Mr. Robot's Approach to Stat Weights
AMR takes a slightly different approach - the fundamental difference is how
many points they sample.

AMR computes stat weights by considering a region of potential gear
sets in the neighborhood of your current gear set (the size of this
region can be controlled). It samples points (each point is a distinct
set of gear) from this region and simulates each of these, coming up
with a DPS value for each point.

This results in a sequence of equations:

    AMR_DPS ( sampled_gearset_1 ) = XXX,XXX
    AMR_DPS ( sampled_gearset_2 ) = XXX,XXX
    AMR_DPS ( sampled_gearset_3 ) = XXX,XXX
    AMR_DPS ( sampled_gearset_4 ) = XXX,XXX
    ...                           = ...

The next part is not 100% clear to me, as the exact model is closed
source, but after speaking with the implementors, it seems to be the
case that they expand this DPS function and approximate it as a linear
model:

    AMR_DPS_predicted( gearset ) = <weights> * <gearset_stats>

This results in an overconstrained set of equations, which can be
solved with the [standard method](https://en.wikipedia.org/wiki/Linear_least_squares_(mathematics)) (least squares minimization) to identify
the optimal set of weights that explains the most variance over the
gearsets sampled from that region.

    <weights> * <gearset_1> = Predicted_DPS_1
    <weights> * <gearset_2> = Predicted_DPS_2
    ....

# Takeaways
The Simcraft method approximates stat weights at a *single* point in
"gearset" space by numerically approximating the Jacobian of the DPS
function with central finite difference methods.

The AMR method approximates stat weights by finding the vector that
optimally captures the DPS for a *large* number of points in "gearset"
space surrounding your current gear set. The size of this search region
can be controlled by constraining what gear you'd like to make available
to your character.

In my opinion, the Simulationcraft default method is a reasonable tool
to use for "point in time" stat weights, but the AMR default method is a
tool that will (naturally) generalize better (perhaps at the cost of
exact, local point-in-time accuracy). Because the AMR tool can be told
to search "gearset" points that are arbitrarily close to your current
point, it strikes me as a *very* powerful tool that is able to act as a
superset of the SimC stat weight scaling tool.

Contradictions in weights between the tools can come from many places.
It is possible that both tools have bugs, which could be responsible for generating such contradictions.
It is also possible that the computation process differences (described
above) are also responsible.

SimC has been known to be subject to "scaling noise" where the delta
chosen is too big (or too small) to capture true partial derivative
information. If you really want to use the simulationcraft scaling
correctly, you should be using the "plotting" functionality, which will
consider a great deal more than just `stat` plus or minus delta.

