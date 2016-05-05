package core.spells.unholy

import core.spells.{Inputable, Spell}
import core.state.State

object SoulReaperUnholy extends Spell with Inputable {
  override def isLegal(state: State): Boolean = ???

  override def updateState(state: State): Option[State] = ???

  override def becomesLegal(state: State): Double = ???
}
