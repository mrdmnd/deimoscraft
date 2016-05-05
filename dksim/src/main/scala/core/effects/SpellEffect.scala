package core.effects

abstract class SpellEffect(stacksIn: Int, timeRemainingIn: Double) {
  val stacks: Int = stacksIn
  val timeRemaining: Double = timeRemainingIn
}
