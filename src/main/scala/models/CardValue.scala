package models

object CardValue {
  val values: Map[String, Int] = Map(
    "2" -> 2, "3" -> 3, "4" -> 4, "5" -> 5, "6" -> 6, "7" -> 7, "8" -> 8,
    "9" -> 9, "10" -> 10, "Jack" -> 11, "Queen" -> 12, "King" -> 13, "Ace" -> 14
  )

  val suits: List[String] = List("Hearts", "Diamonds", "Clubs", "Spades")
  val ranks: List[String] = values.keys.toList

  def getValue(rank: String): Int = values.getOrElse(rank, 0)
}
