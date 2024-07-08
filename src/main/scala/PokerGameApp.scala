import models.{Dealer, Deck, Game, Player}

@main def pokerGameApp(): Unit = {
  val player = new Player("Player", List(), 100)
  val dealer = new Dealer("Dealer", List())

  def createNewGame(): Game = {
    val newDeck = new Deck().init()
    new Game(newDeck, List(), player, dealer, 0, 0, false)
  }

  def startGame(): Unit = {
    val game = createNewGame()
    println("Welcome to Poker Game!")
    game.gameLoop()
  }

  startGame()
}
