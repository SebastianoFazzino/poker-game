package models

class Card(val suit: String, val rank: String) {
  def getSuit: String = suit
  def getRank: String = rank
  def getValue: Int = CardValue.getValue(rank)

  override def toString: String = s"$rank$suit"
}
