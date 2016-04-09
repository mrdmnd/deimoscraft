---
layout: default
---
# Deimos' Advanced Unholy DK - Visual Guide

An up-to-date (as of April, 2016, patch 6.2.3) guide for intermediate/advanced Unholy Death Knight DPS players.

## About the Author

I'm a 24 year old statistican with a background in computer science and mathematics. This guide has some CS-inspired notation. Friendly warning.
I've played a DK main since Wrath of the Lich King. I'm currently the SimulationCraft Death Knight maintainer.
I raid with a bunch of Australians over on Aman'Thul, but live in California. Enough about me.

## Audience, assumptions, and goals

This is *not* an introductory guide. This is *not* a levelling guide. 
This is *not* a guide for that DK alt you play sometimes, but aren't really interested in min/maxing.

This *is* a guide designed for current upper-heroic to mythic level raiders with an interest in pushing progression,
fighting for rankings on WarcraftLogs, and mastering the nuances of an incredibly rewarding/punishing/delightful spec.

This document represents a collection of heuristics, guidelines, facts, and pure/applied math that should inform
DK decision making during raid encounters. I'll attempt to call out particularly complex corner cases as they crop up, 
but you should be aware that this guide represents an approach to DK that is essentially "no-holds-barred" in terms of
mental complexity. We'll expect you to think three-to-four GCDs ahead constantly, and know exactly what's going on with 
each raid encounter. The Unholy Spec is, at its core, a resource-juggling spec, and being able to maintain situational
awareness while tracking debuffs, timers, and three resource systems can be cognitively challenging, to say the least.

The approach this guide takes is that of the power-gamer:  we're going to add a fair bit of complexity to the basics 
for a very small reward. If that approach isn't for you, that's 100% reasonable - there are plenty of other guides that
do a fantastic job bringing you up to speed on the core of the gameplay. Nevertheless, if this style of play appeals to you,
I hope you'll find some useful additions to your own playstyle here.

With the notable exception of Vial of Convulsive Shadows, this guide assumes that you are playing an Unholy Death Knight in 
non-trivial Hellfire Citadel raid encounters, using Hellfire Citadel gear. We won't talk about gear or encounters from previous raid tiers. 
Tier 18's 4-set bonus is incredibly strong, and you should be using it already if you're reading this guide. In general, the content in this guide 
will not apply to fresh level 100 DKs - at the bare minimum, you'll need the T18 4p before this content is relevant for you.

## Unholy Playstyle General Notes

Before diving into the specifics, it's helpful to stop and take a moment to think about some general philosophy behind Unholy DKs.

## Notes on Variance

As a spec, in general, Unholy is one of the *least* RNG dependent specs in the game.

Our standard suite of trinkets in HFC has very minimal RNG associated with it:

  - [Discordant Chorus](http://www.wowhead.com/item=124237/discordant-chorus&bonus=567) - (functionally no variance, because it procs so frequently - PPM high enough to flatten out randomness)
  - [Reaper's Harvest](http://www.wowhead.com/item=124513/reapers-harvest&bonus=567) - (functionally no variance, increases NP damage by flat percentage, tick-count large enough to flatten out randomness)
  - [Unending Hunger](http://www.wowhead.com/item=124236/unending-hunger&bonus=567) - uptime variance throughout encounters
  - [Gronntooth Warhorn](http://www.wowhead.com/item=133595/gronntooth-war-horn) - uptime variance throughout encounters
  - [Vial of Convulsive Shadows](http://www.wowhead.com/item=113969/vial-of-convulsive-shadows&bonus=567) - (no variance at all with on use).
 
Only two (GWH and UEH) have any meaningful variance built into them. If talented with Blood Tap, the only non-deterministic mechanic left for us is Sudden Doom.

This low variance can be a Good Thing (capital G.T.) - it lets us be INCREDIBLY consistent on progression, which is generally desirable for strategy planning purposes.
It also means that, in non-corner cases (like 15 second Iron Reaver kills), you can fairly reliably distinguish yourself as a strong player purely through your execution, 
rather than having your performance be a combination of skill and proc luck.

On the other hand, this variance can also be a Bad Thing, because it means that our DPS ceiling is considerably
lower than classes like Arcane Mage, or Subtlety Rogue, with multiplicatively stacking RNG-based cooldowns. 
Indeed, this trend can be see in the data: 

Take a look at the Warcraft Logs data for 95th versus 75th percentile Mythic Fel Lord Zakuun damage at the 733-735 iLvl bracket, aggregated over two weeks on April 8th, 2016. 
I claim that these values represent the difference between progression raiders who play single target *very* well (or get *very* lucky) 
and progression raiders who play "pretty good" single target, or have above-average luck. 

While this sort of reasoning is prone to sampling bias and independent variable conflation, it seems reasonable that selecting the ilvl 733-735 
bracket reduces the likelihood that a parse came from a speed kill (which can massively inflate rankings), and increases the likelihood that it came from a progression kill, or early farm kill.

If you look at the spread between the 95th and the 75th percentile, you'll see that very strong/very lucky Unholy players 
(95th percentile) have a much smaller percentage gap (+14%) above "good" (75th percentile) Unholy players 
than strong/lucky Arcane Mages, Ret Paladins, and Arms Warriors at the same gear level have to their 75th percentile counterparts (+23%, +20%, +19%).

    | Class         | Spec          | DPS_95        | DPS_75     | Difference | Diff  | Parses |
    |---------------|---------------|---------------|------------|------------|-------|--------|
    | Rogue         | Assassination | 116,219.00    | 91,737.00  | 24,482.00  | 0.27  | 79     |
    | Mage          | Fire          | 156,810.30    | 123,808.80 | 33,001.50  | 0.27  | 82     |
    | Mage          | Arcane        | 141,287.70    | 114,824.50 | 26,463.20  | 0.23  | 1,056  |
    | Paladin       | Retribution   | 120,645.30    | 100,572.50 | 20,072.80  | 0.20  | 644    |
    | Warrior       | Arms          | 110,675.40    | 92,689.30  | 17,986.10  | 0.19  | 223    |
    | Hunter        | Beast Mastery | 100,050.00    | 84,798.60  | 15,251.40  | 0.18  | 16     |
    | Rogue         | Combat        | 93,864.50     | 79,674.30  | 14,190.20  | 0.18  | 51     |
    | Hunter        | Marksmanship  | 115,477.00    | 98,275.70  | 17,201.30  | 0.18  | 1,831  |
    | Warlock       | Destruction   | 110,011.30    | 93,738.40  | 16,272.90  | 0.17  | 74     |
    | Rogue         | Subtlety      | 144,096.10    | 122,988.00 | 21,108.10  | 0.17  | 656    |
    | Monk          | Windwalker    | 107,169.00    | 92,469.70  | 14,699.30  | 0.16  | 139    |
    | Warrior       | Fury          | 104,975.20    | 90,766.90  | 14,208.30  | 0.16  | 515    |
    | Warlock       | Affliction    | 143,148.00    | 123,846.00 | 19,302.00  | 0.16  | 812    |
    | Druid         | Balance       | 121,914.00    | 106,131.00 | 15,783.00  | 0.15  | 929    |
    | Death Knight  | Frost         | 103,794.10    | 90,373.50  | 13,420.60  | 0.15  | 326    |
    | Shaman        | Enhancement   | 109,439.00    | 95,739.30  | 13,699.70  | 0.14  | 285    |
    | Death Knight  | Unholy        | 113,806.20    | 99,611.80  | 14,194.40  | 0.14  | 706    |
    | Shaman        | Elemental     | 107,839.20    | 94,916.20  | 12,923.00  | 0.14  | 246    |
    | Priest        | Shadow        | 116,227.40    | 103,248.00 | 12,979.40  | 0.13  | 595    |
    | Druid         | Feral         | 111,634.40    | 99,360.70  | 12,273.70  | 0.12  | 162    |
    | Mage          | Frost         | 103,928.30    | 93,455.30  | 10,473.00  | 0.11  | 272    |
    | Warrior       | Gladiator     | 89,024.20     | 87,685.70  | 1,338.50   | 0.02  | 7      |
    | Warlock       | Demonology    | 90,082.00     | 89,484.50  | 597.50     | 0.01  | 5      |
    | Hunter        | Survival      | 0             | 0          | 0.00       | 0     | 0      |
    |               |               |               |            |            |       |        |
    | Mythic Zakuun | iLvl 733-735  | Sampled from: | 3/25/2016  | 4/8/2016   |       |        |

Punchline: the low amount of variance inherent to our spec means that Unholy is for players who like to plan ahead, and execute. 
For players who like to be in control.
For players who hate the feeling of relying on randomized procs as their core class mechanic.
If this isn't for you, you can go play ~~Frost DK~~ Arcane Mage and pray to RNGesus for Trinket/Missile procs.

## The State Machine Approach

At the end of the day, we can abstract how players play *any* DPS spec as a state machine. 
Consider the `state` **S** to be the "current state of the universe" for your character. Let **I** be any `input` in the set of potential inputs:
these are your DPS abilities, your cooldowns, everything you can command your character to do. Let **T** be a unit of `time`.
We'll call a tuple (**T**, **I**) an Action - you input **I** after waiting exactly **T** seconds.
These formal definitions might seem unnecessary on the surface, but they'll prove fairly useful notation for later discussion.

If you take away nothing else from this guide, let it be the following:

**Playing a DPS class at maximum effectiveness requires an understanding of the function IdentifyBestAction(s: State):  (t: Time, i: Input)**

We'll say that one more time for emphasis: the very best DPS players take their understanding of their current character state and use it to determine exactly how long to wait, and what ability to use next.
A very large portion of this document is dedicated to specifying exactly what the logic of the IdentifyBestAction function is, and why it is that way. 
This function encapsulates what is traditionally known as the "APL", or "Action Priority List."

Once we have the value (T, I), we can "step forward in time" with our state: imagine a function EvolveState(s: State, (T, I)) => S that takes the current state S, and an `action` (T, I)

### State

For DKs, our state is mostly specified by the following features:

  - Rune Status: For each rune, how much time left before it's recharged? Is it death? Is it depleted and not recharging?
  - Blood Charges: How many do we have? When does the stack expire?
  - Shadow Infusion Charges: How many do we have? When does the stack expire?
  - Runic Power: How much do we have?
  - DPS Cooldowns:
    - Empower Rune Weapon
    - Anti-Magic Shell
    - Outbreak
    - Plague Leech
    - Unholy Blight
    - Death and Decay
    - Soul Reaper (the rune-independent cooldown, which we will get back to)
    - Mind Freeze
    - Breath of Sindragosa
    - Summon Gargoyle
    - Army of the Dead
    - Racials
    - etc.
  - Target Debuffs:
    - Diseases Timers or
    - Necrotic Plague Timer + stacks
  - Number of targets present: should you be blood boiling?
  - Priority Target Time To Die: should we cast soulreaper for a haste buff?
  - Priority Target Time To SoulReaper Range: can we pre-cast soul-reaper?
  - Current time in combat
  - Amount of time left on the GCD
  
There are other factors that go into our notion of <state>, but these are the core ones to think about.

### Inputs
Well, this one is easy. We can literally enumerate the set of possibly abilities to use. Here's a quick table of each input.

# Tricks
Dark Sim shadow riposte on Iskar
Pop AMS during Aura of Oppression + Breath, move left and right
