package models

object HandScore extends Enumeration {

  type HandScore = Value
  val HighCard: Value = Value(1, "HighCard")
  val OnePair: Value = Value(2, "OnePair")
  val TwoPairs: Value = Value(3, "TwoPairs")
  val ThreeOfAKind: Value = Value(4, "ThreeOfAKind")
  val Straight: Value = Value(5, "Straight")
  val Flush: Value = Value(6, "Flush")

  def compareScore(score1: HandScore, score2: HandScore): Int = {
    score1.id.compare(score2.id)
  }

}
