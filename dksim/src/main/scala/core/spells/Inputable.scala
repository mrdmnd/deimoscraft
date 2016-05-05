package core.spells

import core.state.State

trait Inputable {
  def isLegal(state: State): Boolean
  def becomesLegal(state: State): Double
  def updateState(state: State): Option[State] // The new state after pressing this input RIGHT NOW - returns None if not a legal input
}
