ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

lazy val root = (project in file("."))
  .settings(
    name := "poker-game",
      libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test,
      libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "5.1.2",
      libraryDependencies += "org.slf4j" % "slf4j-api" % "2.0.12",
      libraryDependencies += "org.slf4j" % "slf4j-simple" % "2.0.13",
      libraryDependencies += "com.typesafe" % "config" % "1.4.3"
  )
