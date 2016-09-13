---
layout: default
title: Planning around Curse of the Dreadblades
---

The artifact ability for the Outlaw Rogue is Curse of the Dreadblades (COTD): for 12 seconds, your Pistol Shot (PS) and Saber Slash (SS) fill your combo points to maximum.
Naturally, when you hit this ability, your goal is to alternate builder -> finisher -> builder -> finisher, etc. It changes your rotation quite markedly.
The issue, of course, is that we want to fit as many finishers into the COTD window as possible.
First, some basics: without Adrenaline Rush (AR) active, our GCD is 1.0 seconds. With Adrenaline Rush up, the GCD is reduced to 0.8 seconds.
That means that we'll fit 12 GCDs into COTD without AR, and 15 GCDs with it (if it's active for the whole duration, like on the pull). 
We'll investigate (later) what happens if AR covers part of the COTD window but not all of it.
Our goal is to get as many finishers as possible into that COTD window. Let's look at the energy cost for our builders and finishers.
With the appropriate points in your artifact, finishers will cost 12 energy less.
Relics can further decrease their energy cost (we'll investigate that case later), but for now, let's assume a 12 energy cost reduction.
This means that you will have Roll the Bones (RTB) cost you 13 energy, and Run Through (RT) cost you 23 energy.

Saber Slash (SS) costs 50 energy at all times; Pistol Shot (PS) costs 40 energy if you don't have an opportunity proc, and zero energy if you do.
We'll call the zero-cost PS `fPS` for "free" PS.

With AR down, we have twelve globals in the COTD window.

The sequence that requires the *most* energy is

`SS, RT, SS, RT, SS, RT, SS, RT, SS, RT, SS, RT` - this sequence costs (50 + 23)x6 = 438 energy.

One alternate sequence might be "only use pistol shot" during COTD: that sequence looks like

`PS, RT, PS, RT, PS, RT, PS, RT, PS, RT, PS, RT` - this sequence costs (40 + 23)x6 = 378 energy.

The sequence that requires the *least* energy is

`SS, RT, fPS, RT, SS, RT, fPS, RT, SS, RT, fPS, RT` - this sequence costs (50 + 23)x3 + (0+23)x3 = 288 energy.

With AR up, we have 15 globals in the COTD window, so we're regenerating more energy, but we also have a much shorter global.

The sequence that requires the *most* energy with AR is

`SS, RT, SS, RT, SS, RT, SS, RT, SS, RT, SS, RT, SS, RT, SS` - this sequence costs (50 + 23)x7 + 50 = 561 energy.

If you do "only pistol shot" during COTD with AR is

`PS, RT, PS, RT, PS, RT, PS, RT, PS, RT, PS, RT, PS, RT, PS` - this sequence costs (40 + 23)x7 + 40 = 551 energy.

The sequence that requires the *least* energy with AR is

`SS, RT, fPS, RT, SS, RT, fPS, RT, SS, RT, fPS, RT, SS, RT, fPS` - this sequence costs (50 + 23)x4 + (0+23)x3 + 0 = 361 energy.

All else equal, we prefer the sequences that have more Saber Slashes than Pistol Shots, because Saber Slash does more base damage than Pistol Shot does.
We only want to incorporate Pistol Shot into our COTD window if we will be unable to have enough energy to get six finishers out (or more, with AR).

So let's look at energy regeneration to see which sequences are feasible, as a function of which buffs you have:

With Alacrity stacked at 20, in fairly reasonable gear, most outlaw rogues will have a base energy regeneration rate about 15 per second.
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

Looking at the "worst case RNG" with AR up, we see that it costs us 561 energy in 15 globals; 
but we're predicting that we will regenerate *at least* 460 energy in that window.

If you just have AR, you'd want to pool 561 - 460 = ~100 energy before casting COTD.

You can even get away with pooling less energy if you have Buried Treasure or Grand Melee (or both) up.

If you have GM+AR, you'd want to pool 561 - 501 = 60 energy before casting COTD.

If you have BT+AR, you don't have to pool at all, because the expected 604 energy gained exceeds the 561 necessary. 

Takeaway: if you have Adrenaline Rush active for the entirety of your COTD window, you can basically chain-cast SS into RT with a small amount of pooling.
Let's suppose it's the middle of the fight, and you don't have Adrenaline Rush ready. How should you use Curse?
The "worst case RNG" without AR costs us 438 energy in 12 globals.

If you have Buried Treasure and Grand Melee up, you should pool to 438 - (369.6) = 68.4 energy. This ensures that you can chain cast SS + RT.

If you have Buried Treasure up, you should pool to 438 - (336) = 102 energy, so pooling to 100 is probably safe to ensure chain casting SS + RT

If you have Grand Melee up, you'll be unable to cover the full energy requirement of the worst case, even with pooling:
The energy deficit that you'd have to make up is 438 - (298) = 140 energy, so pooling to 100 still leaves you with a 40 energy deficit.
You can make this deficit up by replacing one of the first five SSs with a free Pistol Shot.
NOTE: You are almost certainly not going to land in the "worst case" for energy - that would require seeing *zero* saber slash procs from the first five saber slashes, the odds of which low.

If you don't have the swordmaster talent, or jolly roger, the probability of seeing zero SS procs in five SSs is 0.65^5 = 11.6%, which is already fairly low.

If you have the sword master talent, the probability of seeing zero SS procs in five SSs is 0.55^5 = 5.0%

If you have jolly roger, the probability of seeing zero SS procs in five SSs in 0.25^5 = 0.09%

If you have SM + JR, the probability of seeing zero SS procs in five SSs is basically zero: 0.15^5 = 0.007%.

Even a single free pistol shot proc will save you 50 energy from not having to cast Saber Slash, which makes up the 40 energy deficit.


If you have neither Grand Melee nor Buried Trasure up, you need to make up a 438 - 264 = 174 energy deficit. Pooling to 100 leaves you with a 74 energy deficit.
You can make this up by saving yourself from casting two saber slashes - you need to see at least two procs during the first five saber slashes.
The probability of this is given by a binomial distribution:

If you don't have swordmaster or jolly roger, the probability of seeing at least two procs in five slashes is 57.16%

If you have sword master talent, the probability of seeing at least two procs in five slashes is 74.38%

If you have jolly roger, the probability of seeing at least two procs in five slashes is 98%

If you have SM + JR, the probability of seeing at least two procs in five slashes is 99.7%


A summary of how to pool:

Base: Pool to 100. Pray for two slash procs.

BT: Pool to 100, don't need procs.

GM: Pool to 100. Pray for one slash proc.

AR: Pool to 100.

BT+GM: Pool to 70.

BT+AR: No pooling needed.

GM+AR: Pool to 60.

BT+GM+AR: No pooling needed.

TODO:
- How do relics affect math? Decreased cost of finishing moves is huge here.
- Analyze the case where BT/GM/AR cover part of the window, but not all of it.

Outstanding questions for the TC community:
- If you have Marked for Death come off cooldown during a COTD window, and True Bearing (cooldown reducing buff) is active, should you fire off MFD or hold it until after COTD? 
  - Pros: the more finishers you cast during the window, combined with TB, will get it off CD faster.
  - Cons: it's relative value above replacement action is reduced, because a standard SS or PS will do exactly the same job, and those don't have a cooldown.
- You could conceivably abuse the fact that we almost never have a procless string of five saber slashes in a row to lower the amount of energy pooled.
  We should figure out the "average case" sequence for COTD (and its corresponding energy costs) and re-do the math in this post.
