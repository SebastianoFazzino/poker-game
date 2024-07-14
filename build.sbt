ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

lazy val root = (project in file("."))
  .settings(
    name := "poker-game",
    libraryDependencies ++= Seq(

      // MongoDB Scala Driver
      "org.mongodb.scala" %% "mongo-scala-driver" % "5.1.2",

      // SLF4J logging dependencies
      "org.slf4j" % "slf4j-api" % "2.0.12",
      "org.slf4j" % "slf4j-simple" % "2.0.13",

      // Config for configuration management
      "com.typesafe" % "config" % "1.4.3",

      // Testing dependencies
      "org.scalatest" %% "scalatest" % "3.2.19" % Test,
      "org.mockito" %% "mockito-scala" % "1.17.37" % Test
    )
  )
