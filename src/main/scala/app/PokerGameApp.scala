package app

import configs.MongoConfig
import models.{Dealer, Deck, Player}
import repositories.GameRepositorySingleton
import services.Game

object PokerGameApp {
  def main(args: Array[String]): Unit = {

    // Initialize MongoDB configuration and repository
    val mongoConfig = new MongoConfig
    val gameRepository = mongoConfig.getGameRepository

    // Set the repository for the singleton object
    GameRepositorySingleton.setRepository(gameRepository)

    def createNewGame(playerName: String): Game = {
      val newDeck = new Deck().init()
      val player = new Player(playerName)
      val dealer = new Dealer("Dealer")
      new Game(newDeck, List(), player, dealer, 0, 0)
    }

    println("Welcome to Poker Game!")
    println("Please enter your name:")
    val playerName = scala.io.StdIn.readLine().trim

    val game = createNewGame(playerName)
    game.gameLoop()
  }
}