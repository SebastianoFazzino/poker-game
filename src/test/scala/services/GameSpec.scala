package services

import models._
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import repositories.documents.GameDocument
import repositories.{GameRepository, GameRepositorySingleton}

import scala.concurrent.Future

class GameSpec extends AnyFlatSpec with Matchers {

  val mockDeck: Deck = mock(classOf[Deck])
  val mockPlayer: Player = mock(classOf[Player])
  val mockDealer: Dealer = mock(classOf[Dealer])
  val card1 = new Card("Spades", "Ace")
  val card2 = new Card("Hearts", "Ace")
  val card3 = new Card("Diamonds", "Queen")
  val card4 = new Card("Clubs", "Jack")
  val card5 = new Card("Hearts", "10")
  val card6 = new Card("Spades", "King")
  val card7 = new Card("Diamonds", "King")
  val mockHand: Hand = mock(classOf[Hand])

  val communityCards: List[Card] = List(card5, card6, card7)
  val initialPot = 100
  val initialBet = 10
  val mockGame = new Game(mockDeck, communityCards, mockPlayer, mockDealer, initialBet, initialPot)

  when(mockPlayer.getName).thenReturn("Player1")
  when(mockDealer.getName).thenReturn("Dealer1")
  when(mockDeck.deal(3)).thenReturn(List(card5, card6, card7))

  "Game" should "initialize with correct values" in {
    mockGame.deck shouldBe mockDeck
    mockGame.communityCards shouldBe communityCards
    mockGame.player shouldBe mockPlayer
    mockGame.dealer shouldBe mockDealer
    mockGame.bet shouldBe initialBet
    mockGame.pot shouldBe initialPot
  }

  "startGame" should "update the pot correctly" in {
    val newGame = mockGame.startGame(20)
    newGame.pot shouldBe initialPot + 20
  }

  "askForBet" should "handle valid and invalid bets correctly" ignore {
    // This test is ignored because it relies on user input
    when(mockPlayer.getChips).thenReturn(100)
  }

  "dealPersonalCards" should "deal two cards to player and dealer" in {
    when(mockDeck.deal(2)).thenReturn(List(card1, card2))

    mockGame.dealPersonalCards()

    verify(mockPlayer).receiveCards(List(card1, card2))
    verify(mockDealer).receiveCards(List(card1, card2))
  }

  "dealCommunityCards" should "deal 3 additional community cards" in {
    val newCards = List(card5, card6, card7)

    val newGame = mockGame.dealCommunityCards()
    newGame.communityCards should contain theSameElementsAs (communityCards ++ newCards)
  }

  "revealDealerHand" should "print the dealer's hand" in {
    when(mockDealer.getHand).thenReturn(List(card1, card2))

    val output = new java.io.ByteArrayOutputStream()
    Console.withOut(output) {
      mockGame.revealDealerHand()
    }
    output.toString.trim should include ("Revealing dealer's cards: AceSpades, AceHearts")
  }

  "evaluateHands" should "compare player and dealer hands and determine the winner" in {

    val mockPlayerHand: List[Card] = List(card1, card2)
    val mockDealerHand: List[Card] = List(card3, card4)

    when(mockPlayer.getHand).thenReturn(mockPlayerHand)
    when(mockDealer.getHand).thenReturn(mockDealerHand)

    mockGame.evaluateHands()

    mockGame.winnerName shouldBe mockPlayer.getName
    mockGame.higherScore shouldBe Some(HandScore.TwoPairs)
  }

  "continueGame" should "handle continuation or exit based on user input" ignore {
    // This test is ignored because it relies on user input
  }

  "saveGame" should "attempt to save the game to the repository" in {

    // Mock the repository save operation
    val mockRepository = mock(classOf[GameRepository])
    GameRepositorySingleton.setRepository(mockRepository)

    when(mockPlayer.getHand).thenReturn(List(card1, card2))
    when(mockDealer.getHand).thenReturn(List(card3, card4))

    when(mockRepository.saveGame(any[GameDocument])).thenReturn(Future.successful(()))

    // Call the saveGame method
    mockGame.saveGame()

    // Verify save operation was called
    verify(mockRepository).saveGame(any[GameDocument])
  }

  "toString" should "return the correct string representation of the game" in {
    when(mockPlayer.getName).thenReturn("Player1")
    mockGame.toString shouldBe "Player1 starts game betting 10."
  }

  "gameLoop" should "handle a complete game cycle including bet, community cards, and evaluation" ignore {
    // Mocking player's chips
    when(mockPlayer.getChips).thenReturn(100)

    // Mock the methods called during the game loop
    when(mockGame.dealCommunityCards()).thenReturn(mockGame)
    when(mockGame.revealDealerHand()).thenReturn(())
    when(mockGame.evaluateHands()).thenReturn(())
    when(mockGame.continueGame()).thenReturn(())

    // Capture the output
    val output = new java.io.ByteArrayOutputStream()
    Console.withOut(output) {
      mockGame.gameLoop()
    }

    // Check for expected output
    output.toString should include ("Welcome Player1! Starting a new game...")
    output.toString should include ("Please enter your bet amount (min 5, max 100):")
    output.toString should include ("Dealing community cards:")
    output.toString should include ("Revealing dealer's cards:")
    output.toString should include ("Evaluating hands:")
    output.toString should include ("Would you like to play another round? (y/n)")
  }

}
