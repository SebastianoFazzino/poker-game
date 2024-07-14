package repositories.documents

case class PlayerDocument(
                      name: String,
                      chips: Int,
                      hand: List[String],
                      handScore: String
                      )
