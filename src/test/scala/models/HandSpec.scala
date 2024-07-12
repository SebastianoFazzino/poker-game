package models

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class HandSpec extends AnyFlatSpec with Matchers {

  // Check if evaluateHand returns the correct HandScore

  "Hand" should "evaluate to a flush" in {
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

  // Check if compareFlush returns the correct comparison result

  it should "compare flushes by suit" in {
    val hand1Cards = List(
      new Card("Hearts", "2"),
      new Card("Hearts", "4"),
      new Card("Hearts", "6"),
      new Card("Hearts", "8"),
      new Card("Hearts", "10")
    )
    val hand2Cards = List(
      new Card("Diamonds", "2"),
      new Card("Diamonds", "4"),
      new Card("Diamonds", "6"),
      new Card("Diamonds", "8"),
      new Card("Diamonds", "10")
    )
    val hand1 = new Hand(hand1Cards)
    val hand2 = new Hand(hand2Cards)

    hand1.compareHands(hand2) shouldEqual 1 // Hearts flush beats Diamonds flush
  }

  it should "evaluate to a straight" in {
    val cards = List(
      new Card("Hearts", "2"),
      new Card("Diamonds", "4"),
      new Card("Clubs", "3"),
      new Card("Spades", "6"),
      new Card("Hearts", "5")
    )
    val hand = new Hand(cards)
    hand.evaluateHand shouldEqual HandScore.Straight
  }

  it should "evaluate to a low ace straight" in {
    val cards = List(
      new Card("Hearts", "2"),
      new Card("Diamonds", "3"),
      new Card("Clubs", "4"),
      new Card("Spades", "Ace"),
      new Card("Hearts", "5")
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

  it should "compare hands with Three of a Kind correctly" in {
    val hand1 = new Hand(List(
      new Card("Hearts", "3"),
      new Card("Diamonds", "3"),
      new Card("Clubs", "4"),
      new Card("Spades", "3"),
      new Card("Hearts", "6")
    ))
    val hand2 = new Hand(List(
      new Card("Hearts", "2"),
      new Card("Diamonds", "2"),
      new Card("Clubs", "8"),
      new Card("Spades", "King"),
      new Card("Hearts", "2")
    ))

    val hand3 = new Hand(List(
      new Card("Hearts", "4"),
      new Card("Diamonds", "4"),
      new Card("Clubs", "4"),
      new Card("Spades", "3"),
      new Card("Hearts", "2")
    ))

    hand1.compareHands(hand2) shouldEqual 1 // Three of a Kind of 3s beats Three of a Kind of 2s
    hand2.compareHands(hand3) shouldEqual -1 // Three of a Kind of 2s loses to Three of a Kind of 4s
    hand3.compareHands(hand1) shouldEqual 1 // Three of a Kind of 4s beats Three of a Kind of 3s
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

  it should "compare hands with Two Pairs correctly" in {
    val hand1 = new Hand(List(
      new Card("Hearts", "3"),
      new Card("Diamonds", "3"),
      new Card("Clubs", "5"),
      new Card("Spades", "5"),
      new Card("Hearts", "6")
    ))
    val hand2 = new Hand(List(
      new Card("Hearts", "2"),
      new Card("Diamonds", "2"),
      new Card("Clubs", "King"),
      new Card("Spades", "King"),
      new Card("Hearts", "Ace")
    ))

    val hand3 = new Hand(List(
      new Card("Hearts", "4"),
      new Card("Diamonds", "4"),
      new Card("Clubs", "6"),
      new Card("Spades", "6"),
      new Card("Hearts", "3")
    ))

    hand2.compareHands(hand1) shouldEqual 1 // Two Pairs (Kings and 2s) beats Two Pairs (5s and 3s)
    hand3.compareHands(hand2) shouldEqual -1 // Two Pairs (6s and 4s) loses to Two Pairs (Kings and 2s)
    hand3.compareHands(hand1) shouldEqual 1 // Two Pairs (6s and 4s) beats Two Pairs (5s and 3s)

    // Check if the higher card is considered when comparing two pairs
    val hand4 = new Hand(List(
      new Card("Hearts", "4"),
      new Card("Diamonds", "4"),
      new Card("Clubs", "6"),
      new Card("Spades", "6"),
      new Card("Diamonds", "2")
    ))

    val hand5 = new Hand(List(
      new Card("Clubs", "4"),
      new Card("Spades", "4"),
      new Card("Hearts", "6"),
      new Card("Clubs", "6"),
      new Card("Hearts", "3")
    ))

    hand4.compareHands(hand5) shouldEqual -1 // Two Pairs (6s and 4s, kicker 2) loses against Two Pairs (6s and 4s, kicker 3)
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

  // Check if compareHands returns the correct comparison result

  it should "compare hands correctly" in {
    val flushHand = new Hand(List(
      new Card("Hearts", "2"),
      new Card("Hearts", "4"),
      new Card("Hearts", "6"),
      new Card("Hearts", "8"),
      new Card("Hearts", "10")
    ))
    val straightHand = new Hand(List(
      new Card("Diamonds", "2"),
      new Card("Diamonds", "3"),
      new Card("Diamonds", "4"),
      new Card("Diamonds", "5"),
      new Card("Diamonds", "6")
    ))
    flushHand.compareHands(straightHand) shouldEqual 1 // Flush beats straight

    val threeOfAKindHand = new Hand(List(
      new Card("Hearts", "2"),
      new Card("Diamonds", "2"),
      new Card("Clubs", "2"),
      new Card("Spades", "5"),
      new Card("Hearts", "6")
    ))
    straightHand.compareHands(threeOfAKindHand) shouldEqual 1 // Straight beats three of a kind

    val twoPairsHand = new Hand(List(
      new Card("Hearts", "2"),
      new Card("Diamonds", "2"),
      new Card("Clubs", "3"),
      new Card("Spades", "3"),
      new Card("Hearts", "6")
    ))
    threeOfAKindHand.compareHands(twoPairsHand) shouldEqual 1 // Three of a kind beats two pairs

    val onePairHand = new Hand(List(
      new Card("Hearts", "2"),
      new Card("Diamonds", "2"),
      new Card("Clubs", "4"),
      new Card("Spades", "5"),
      new Card("Hearts", "6")
    ))
    twoPairsHand.compareHands(onePairHand) shouldEqual 1 // Two pairs beats one pair

    val highCardHand = new Hand(List(
      new Card("Hearts", "2"),
      new Card("Diamonds", "4"),
      new Card("Clubs", "6"),
      new Card("Spades", "8"),
      new Card("Hearts", "10")
    ))
    onePairHand.compareHands(highCardHand) shouldEqual 1 // One pair beats high card
  }
}
