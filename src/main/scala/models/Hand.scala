package models

import models.HandScore.HandScore

class Hand(val cards: List[Card]) {
  private val sortedCards = cards.sortBy(_.getValue)
  private val groupedByRank = sortedCards.groupBy(_.getRank).view.mapValues(_.size).toMap

  private def isFlush: Boolean = cards.map(_.getSuit).distinct.size == 1
  private def isStraight: Boolean = {
    val values = sortedCards.map(_.getValue).distinct
    values.size == 5 && values.zip(values.tail).forall { case (a, b) => b - a == 1 }
  }

  def evaluateHand: HandScore = {
    if (isFlush && isStraight) HandScore.Flush
    else if (isFlush) HandScore.Flush
    else if (isStraight) HandScore.Straight
    else if (groupedByRank.exists(_._2 == 3)) HandScore.ThreeOfAKind
    else if (groupedByRank.count(_._2 == 2) == 2) HandScore.TwoPairs
    else if (groupedByRank.exists(_._2 == 2)) HandScore.OnePair
    else HandScore.HighCard
  }

  def compareHands(other: Hand): Int = {
    val thisScore = this.evaluateHand
    val otherScore = other.evaluateHand

    val scoreComparison = HandScore.compareScore(thisScore, otherScore)
    if (scoreComparison != 0) return scoreComparison

    thisScore match {
      case HandScore.Flush => compareFlush(other)
      case HandScore.Straight => compareStraight(other)
      case HandScore.ThreeOfAKind => compareThreeOfAKind(other)
      case HandScore.TwoPairs => compareTwoPairs(other)
      case HandScore.OnePair => compareOnePair(other)
      case HandScore.HighCard => compareHighCard(other)
    }
  }

  private def compareHighCard(other: Hand): Int = {
    this.sortedCards.zip(other.sortedCards).foreach {
      case (card1, card2) =>
        val comparison = card1.getValue.compare(card2.getValue)
        if (comparison != 0) return comparison
    }
    0
  }

  private def compareFlush(other: Hand): Int = compareHighCard(other)
  private def compareStraight(other: Hand): Int = compareHighCard(other)

  private def compareThreeOfAKind(other: Hand): Int = {
    val thisThreeOfAKindRank = groupedByRank.find(_._2 == 3).get._1
    val otherThreeOfAKindRank = other.groupedByRank.find(_._2 == 3).get._1
    val thisThreeOfAKindValue = CardValue(thisThreeOfAKindRank)
    val otherThreeOfAKindValue = CardValue(otherThreeOfAKindRank)
    val comparison = thisThreeOfAKindValue.compare(otherThreeOfAKindValue)
    if (comparison != 0) return comparison
    compareHighCard(other)
  }

  private def compareTwoPairs(other: Hand): Int = {
    val thisPairs = groupedByRank.filter(_._2 == 2).keys.toList.sorted.map(CardValue(_)).reverse
    val otherPairs = other.groupedByRank.filter(_._2 == 2).keys.toList.sorted.map(CardValue(_)).reverse
    for ((thisPair, otherPair) <- thisPairs zip otherPairs) {
      val comparison = thisPair.compare(otherPair)
      if (comparison != 0) return comparison
    }
    compareHighCard(other)
  }

  private def compareOnePair(other: Hand): Int = {
    val thisPairRank = groupedByRank.find(_._2 == 2).get._1
    val otherPairRank = other.groupedByRank.find(_._2 == 2).get._1
    val thisPairValue = CardValue(thisPairRank)
    val otherPairValue = CardValue(otherPairRank)
    val comparison = thisPairValue.compare(otherPairValue)
    if (comparison != 0) return comparison
    compareHighCard(other)
  }

  private object CardValue {
    def apply(rank: String): Int = {
      rank match {
        case "2" => 2
        case "3" => 3
        case "4" => 4
        case "5" => 5
        case "6" => 6
        case "7" => 7
        case "8" => 8
        case "9" => 9
        case "T" => 10
        case "J" => 11
        case "Q" => 12
        case "K" => 13
        case "A" => 14
      }
    }
  }

  override def toString: String = s"Cards: ${sortedCards.mkString(", ")} with score ${evaluateHand}"
}
