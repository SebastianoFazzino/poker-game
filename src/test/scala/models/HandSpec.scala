package models

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class HandSpec extends AnyFlatSpec with Matchers {

  "A Hand" should "evaluate to a flush" in {
    val cards = List(
      new Card("Hearts", "2"),
      new Card("Hearts", "4"),
      new Card("Hearts", "6"),
      new Card("Hearts", "8"),
      new Card("Hearts", "10")
    )
    val hand = new Hand(cards)
    hand.evaluateHand shouldEqual HandScore.Flush
  }

  it should "evaluate to a straight" in {
    val cards = List(
      new Card("Hearts", "2"),
      new Card("Diamonds", "3"),
      new Card("Clubs", "4"),
      new Card("Spades", "5"),
      new Card("Hearts", "6")
    )
    val hand = new Hand(cards)
    hand.evaluateHand shouldEqual HandScore.Straight
  }

  it should "evaluate to three of a kind" in {
    val cards = List(
      new Card("Hearts", "2"),
      new Card("Diamonds", "2"),
      new Card("Clubs", "2"),
      new Card("Spades", "5"),
      new Card("Hearts", "6")
    )
    val hand = new Hand(cards)
    hand.evaluateHand shouldEqual HandScore.ThreeOfAKind
  }

  it should "evaluate to two pairs" in {
    val cards = List(
      new Card("Hearts", "2"),
      new Card("Diamonds", "2"),
      new Card("Clubs", "3"),
      new Card("Spades", "3"),
      new Card("Hearts", "6")
    )
    val hand = new Hand(cards)
    hand.evaluateHand shouldEqual HandScore.TwoPairs
  }

  it should "evaluate to one pair" in {
    val cards = List(
      new Card("Hearts", "2"),
      new Card("Diamonds", "2"),
      new Card("Clubs", "4"),
      new Card("Spades", "5"),
      new Card("Hearts", "6")
    )
    val hand = new Hand(cards)
    hand.evaluateHand shouldEqual HandScore.OnePair
  }

  it should "evaluate to a high card" in {
    val cards = List(
      new Card("Hearts", "2"),
      new Card("Diamonds", "4"),
      new Card("Clubs", "6"),
      new Card("Spades", "8"),
      new Card("Hearts", "10")
    )
    val hand = new Hand(cards)
    hand.evaluateHand shouldEqual HandScore.HighCard
  }

  it should "compare hands correctly" in {
    val hand1Cards = List(
      new Card("Hearts", "2"),
      new Card("Hearts", "4"),
      new Card("Hearts", "6"),
      new Card("Hearts", "8"),
      new Card("Hearts", "10")
    )
    val hand2Cards = List(
      new Card("Diamonds", "2"),
      new Card("Diamonds", "3"),
      new Card("Diamonds", "4"),
      new Card("Diamonds", "5"),
      new Card("Diamonds", "6")
    )
    val hand1 = new Hand(hand1Cards)
    val hand2 = new Hand(hand2Cards)

    hand1.compareHands(hand2) shouldEqual 1 // Flush beats straight
  }
}
