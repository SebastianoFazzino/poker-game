package models

import org.scalatest.funsuite.AnyFunSuite

class HandScoreSpec extends AnyFunSuite {

  test("HandScore enumeration values should be defined correctly") {
    assert(HandScore.HighCard.id == 1)
    assert(HandScore.OnePair.id == 2)
    assert(HandScore.TwoPairs.id == 3)
    assert(HandScore.ThreeOfAKind.id == 4)
    assert(HandScore.Straight.id == 5)
    assert(HandScore.Flush.id == 6)
  }

  test("HandScore compareScore method should compare scores correctly") {
    assert(HandScore.compareScore(HandScore.HighCard, HandScore.OnePair) < 0)
    assert(HandScore.compareScore(HandScore.TwoPairs, HandScore.HighCard) > 0)
    assert(HandScore.compareScore(HandScore.Flush, HandScore.Flush) == 0)
  }

}
