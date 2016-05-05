package core.spells

trait Spell {
  // Override the defaults if the value is different.
  val offGCD: Boolean = false
  val hasTravelTime: Boolean = false
  val baseCooldown: Double = 0.0
}
