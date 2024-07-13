package models

import models.Game.createNewGame

class Game(
            val deck: Deck,
            val communityCards: List[Card],
            val player: Player,
            val dealer: Dealer,
            val bet: Int,
            val pot: Int,
            val inGame: Boolean
          ) {

  private val minBet = 5
  private val maxBet = 100

  private def getCommunityCards: List[Card] = communityCards
  private def getPlayer: Player = player
  private def getDealer: Dealer = dealer

  def getDeck: Deck = deck
  def getBet: Int = bet
  def getPot: Int = pot

  private def startGame(bet: Int): Game = {
    val newPot = pot + bet
    val newGame = new Game(deck, communityCards, player, dealer, bet, newPot, true)
    newGame
  }

  private def askForBet(): Option[Int] = {
    println("\nDo you want to place a bet or fold? (f to fold, any other key to bet)")
    val action = scala.io.StdIn.readLine().trim.toLowerCase

    if (action == "f") {
      None
    } else {
      var validBet = false
      var betAmount = 0

      while (!validBet) {

        try {
          println(s"Please enter your bet amount (min $minBet, max $maxBet):")
          betAmount = scala.io.StdIn.readInt()

          if (betAmount < minBet) {
            throw new IllegalArgumentException(s"Bet amount must be at least $minBet")
          } else if (betAmount > maxBet) {
            throw new IllegalArgumentException(s"Bet amount must be at most $maxBet")
          } else if (betAmount > player.getChips) {
            throw new IllegalArgumentException("Insufficient chips for the bet amount")
          } else {
            validBet = true
          }

        } catch {
          case e: IllegalArgumentException => println(e.getMessage)
          case _: NumberFormatException => println("Invalid input. Please enter a numeric value.")
        }
      }
      player.bet(betAmount)
      Some(betAmount)
    }
  }

  private def dealPersonalCards(): Unit = {
    player.receiveCards(deck.deal(2))
    dealer.receiveCards(deck.deal(2))
  }

  private def dealCommunityCards(): Game = {
    val newCards = communityCards ++ deck.deal(3)
    new Game(deck, newCards, player, dealer, bet, pot, true)
  }

  private def revealDealerHand(): Unit = {
    println(s"Revealing dealer's cards: ${dealer.getHand.mkString(", ")}")
  }

  private def evaluateHands(): Unit = {
    val playerHand = new Hand(player.getHand ++ communityCards)
    val dealerHand = new Hand(dealer.getHand ++ communityCards)

    playerHand.cards.sortBy(_.getValue).reverse
    playerHand.cards.sortBy(_.getValue).reverse

    println(s"${player.getName}'s cards: ${playerHand.cards.mkString(", ")}")
    println(s"Dealer's cards: ${dealerHand.cards.mkString(", ")}")

    println(s"\n${player.getName}'s hand: ${playerHand.getHandScore}")
    println(s"Dealer's hand: ${dealerHand.getHandScore}")

    printSeparator()

    Thread.sleep(1000)

    val result = playerHand.compareHands(dealerHand)

    if (result > 0) {
      println(s"${player.getName} wins ${pot} chips with ${playerHand.getHandScore}!")
      player.winHand(bet *2)

    } else if (result < 0) {
      println(s"Dealer wins with ${dealerHand.getHandScore}, Player loses ${pot} chips.")

    } else {
      println("It's a tie!")
      player.addChips(bet)
    }

    printSeparator()
  }

  private def continueGame(): Unit = {
    if (player.getChips <= minBet) {
      println(s"${player.getName} has not enough chips for playing another game. Game over!")
      return
    }
    println("\nWould you like to play another round? (y/n)")
    val answer = scala.io.StdIn.readLine().trim.toLowerCase
    if (answer == "y") {
      val newGame = createNewGame(player, dealer)
      newGame.gameLoop()
    } else {
      println(s"\n${player.getName} leaves the game with ${player.getChips} chips and ${player.getTotalWins} wins.")
      println("\nThanks for playing!\n")
    }
  }

  def gameLoop(): Unit = {
    println(s"Welcome ${player.name}! Starting a new game...")
    try {

      println(s"\n${player.getName}'s chips: ${player.getChips}")

      dealPersonalCards()  // Deal cards to player and dealer
      println(s"${player.getName}'s hand: ${player.getHand.mkString(", ")}")

      askForBet() match {
        case Some(betAmount) =>
          println(s"${player.getName} placed a bet of $betAmount")
          printSeparator()

          val gameWithInitialBet = startGame(betAmount)  // Start the game with the player's bet
          val gameWithCommunityCards = gameWithInitialBet.dealCommunityCards()  // Deal community cards
          println(s"Dealing community cards: ${gameWithCommunityCards.getCommunityCards.mkString(", ")}")

          gameWithCommunityCards.revealDealerHand()  // Reveal dealer's cards

          printSeparator()

          gameWithCommunityCards.evaluateHands()  // Evaluate hands

          Thread.sleep(1000)

          gameWithCommunityCards.continueGame()  // Ask if the player wants to play another round

        case None =>
          println(s"${player.getName} folded!")
          continueGame()
      }
    } catch {
      case e: Exception => println(s"Error: ${e.getMessage}")
    }
  }

  private def printSeparator(): Unit = {
    println("----------------------------------------------------------------------------------")
  }

  override def toString: String = s"${player.getName} starts game betting ${this.bet}."
}

object Game {
  def createNewGame(player: Player, dealer: Dealer): Game = {
    val newDeck = new Deck().init()
    player.startNewGame()
    dealer.startNewGame()
    new Game(newDeck, List(), player, dealer, 0, 0, false)
  }

}
