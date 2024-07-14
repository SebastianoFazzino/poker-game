package repositories

object GameRepositorySingleton {
  var gameRepository: Option[GameRepository] = None

  def setRepository(repository: GameRepository): Unit = {
    gameRepository = Some(repository)
  }

  def getRepository: GameRepository = {
    gameRepository.getOrElse(throw new IllegalStateException("GameRepository is not initialized"))
  }
}

