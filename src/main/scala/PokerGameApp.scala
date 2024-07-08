import models.{Game, Player, Dealer}

@main def pokerGameApp(): Unit = {
  val player = new Player("Player", List(), 100)
  val dealer = new Dealer("Dealer", List())

  def startGame(): Unit = {
    val game = Game.createNewGame(player, dealer)
    println("Welcome to Poker Game!")
    game.gameLoop()
  }

  startGame()
}