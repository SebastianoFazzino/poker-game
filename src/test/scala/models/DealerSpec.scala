package models

import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite

class DealerSpec extends AnyFunSuite with BeforeAndAfterEach {

  var deck: Deck = _

  override def beforeEach(): Unit = {
    deck = new Deck().init()
  }

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
    val deck = new Deck().init()

    dealer.receiveCards(deck.deal(2))

    assert(dealer.getHand.nonEmpty)

    dealer.startNewGame()

    assert(dealer.getHand.isEmpty)
  }

}
