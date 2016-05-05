package core.state

import core.effects.{Buff, Debuff}
import core.effects.buffs.BloodCharge
import core.effects.debuffs.BloodPlague
import core.resources.{Runes, RunicPower}
import core.spells.shared.GlobalCooldown
import core.{CharacterStats, Glyphs, Talents, Target}
import core.spells.{Action, Spell}

abstract class State {
  // All data members in here should be IMMUTABLE! This is important for thread safety.
  // ENVIRONMENT
  val timeInCombat: Double

  // PLAYER

  // Stable configuration info
  val talents: Talents
  val glyphs: Glyphs

  // Resource systems
  val runes: Runes
  val bloodCharges: BloodCharge
  val runicPower: RunicPower

  // Snapshot stats
  val characterSheetStats: CharacterStats

  // Buffs
  val buffs: Seq[Buff]

  // these are uh specific
  // val shadowInfusion: Option[ShadowInfusion]
  // val suddenDoom: Option[SuddenDoom]
  //val hasGhoulSummoned: Boolean

  // Cooldowns
  // Includes the GCD cooldown.
  // a bit weird as the keys are of type Object (extending Spell), not a class.
  val cooldowns: Map[Spell, Double]




  // TARGET(s)
  val knownTargets: Seq[Target] // All living targets the sim knows about
  val taggedTargets: Seq[Target] // All living targets we've ever damaged
  val bloodBoilRangeTargets: Seq[Target] // All living targets inside of our blood boil radius
  val currentTarget: Target // Our current Target


  // Utility Methods
  def gcdInactive: Boolean = return cooldowns(GlobalCooldown) == 0.0
  def gcdActive: Boolean = return cooldowns(GlobalCooldown) > 0.0

  // Abstract methods (must be implemented by subclass)
  abstract def step(action: Action): State
}
