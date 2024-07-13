package models

import models.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite

import scala.compiletime.uninitialized

class DeckSpec extends AnyFunSuite with BeforeAndAfterEach {

  var deck: Deck = uninitialized

  override def beforeEach(): Unit = {
    deck = new Deck().init()
  }

  test("Deck should initialize with 52 unique cards") {
    assert(deck.cards.length == 52)
    val allUniqueCards = deck.cards.groupBy(_.toString).forall(_._2.length == 1)
    assert(allUniqueCards)
  }

  test("Deck should deal specified number of cards") {
    val initialSize = deck.cards.length
    val dealtCards = deck.deal(5)
    assert(dealtCards.length == 5)
    assert(deck.cards.length == initialSize - 5)
  }

  test("Deck should not deal more cards than available") {
    val initialSize = deck.cards.length
    val dealtCards = deck.deal(53) // Try to deal more cards than available
    assert(dealtCards.length == initialSize) // Should deal all remaining cards
    assert(deck.cards.isEmpty) // Deck should be empty after dealing all cards
  }

  test("Dealt cards should be shuffled") {
    val dealtCards1 = deck.deal(5)
    deck.init() // Reinitialize the deck to reshuffle cards
    val dealtCards2 = deck.deal(5)
    assert(dealtCards1 != dealtCards2) // Should be shuffled differently
  }
  
}
