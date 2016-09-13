---
layout: default
title: Planning around Curse of the Dreadblades
---

# A Nuanced Guide to Curse of the Dreadblades

The artifact ability for the Outlaw Rogue is called Curse of the Dreadblades (COTD): for 12 seconds, your Pistol Shot (PS) and Saber Slash (SS) fill your combo points to maximum.
Naturally, when you hit this ability, your goal is to alternate builder -> finisher -> builder -> finisher, etc. It changes your rotation quite a bit.
The plan, of course, is that we want to fit as many buffed builders into the COTD window as possible, because their relative value is inflated massively.

## Basics

First, some basics: without Adrenaline Rush (AR) active, our GCD is 1.0 seconds. With Adrenaline Rush up, the GCD is reduced to 0.8 seconds.
That means that we'll fit 12 GCDs into COTD without AR, and 15 GCDs with it (if it's active for the whole duration, like on the pull).

Saber Slash (SS) costs 50 energy at all times; Pistol Shot (PS) costs 40 energy if you don't have an opportunity proc, and zero energy if you do.

For now, our goal is to get as many buffed builders as possible into that COTD window. Let's look at the energy cost for our builders and finishers.
With the appropriate points in your artifact, finishers will cost 12 energy less.
Relics can further decrease their energy cost (we'll revisit that case later), but for now, let's assume a 12 energy cost reduction.
This means that you will have Roll the Bones (RTB) cost you 13 energy, and Run Through (RT) cost you 23 energy.

You want to enter the COTD window immediately after casting a finisher, because you want to start at low combo points to maximize the relative gain.

## COTD Window Sequencing and Energy Demands
We'll call the zero-cost PS `fPS` for "free" PS.

With AR down, we have twelve globals in the COTD window, but we're really only concerned about fitting in six builders and five finishers - 
it's okay for the last finisher to end up outside the window, because there's no special damage bonus; just a combo-point boost for builders.

All else equal, we prefer the sequences that have more Saber Slashes than Pistol Shots, because Saber Slash does more base damage than Pistol Shot does.
We only want to incorporate Pistol Shot into our COTD window if we will be unable to have enough energy to get six finishers out (seven, with AR).

With respect to finishers: if you have no beneficial damage procs up, "a combo point is a combo point", and it makes no difference if you spend CP on 
Run Through or Roll the Bones (RTB is a bit cheaper, energy-wise, which makes a small difference.)

Heuristically (NOT validated with simulation), I'd imagine that it's a DPS loss to use combo points for Roll the Bones during the opener, when you are likely to have trinket procs and a pre-pot up.
During Curse of the Dreadblade windows outside of the opener (or big damage procs), it's fine to case Roll the Bones. 

### Full Case Analysis
Let `p = `probability of a saber slash proc.
`p = 0.35` by default. With Swordmaster talent, `p += 0.10`. With Jolly Roger buff, `p += 0.40`.

The average case is a bit more complicated: here are all possible sequences, and their energy costs, along with their probabilities.
Assume that each builder in this sequence is alternated with a finisher (omitted here for space).

### Without AR
No free pistol shots: Energy cost is 415.

`SS,SS,SS,SS,SS,SS` - Probability = (1-p)(1-p)(1-p)(1-p)(1-p)

Exactly one free pistol shot: Energy cost is 365.

`SS,fPS,SS,SS,SS,SS` - Probability = (p)(1-p)(1-p)(1-p) - (first SS procs, second, third, and fourth do not proc)

`SS,SS,fPS,SS,SS,SS` - Probability = (1-p)(p)(1-p)(1-p) - (first SS fails to proc, second procs, third and fourth do not proc)

`SS,SS,SS,fPS,SS,SS` - Probability = (1-p)(1-p)(p)(1-p) ...

`SS,SS,SS,SS,fPS,SS` - Probability = (1-p)(1-p)(1-p)(p) ...

`SS,SS,SS,SS,SS,fPS` - Probability = (1-p)(1-p)(1-p)(1-p)(p) - (first four SSs fail to proc, fifth SS procs)

Two free pistol shots: Energy cost is 315.

`SS,fPS,SS,fPS,SS,SS` - Probability = (p)(p)(1-p)

`SS,fPS,SS,SS,fPS,SS` - Probability = (p)(1-p)(p)

`SS,fPS,SS,SS,SS,fPS` - Probability = (p)(1-p)(1-p)(p)

`SS,SS,fPS,SS,fPS,SS` - Probability = (1-p)(p)(p)

`SS,SS,fPS,SS,SS,fPS` - Probability = (1-p)(p)(1-p)(p)

`SS,SS,SS,fPS,SS,fPS` - Probability = (1-p)(1-p)(p)(p)

Three free pistol shots: Energy cost is 265.

`SS,fPS,SS,fPS,SS,fPS` - Probability = (p)(p)(p)

The mean energy cost is the weighted sum

`415*[(1-p)^5] + 365*[4*p(1-p)^3 + p(1-p)^4] + 315*[3*p^2(1-p) + 3*p^2(1-p)^2] + 265*[p^3]`

Mean energy (no swordmaster, no jolly roger): 346.8
Mean energy (   swordmaster, no jolly roger): 332.5
Mean energy (no swordmaster,    jolly roger): 296.5
Mean energy (   swordmaster,    jolly roger): 284.9

![alt text](energy_cost.png, "A plot of sequence energy cost as a function of saber slash proc probability.")

The most expensive scenario is the 415 energy consuming sequence (straight SS -> RT spam).

### With AR
With AR up, we have 15 globals in the COTD window, so we're regenerating energy faster, but have more globals to spend it on.

The sequence that requires the *most* energy with AR is

`SS, RT, SS, RT, SS, RT, SS, RT, SS, RT, SS, RT, SS, RT, SS`

This sequence costs 50x8 + 23x7 = 561 energy.

The sequence that requires the *least* energy with AR is

`SS, RT, fPS, RT, SS, RT, fPS, RT, SS, RT, fPS, RT, SS, RT, fPS`

This sequence costs 50x4 + 23x7 + 0x4 = 361 energy.

Doing a full case analysis would demonstrate combinatorial explosion here, though a dedicated reader could easily replicate the logic from the section above.

## Energy Regeneration
So let's look at energy regeneration to see which sequences are feasible, as a function of which buffs you have:

With Alacrity stacked at 20, in standard T19P pre-raid gear, most outlaw rogues will have a base energy regeneration rate about 15 per second.
Combat potency adds roughly another 7 energy per second, depending on gear.
This value for combat potency was estimated from T19P sims by computing the mean energy gained from combat potency and dividing by the encounter length.
That's a rate of 22 energy per second.

With Buried Treasure up, we have 40% increased energy regeneration rate, which pushes us to 7 + 1.4x15 = 28 energy per second.
With Grand Melee up, we auto-attack 40% faster, which means 40% more combat potency procs, which pushes us to 1.4x7 + 15 = 24.8 energy per second.
With Adrenaline Rush up, we have 100% increased energy regeneration rate, and 20% more attack speed, which pushes us to 1.2x7 + 2.0x15 = 38.4 energy per second.

The coefficients from these buffs stack multiplicatively - here's the energy gains as a function of various buffs.

Base: 7 + 15 = 22 energy per second

BT: 7 + (1.4)x15 = 28 energy per second

GM: (1.4)x7 + 15 = 24.8 energy per second

AR: (1.2)x7 + (2.0)x15 = 38.4 energy per second

BT+GM: (1.4)x7 + (1.4)x15 = 30.8 energy per second

BT+AR: (1.2)x7 + (1.4 x 2.0)x15 = 50.4 energy per second

GM+AR: (1.4 x 1.2)x7 + (2.0)x15 = 41.8 energy per second

BT+GM+AR: (1.4 x 1.2)x7 + (1.4 x 2.0)x15 = 53.8 energy per second

If these buffs are active for the entirety of the dreadblades window, then over 12 seconds we'll regenerate

Base: 264 energy

BT: 336 energy

GM: 297.6 energy

AR: 460.8 energy

BT+GM: 369.6 energy

BT+AR: 604.8 energy

GM+AR: 501.6 energy

BT+GM+AR: 645.6 energy

Turning on Blade Flurry reduces these by 10% (with all artifact traits affecting blade flurry).

## AR
Looking at the "worst case RNG" with AR up, we see that it costs us 561 energy in 15 globals.

If you just have AR, you'd want to pool 561 - 460 = ~100 energy before casting COTD.

You can even get away with pooling less energy if you have Buried Treasure or Grand Melee (or both) up.

If you have GM+AR, you'd want to pool 561 - 501 = 60 energy before casting COTD.

If you have BT+AR, you don't have to pool at all, because the expected 604 energy gained exceeds the 561 necessary. 

Takeaway: if you have Adrenaline Rush active for the entirety of your COTD window, you can basically chain-cast SS into RT with a small amount of pooling.

## No AR
Let's suppose it's the middle of the fight, and you don't have Adrenaline Rush ready. How should you use Curse?
The "worst case RNG" without AR costs us 415 energy in 11 globals.
The "mean case" without AR costs us 346 energy, (or less, depending on jolly roger buff and swordmaster talent -- see above for exact values).

If you have Buried Treasure and Grand Melee up, you should pool to 415- (369.6) = 53.4 energy. This ensures that you can chain cast SS + RT.

If you have Buried Treasure up, you should pool to 415 - 336 = 87 energy, so pooling to 87 is probably safe to ensure chain casting SS + RT

If you have Grand Melee up, you'll be unable to cover the full energy requirement of the worst case, even with pooling:
The energy deficit that you'd have to make up is 415 - 298 = 125 energy, so pooling to 100 still leaves you with a 25 energy deficit.
You can make this deficit up by replacing one of the first five SSs with a free Pistol Shot.
NOTE: You are almost certainly not going to land in the "worst case" for energy - that would require seeing *zero* saber slash procs from the first five saber slashes, the odds of which low.

If you don't have the swordmaster talent, or jolly roger, the probability of seeing zero SS procs in five SSs is 0.65^5 = 11.6%, which is already fairly low.

If you have the sword master talent, the probability of seeing zero SS procs in five SSs is 0.55^5 = 5.0%

If you have jolly roger, the probability of seeing zero SS procs in five SSs in 0.25^5 = 0.09%

If you have SM + JR, the probability of seeing zero SS procs in five SSs is basically zero: 0.15^5 = 0.007%.

Even a single free pistol shot proc will save you 50 energy from not having to cast Saber Slash, which makes up the 40 energy deficit.


If you have neither Grand Melee nor Buried Trasure up, you need to make up a 415 - 264 = 159 energy deficit. Pooling to 100 leaves you with a 59 energy deficit.
You can make this up by saving yourself from casting two saber slashes - you need to see at least two procs during the first five saber slashes.
The probability of this is given by a binomial distribution:

If you don't have swordmaster or jolly roger, the probability of seeing at least two procs in five slashes is 57.16%

If you have sword master talent, the probability of seeing at least two procs in five slashes is 74.38%

If you have jolly roger, the probability of seeing at least two procs in five slashes is 98%

If you have SM + JR, the probability of seeing at least two procs in five slashes is 99.7%

### A summary of results:

Baseline: Pool to 100. Pray for two slash procs.

BT: Pool to 100, don't need procs. Or pool to less energy, and live dangerously. Your call. You picked the Outlaw Rogue, might as well #yolo it up.

GM: Pool to 100. Pray for one slash proc.

AR: Pool to 100.

BT+GM: Pool to 54.

BT+AR: No pooling needed.

GM+AR: Pool to 60.

BT+GM+AR: No pooling needed.



## TODO:
- How do relics affect math? Decreased cost of finishing moves is huge here.

- Analyze the case where BT/GM/AR cover part of the window, but not all of it.

- Analyze the cases with Blade Flurry up


Outstanding questions for the TC community:

- If you have Marked for Death come off cooldown during a COTD window, and True Bearing (cooldown reducing buff) is active, should you fire off MFD or hold it until after COTD? 

  - Pros: the more finishers you cast during the window, combined with TB, will get it off CD faster.

  - Cons: it's relative value above replacement action is reduced, because a standard SS or PS will do exactly the same job, and those don't have a cooldown.

- You could conceivably abuse the fact that we almost never have a procless string of five saber slashes in a row to lower the amount of energy pooled.
