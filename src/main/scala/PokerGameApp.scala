import models.{Dealer, Deck, Game, Player}

@main def pokerGameApp(): Unit = {

  def createNewGame(playerName: String): Game = {
    val newDeck = new Deck().init()
    val player = new Player(playerName, List(), 100)
    val dealer = new Dealer("Dealer", List())
    new Game(newDeck, List(), player, dealer, 0, 0, false)
  }

  println("Welcome to Poker Game!")
  println("Please enter your name:")
  val playerName = scala.io.StdIn.readLine().trim

  val game = createNewGame(playerName)
  game.gameLoop()

}