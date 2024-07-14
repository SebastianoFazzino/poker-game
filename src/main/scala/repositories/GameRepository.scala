package repositories

import com.typesafe.config.ConfigFactory
import org.mongodb.scala._
import org.mongodb.scala.bson.ObjectId
import repositories.documents.GameDocument

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class GameRepository(database: MongoDatabase) {

  private val config = ConfigFactory.load()
  private val collectionName: String = config.getString("mongo.collectionName")

  private val collection: MongoCollection[GameDocument] = database.getCollection(collectionName)

  def saveGame(gameDoc: GameDocument): Future[Unit] = {
    val updatedGameDoc = gameDoc.copy(_id = Some(new ObjectId()))
    collection.insertOne(updatedGameDoc).toFuture().map(_ => ())
  }

}
