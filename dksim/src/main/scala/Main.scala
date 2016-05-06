import core._

object Main {
  def main(args: Array[String]): Unit = {
    // For each spell, figure out how long we have to wait before it's usable.
    // Sort the list.

  }

  def initialState(talentCalcUrl: String): state.State = {
    val data = talentCalcUrl.substring(talentCalcUrl.indexOf("#")+1)
    assert(data.startsWith("d"))
    val spec: Spec = data.charAt(1) match {
      case 'b' => Unholy
      case 'a' => Blood
      case 'Z' => Frost
    }
  }

}
