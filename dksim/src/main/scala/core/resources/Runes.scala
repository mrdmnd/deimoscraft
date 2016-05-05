package core.resources

case class Rune(timeLeft: Double, isDeath: Boolean, isDepleted: Boolean)

class Runes(runes: Seq[Rune])
