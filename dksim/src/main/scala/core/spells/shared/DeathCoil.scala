package core.spells.shared

import core.spells.{Inputable, Spell}
import core.state.State

object DeathCoil extends Spell with Inputable {
  override def isLegal(state: State): Boolean = ???

  override def updateState(state: State): Option[State] = ???

  override def becomesLegal(state: State): Double = ???
}
