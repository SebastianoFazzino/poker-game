package models

import scala.util.Random

class Deck {
  private val suits = List("Hearts", "Diamonds", "Clubs", "Spades")
  private val ranks = List("2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace")
  private var cards: List[Card] = List()

  def init(): Deck = {
    cards = for {
      suit <- suits
      rank <- ranks
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
