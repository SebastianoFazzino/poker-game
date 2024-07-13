package models

import scala.util.Random

class Deck {
  var cards: List[Card] = List()

  def init(): Deck = {
    cards = for {
      suit <- CardValue.suits
      rank <- CardValue.ranks
    } yield new Card(suit, rank)
    cards = Random.shuffle(cards)
    this
  }

  def deal(n: Int): List[Card] = {
    val dealtCards = cards.take(n)
    cards = cards.drop(n)
    dealtCards
  }
}
