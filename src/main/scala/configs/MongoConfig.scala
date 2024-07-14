package configs

import com.typesafe.config.ConfigFactory
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.{MongoClient, MongoDatabase}
import repositories.GameRepository
import repositories.documents.{DealerDocument, GameDocument, PlayerDocument}

class MongoConfig {

  private val config = ConfigFactory.load()
  private val mongoUri: String = config.getString("mongo.uri")
  private val databaseName: String = config.getString("mongo.databaseName")

  private val codecRegistry = fromRegistries(
    fromProviders(
      classOf[PlayerDocument],
      classOf[DealerDocument],
      classOf[GameDocument]
    ),
    DEFAULT_CODEC_REGISTRY
  )

  private val client: MongoClient = MongoClient(mongoUri)
  val database: MongoDatabase = client.getDatabase(databaseName).withCodecRegistry(codecRegistry)

  def getGameRepository: GameRepository = {
    new GameRepository(database)
  }

  def closeClient(): Unit = {
    client.close()
  }
}
