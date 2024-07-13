package models

import org.scalatest.funsuite.AnyFunSuite

class DealerSpec extends AnyFunSuite {

  test("Dealer should receive cards from deck") {
    val dealer = new Dealer("Dealer")
    val deck = new Deck().init()

    val initialHandSize = dealer.getHand.length
    val dealtCards = dealer.dealCards(deck, 2)

    assert(dealer.getHand.length == initialHandSize + 2)
    assert(dealer.getHand.containsSlice(dealtCards))
  }

  test("Dealer should start a new game with an empty hand") {
    val dealer = new Dealer("Dealer")
    dealer.receiveCards(List(Card("Spades", "A"), Card("Hearts", "2")))

    assert(dealer.getHand.nonEmpty)

    dealer.startNewGame()

    assert(dealer.getHand.isEmpty)
  }

}
