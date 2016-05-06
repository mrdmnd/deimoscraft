package core.effects

abstract class Debuff(stacks: Int, timeRemaining: Double) extends SpellEffect(stacks, timeRemaining)

class BloodPlague(stacks: Int, timeRemaining: Double) extends Debuff(stacks, timeRemaining)

class FrostFever(stacks: Int, timeRemaining: Double) extends Debuff(stacks, timeRemaining)

class MarkOfSindragosa(stacks: Int, timeRemaining: Double) extends Debuff(stacks, timeRemaining)

class SoulReaperDot(stacks: Int, timeRemaining: Double) extends Debuff(stacks, timeRemaining)

class NecroticPlague(stacks: Int, timeRemaining: Double) extends Debuff(stacks, timeRemaining)
