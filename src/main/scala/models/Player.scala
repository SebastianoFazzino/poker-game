package models

class Player(val name: String, var hand: List[Card] = List(), var chips: Int = 100, var totalWins: Int = 0) {
  def getName: String = name
  def getHand: List[Card] = hand
  def getChips: Int = chips
  def getTotalWins: Int = totalWins
  def startNewGame(): Unit = hand = List()

  def bet(amount: Int): Unit = {
    if (amount > chips) throw new IllegalArgumentException("Not enough chips")
    chips -= amount
  }
  
  def winHand(chipsWon: Int): Unit = {
    chips += chipsWon
    totalWins += 1
  }
  
  def addChips(amount: Int): Unit = chips += amount
  
  def receiveCards(newCards: List[Card]): Unit = hand = hand ++ newCards

  override def toString: String = s"$name: $hand (Chips: $chips)"
}
