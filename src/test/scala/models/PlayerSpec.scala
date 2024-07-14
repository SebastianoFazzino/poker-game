package models

import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite

class PlayerSpec extends AnyFunSuite with BeforeAndAfterEach {

  var deck: Deck = _

  override def beforeEach(): Unit = {
    deck = new Deck().init()
  }

  test("Player should receive cards") {
    val player = new Player("Player", List(), 100)
    val initialHandSize = player.getHand.length

    player.receiveCards(deck.deal(2))
    assert(player.getHand.length == initialHandSize + 2)
  }

  test("Player should bet correctly") {
    val player = new Player("Player", List(), 100)

    assert(player.getChips == 100)

    player.bet(20)

    assert(player.getChips == 80)
  }

  test("Player should start a new game with an empty hand") {
    val player = new Player("Player", deck.deal(2), 100)

    assert(player.getHand.nonEmpty)

    player.startNewGame()

    assert(player.getHand.isEmpty)
  }

  test("Player should win hand correctly") {
    val player = new Player("Player", List(), 100)

    assert(player.getChips == 100)
    assert(player.getTotalWins == 0)

    player.winHand(50)

    assert(player.getChips == 150)
    assert(player.getTotalWins == 1)
  }
  
}
