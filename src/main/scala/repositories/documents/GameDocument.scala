package repositories.documents

import org.mongodb.scala.bson.ObjectId

import java.time.LocalDateTime

case class GameDocument(
                         _id: Option[ObjectId] = None,
                         gameDateTime: LocalDateTime,
                         players: List[PlayerDocument],
                         dealer: DealerDocument,
                         communityCards: List[String],
                         winner: String,
                         higherScore: String,
                         pot: Int,
                       )
