package models

object CardValue {

  private val values: Map[String, Int] = Map(
    "2" -> 2, "3" -> 3, "4" -> 4, "5" -> 5, "6" -> 6, "7" -> 7, "8" -> 8,
    "9" -> 9, "10" -> 10, "Jack" -> 11, "Queen" -> 12, "King" -> 13, "Ace" -> 14
  )

  private val suitRank: Map[String, Int] = Map(
    "Clubs" -> 1,
    "Diamonds" -> 2,
    "Hearts" -> 3,
    "Spades" -> 4
  )

  val suits: List[String] = List("Clubs", "Diamonds", "Hearts", "Spades")
  val ranks: List[String] = values.keys.toList

  def getValue(rank: String): Int = values.getOrElse(rank, 0)
  def getSuitRank(suit: String): Int = suitRank.getOrElse(suit, 0)

}
