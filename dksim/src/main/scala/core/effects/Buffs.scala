package core.effects

abstract class Buff(stacks: Int, timeRemaining: Double) extends SpellEffect(stacks, timeRemaining)

class BloodCharge(stacks: Int, timeRemaining: Double) extends Buff(stacks, timeRemaining)

class ShadowInfusion(stacks: Int, timeRemaining: Double) extends Buff(stacks, timeRemaining)

class SoulReaperHaste(stacks: Int, timeRemaining: Double) extends Buff(stacks, timeRemaining)

class SuddenDoom(stacks: Int, timeRemaining: Double) extends Buff(stacks, timeRemaining)
