package models

class Dealer(name: String, var hand: List[Card]) {
  
  def receiveCards(newCards: List[Card]): Unit = hand = hand ++ newCards

  def dealCards(deck: Deck, n: Int): List[Card] = {
    val dealtCards = deck.deal(n)
    receiveCards(dealtCards)
    dealtCards
  }

  def getName: String = name
  def getHand: List[Card] = hand

  override def toString: String = s"$name with hand: ${hand.mkString(", ")}"
}