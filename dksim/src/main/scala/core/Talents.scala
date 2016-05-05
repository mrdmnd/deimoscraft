package core

class Talents(talentString: String) {
  // Follows same format as armory:
  // http://us.battle.net/wow/en/tool/talent-calculator#daa!1200002!oPUjRs
  // zero-indexed, top to bottom

  def usingPL : Boolean = {
    talentString.startsWith("1")
  }
  def usingUB : Boolean = {
    talentString.startsWith("2")
  }

  def usingNP : Boolean = {
    talentString.endsWith("0")
  }
  def usingDefile : Boolean = {
    talentString.endsWith("1")
  }
  def usingBOS : Boolean = {
    talentString.endsWith("2")
  }
}

