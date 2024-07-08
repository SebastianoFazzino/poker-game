package models

class Card(val suit: String, val rank: String) {
  private val values = Map("2" -> 2, "3" -> 3, "4" -> 4, "5" -> 5, "6" -> 6, "7" -> 7, "8" -> 8, "9" -> 9, "T" -> 10, "J" -> 11, "Q" -> 12, "K" -> 13, "A" -> 14)

  def getSuit: String = suit
  def getRank: String = rank
  def getValue: Int = values.getOrElse(rank, 0)

  override def toString: String = s"$rank$suit"
}
