import models.{Dealer, Deck, Game, Player}

@main def pokerGameApp(): Unit = {

  def createNewGame(): Game = {
    val newDeck = new Deck().init()
    new Game(
      newDeck,
      List(),
      new Player("Player", List(), 100),
      new Dealer("Dealer", List()),
      0,
      0,
      false
    )
  }

  def startGame(): Unit = {
    val game = createNewGame()
    println("Welcome to Poker Game!")
    game.gameLoop()
  }

  startGame()
}
