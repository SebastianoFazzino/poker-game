package models

class Player(val name: String, var hand: List[Card], var chips: Int) {
  def getName: String = name
  def getHand: List[Card] = hand
  def getChips: Int = chips

  def bet(amount: Int): Unit = {
    if (amount > chips) throw new IllegalArgumentException("Not enough chips")
    chips -= amount
  }

  def receiveCards(newCards: List[Card]): Unit = hand = hand ++ newCards

  override def toString: String = s"$name: $hand (Chips: $chips)"
}
