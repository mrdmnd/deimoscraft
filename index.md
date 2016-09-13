---
layout: default
title: Blog Introduction
---
Musings from a SimC committer and melee DPS theorycrafter.

## About the Author
Deïmos/Synecdoche (Matt Redmond) is a 24 year old data scientist with a background in computer science and mathematics.
Towards the end of WoD, he worked actively as the SimulationCraft Death Knight engine maintainer.
In Legion, he switched mains to his rogue, and works on the Rogue module.
Deïmos has played various classes on-and-off since the Gates of Ahn'Qiraj opened on his original server, but enjoys melee DPS the most.
Currently, he lives in California, and races bicycles as his second hobby.
You can contact him most easily through [Ravenholdt Discord](https://discordapp.com/channels/124169701881282560/215503271068958722), or by PMing deimos#9681.

## Audience, assumptions, and goals

This is *not* an introductory guide to various classes - you can find those elsewhere.
This is *not* a set of levelling guides.
This is *not* a guide for that random alt you play sometimes, but aren't really interested in playing optimally.
This *is* a document designed for mythic level raiders with an interest in pushing progression, fighting for rankings, and mastering the nuances of DPS specs.

This document represents a collection of heuristics, guidelines, facts, and pure/applied napkin math that should inform decision making during raid encounters.
I'll attempt to identify interesting and complex corner cases as they crop up, and examine things in depth where possible.
The audience should be aware that this document represents an approach to DPS that is essentially "no-holds-barred" in terms of mental complexity.
We'll expect you to think three-to-four GCDs ahead constantly, and know exactly what's going on during each raid encounter.
Most DPS specs, at their core, are about resource-juggling, planning, and being able to maintain situational awareness while tracking debuffs and timers.
Playing to 99th percentile can be cognitively challenging, to say the least.

A common criticism of the wisdom gained from simulators is "no player can accurately play as robotically as the sim does!"
This statement is not true; players are certainly able to make the same decisions as a simulator, and cast their abilities at the same time.
We'll walk though various APLs and examine why each line is in place, and what lead the theorycrafters to reach the ordering they did.

The approach this guide takes is that of the power-gamer:  we're going to add a fair bit of complexity to the basics for a very small reward.
If that approach isn't for you, that's 100% reasonable - there are plenty of other guides that provide baseline instruction on the core of the gameplay. Nevertheless, if this style of play appeals to you, I hope you'll find some useful additions to your DPS toolkit here.

## The State Machine Approach

At the end of the day, we can abstract how players play *any* DPS spec as a state machine.
Let `state` **S** to be the "current status" for your character.
Let `input` **I** be any `input` in the set of all potential inputs: these are your DPS abilities, your cooldowns, everything you can command your character to do.
Note that the empty input ("wait") is valid - this corresponds to pooling or regenerating resources (mana, energy, whatever).
These formal definitions might seem unnecessary on the surface, but they'll prove fairly useful notation for later discussion.

If you take away nothing else from this guide, let it be the following:

**Playing a DPS class at maximum effectiveness requires an understanding of the function IdentifyBestAction(s: State) ==> Input**

We'll say that one more time for emphasis: the very best DPS players take their understanding of their current character state
and use it to determine exactly what action to input next.

A very large portion of this blog/site is dedicated to specifying exactly why the logic of each spec's IdentifyBestAction function is the way it is. 
This function encapsulates what is traditionally known as the "APL", or "Action Priority List."

[Test Link](curse_of_the_dreadblades.html)
