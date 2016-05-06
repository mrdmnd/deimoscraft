package core

case class Talent(name: String)

class Talents(talents: Seq[Talent])
  // Follows same format as armory:
  // http://us.battle.net/wow/en/tool/talent-calculator#daa!1200002!oPUjRs
  // zero-indexed, top to bottom

class Talents(talentBuilderString: String) {

}

