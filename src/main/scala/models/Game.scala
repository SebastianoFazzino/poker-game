package models

class Game(
            val deck: Deck,
            val communityCards: List[Card],
            val player: Player,
            val dealer: Dealer,
            val bet: Int,
            val pot: Int,
            val inGame: Boolean
          ) {
  def getDeck: Deck = deck
  def getPlayer: Player = player
  def getBet: Int = bet
  def getPot: Int = pot
  private def getDealer: Dealer = dealer
  private def getCommunityCards: List[Card] = communityCards

  def resetGame(): Game = {
    val newDeck = new Deck().init()
    val newCommunityCards = List()
    val newPlayer = new Player("Player", List(), 100)
    val newDealer = new Dealer("Dealer", List())
    val newBet = 0
    val newPot = 0
    new Game(newDeck, newCommunityCards, newPlayer, newDealer, newBet, newPot, true)
  }

  def startGame(bet: Int): Game = {
    val newDeck = new Deck().init()
    player.bet(bet)
    val newPot = pot + bet
    new Game(newDeck, communityCards, player, dealer, bet, newPot, true)
  }

  private def askForBet(): Int = {
    val minBet = 10  // Define the minimum bet
    println(s"Please enter your bet amount (min $minBet):")
    val betAmount = scala.io.StdIn.readInt()
    if (betAmount < minBet) {
      throw new IllegalArgumentException(s"Bet amount must be at least $minBet")
    }
    if (betAmount <= player.getChips) {
      player.bet(betAmount)
      betAmount
    } else {
      throw new IllegalArgumentException("Insufficient chips for the bet amount")
    }
  }

  private def dealCommunityCards(): Game = {
    val newCards = deck.deal(3)
    new Game(deck, newCards, player, dealer, bet, pot, true)
  }

  private def revealDealerHand(): Game = {
    val dealerHand = dealer.dealCards(deck, 2)
    val updatedDealer = new Dealer(dealer.getName, dealerHand)
    new Game(deck, communityCards, player, updatedDealer, bet, pot, true)
  }

  private def evaluateHands(): Unit = {
    val playerHand = new Hand(player.getHand ++ communityCards)
    val dealerHand = new Hand(dealer.getHand ++ communityCards)

    println(s"Player's hand: ${playerHand}")
    println(s"Dealer's hand: ${dealerHand}")

    val result = HandScore.compareScore(playerHand.evaluateHand, dealerHand.evaluateHand)
    if (result > 0) {
      println("Player wins!")
    } else if (result < 0) {
      println("Dealer wins!")
    } else {
      println("It's a tie!")
    }
  }

  private def continueGame(): Unit = {
    println("Would you like to play another round? (yes/no)")
    val answer = scala.io.StdIn.readLine().trim.toLowerCase
    if (answer == "yes") {
      val newGame = Game.createNewGame(player, dealer)
      newGame.gameLoop()
    } else {
      println("Thanks for playing!")
    }
  }

  def gameLoop(): Unit = {
    println("Starting a new game...")
    try {
      val betAmount = askForBet()  // Ask player for a bet
      println(s"Player placed a bet of $betAmount")

      val gameWithInitialBet = startGame(betAmount)  // Start the game with the player's bet
      val gameWithCommunityCards = gameWithInitialBet.dealCommunityCards()  // Deal community cards
      println(s"Community Cards: ${gameWithCommunityCards.getCommunityCards.mkString(", ")}")

      val gameAfterRevealing = gameWithCommunityCards.revealDealerHand()  // Reveal dealer's hand
      println(s"Dealer's hand: ${gameAfterRevealing.getDealer.getHand.mkString(", ")}")

      gameAfterRevealing.evaluateHands()  // Evaluate hands

      gameAfterRevealing.continueGame()  // Ask if the player wants to play another round
    } catch {
      case e: Exception => println(s"Error: ${e.getMessage}")
    }
  }

  override def toString: String = s"${player.name} starts game betting ${this.bet}."
}

object Game {
  def createNewGame(player: Player, dealer: Dealer): Game = {
    val newDeck = new Deck().init()
    new Game(newDeck, List(), player, dealer, 0, 0, false)
  }
}

