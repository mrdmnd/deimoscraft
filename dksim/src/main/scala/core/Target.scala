package core

import core.effects.Debuff
import core.effects.debuffs.{BloodPlague, FrostFever, NecroticPlague}

class Target(debuffs: Seq[Debuff], range: Double, currentHp: Int, maxHp: Int, name: String) {

  def timeToDie(dpsOnTarget: Double): Double = {
    (1.0 * currentHp / dpsOnTarget)
  }

  def inMeleeRange: Boolean = {
    range < 5
  }

  def inBloodBoilRange(glyphed: Boolean): Boolean = {
    range < (if (glyphed) 15 else 10)
  }

  def allDiseasesActive: Boolean = {
    var bpUp, ffUp, npUp = false
    debuffs.foreach {
      case debuff: BloodPlague => (bpUp = true)
      case debuff: FrostFever => (ffUp = true)
      case debuff: NecroticPlague => (npUp = true)
    }
    (bpUp && ffUp) || npUp
  }

  def diseaseTimeRemaining: Double = {
    // How long until min(bpDuration, ffDuration) or NP duration
    var bpTime, ffTime, npTime = 0.0
    debuffs.foreach {
      case d: BloodPlague => (bpTime = d.timeRemaining)
      case d: FrostFever => (ffTime = d.timeRemaining)
      case d: NecroticPlague => (npTime = d.timeRemaining)
    }
    return Seq(bpTime, ffTime, npTime).min
  }


  def targetInUnholySoulReaperRange(dpsOnTarget: Double): Boolean = {
    val hpPercent = (1.0 * currentHp / maxHp)
    hpPercent - 5 * (hpPercent / timeToDie(dpsOnTarget)) <= 0.45
  }

  def targetInBloodSoulReaperRange(dpsOnTarget: Double): Boolean = {
    val hpPercent = (1.0 * currentHp / maxHp)
    hpPercent - 5 * (hpPercent / timeToDie(dpsOnTarget)) <= 0.35
  }
}
