package models

import models.HandScore.HandScore

class Hand(val cards: List[Card]) {
  private val sortedCards = cards.sortBy(card => CardValue.getValue(card.rank)).reverse
  private val groupedByRank = sortedCards.groupBy(_.getRank).view.mapValues(_.size).toMap

  private def isFlush: Boolean = cards.map(_.getSuit).distinct.size == 1

  private def isStraight: Boolean = {
    val values = cards.map(card => CardValue.getValue(card.rank)).distinct.sorted

    if (values.size != 5) {
      false
    } else {
      // Check for normal straight (Ace as high)
      val normalStraight = values.zip(values.tail).forall { case (a, b) => b - a == 1 }

      // Check for straight with Ace as low (Ace, 2, 3, 4, 5)
      val aceLowStraight = values.sorted == List(2, 3, 4, 5, 14).sorted

      normalStraight || aceLowStraight
    }
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
      case HandScore.Straight => compareHighCard(other)
      case HandScore.ThreeOfAKind => compareThreeOfAKind(other)
      case HandScore.TwoPairs => compareTwoPairs(other)
      case HandScore.OnePair => compareOnePair(other)
      case HandScore.HighCard => compareHighCard(other)
    }
  }

  private def compareFlush(other: Hand): Int = {
    val highCardComparison = compareHighCard(other)
    if (highCardComparison != 0) return highCardComparison

    // If high cards are the same, compare suits
    val thisSuitRank = CardValue.getSuitRank(cards.head.getSuit)
    val otherSuitRank = CardValue.getSuitRank(other.cards.head.getSuit)
    thisSuitRank.compare(otherSuitRank)
  }

  private def compareHighCard(other: Hand): Int = {
    this.sortedCards.zip(other.sortedCards).foreach {
      case (card1, card2) =>
        val comparison = CardValue.getValue(card1.rank).compare(CardValue.getValue(card2.rank))
        if (comparison != 0) return comparison
    }
    0
  }

  private def compareThreeOfAKind(other: Hand): Int = {
    val thisThreeOfAKindRank = groupedByRank.find(_._2 == 3).get._1
    val otherThreeOfAKindRank = other.groupedByRank.find(_._2 == 3).get._1
    val comparison = CardValue.getValue(thisThreeOfAKindRank).compare(CardValue.getValue(otherThreeOfAKindRank))
    if (comparison != 0) return comparison
    compareHighCard(other)
  }

  private def compareTwoPairs(other: Hand): Int = {
    val thisPairs = groupedByRank.filter(_._2 == 2).keys.toList.sorted.map(CardValue.getValue).reverse
    val otherPairs = other.groupedByRank.filter(_._2 == 2).keys.toList.sorted.map(CardValue.getValue).reverse
    for ((thisPair, otherPair) <- thisPairs zip otherPairs) {
      val comparison = thisPair.compare(otherPair)
      if (comparison != 0) return comparison
    }
    compareHighCard(other)
  }

  private def compareOnePair(other: Hand): Int = {
    val thisPairRank = groupedByRank.find(_._2 == 2).get._1
    val otherPairRank = other.groupedByRank.find(_._2 == 2).get._1
    val comparison = CardValue.getValue(thisPairRank).compare(CardValue.getValue(otherPairRank))
    if (comparison != 0) return comparison
    compareHighCard(other)
  }

  override def toString: String = s"Cards: ${sortedCards.mkString(", ")} with score ${evaluateHand}"
}
