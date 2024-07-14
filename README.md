# Simplified Poker Game in Scala

## Overview

This project is a Scala implementation of a simplified poker game. It demonstrates various aspects of Scala programming through the development of a functional poker game, covering both the game domain and persistence features.

## Key Features
- Poker Game Mechanics: Simulates poker rules and gameplay, including card dealing, hand evaluation, and determining winners.
- Domain Model: Implements essential components such as cards, hands, decks, and players.
- Persistence Layer: Uses MongoDB to persist game data.

## Technologies Used
- **Scala**: A functional programming language that runs on the JVM.
- **MongoDB**: A NoSQL database used for storing game data.
- **SBT**: A build tool for Scala projects.

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Dependencies](#project-dependencies)
- [Installation](#installation)
- [Summary Table](#summary-table)

## Features

### Domain Model
- **Game Rules:** Simplified Poker for one player and one dealer.
- **Game Flow:**
    1. Deal 2 cards face down for the dealer.
    2. Deal 2 cards face up for the player.
    3. Ask the player for a bet amount.
    4. Deal 3 community cards face up.
    5. Determine the winner based on the hand types: High Card, One Pair, Two Pairs, Three of a Kind, Straight, Flush.
    6. Ask if the player would like to play another round.

### Persistence Layer
- **Persistence:** Stores game rounds data using MongoDB.

## Project Structure

- **`build.sbt`**: SBT build configuration file for project dependencies and settings.
- **`project/build.properties`**: File for specifying the SBT version used in the project.
- **`src/main/scala/`**: Contains the main source code.
  - **`models/`**: Includes the game models and domain logic.
  - **`repositories/`**: Contains MongoDB repository and document models.
  - **`services/`**: Game service with core functionality.
- **`src/main/resources/`**: Contains configuration files for MongoDB.
- **`src/test/scala/`**: Contains test code.
  - **`services/`**: Includes tests for game service.
  - **`models/`**: Includes tests for game models.

## Project Dependencies

The project dependencies are managed via `build.sbt` and will be automatically resolved by SBT when you run the build commands. Here are the main dependencies:

- **MongoDB Scala Driver**:
  ```scala
  "org.mongodb.scala" %% "mongo-scala-driver" % "5.1.2"
  ```
- **SL4J Logging**:
  ```scala
  "org.slf4j" % "slf4j-api" % "2.0.12"
  "org.slf4j" % "slf4j-simple" % "2.0.13"
  ```
- **ScalaTest**:
  ```scala
  "org.scalatest" %% "scalatest" % "3.2.19" % Test
  ```
- **Mockito**:
  ```scala
  "org.mockito" %% "mockito-scala" % "1.17.37" % Test
  ```
- **Configuration Management**:
  ```scala
  "com.typesafe" % "config" % "1.4.3"
  ```

## Installation

To set up and run the project, follow these steps:

### 1. Clone the Repository

First, clone the repository to your local machine:

```sh
git clone https://github.com/SebastianoFazzino/poker-game.git
cd poker-game
```

### 2. Install Dependencies

To get started with the project, you need to install the following dependencies:

#### Prerequisites

1. **[SBT](https://www.scala-sbt.org/)** - The Scala Build Tool is used for managing the project dependencies and building the project.
  
2. **[MongoDB](https://www.mongodb.com/)** - A NoSQL database used for persisting game data.
    - **Alternative (Using Docker)**:
      - You can run MongoDB on Docker with the following command:
        ```bash
        docker pull mongo
        docker run -d -p 27017:27017 --name poker-mongo mongo
        ```
        
#### Compatability
**Please note that the [MongoDB Scala driver](https://www.mongodb.com/docs/languages/scala/scala-driver/current/installation/) does not support Scala 3.**

### 3. Configure MongoDB
By default, the application connects to MongoDB running on localhost:27017.
<br>
If you need to change the connection settings, update the `application.conf` file in the src/main/resources directory.

### 4. Build the Project
Once you have cloned the repository and installed the dependencies, build the project with:

```sh
sbt compile
```

### 5. Run the Project
To run the project, execute:

```sh
sbt run
```
This will start the application, connect to the MongoDB instance and ask you to enter your name to start a new game round.

### 6. Run Tests
To run the tests, execute:

```sh
sbt test
```

## Summary Table

| Step                         | Command                                                                            |
|------------------------------|------------------------------------------------------------------------------------|
| **Clone the Repository**     | `git clone https://github.com/SebastianoFazzino/poker-game.git`<br>`cd poker-game` |
| **Install SBT**              | [SBT Installation](https://www.scala-sbt.org/)                                     |
| **Install MongoDB**          | [MongoDB Installation](https://www.mongodb.com/)                                   |
| **Run MongoDB on Docker**    | `docker pull mongo`<br>`docker run -d -p 27017:27017 --name poker-mongo mongo`     |
| **Build the Project**        | `sbt compile`                                                                      |
| **Run the Project**          | `sbt run`                                                                          |
| **Run Tests**                | `sbt test`                                                                         |
| **Clean the Project**        | `sbt clean`                                                                        |
| **Update Dependencies**      | `sbt update`                                                                       |
| **Package the Project**      | `sbt package`                                                                      |