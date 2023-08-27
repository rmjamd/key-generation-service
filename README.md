# Twitter Snowflake with ZooKeeper Implementation

## Introduction

This project demonstrates the implementation of Twitter Snowflake for generating unique IDs in a distributed system using Apache ZooKeeper as the coordination service. Snowflake is a distributed unique ID generation system that provides a simple way to generate unique IDs across multiple nodes without conflicts.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage and Benefits](#usage-and-benefits)
- [Configuration](#configuration)
- [Postman Collection](#postman-collection)

## Features

- Distributed unique ID generation.
- Utilizes Twitter Snowflake algorithm.
- Coordination and synchronization with Apache ZooKeeper.
- High scalability and fault tolerance.
- Simple integration into your Java application.

## Requirements
Before you proceed with setting up the project, make sure you have the following installed on your system:

- [Java 8+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Apache ZooKeeper](https://zookeeper.apache.org/releases.html) (running instance or ensemble)
- [Maven](https://maven.apache.org/download.cgi)

## Installation

1. Clone this repository:
   ```bash
   git clone git@github.com:rmjamd/key-generation-service.git
   ```
2. Navigate to the project directory:
    ```bash
   cd key-generation-service
    ```
3. create the jar and run the jar
    ```bash
   maven clean package 
   java -jar kgs-server/target/kgs-server.jar --server.port=8081
   # you can run multiple jar in different port
    ```
   ![Sample Output](https://example.com/path/to/sample-output-screenshot.png)
## Usage and Benefits

1. **Distributed Unique ID Generation**: Twitter Snowflake offers a solution for generating globally unique IDs in distributed systems.

2. **Database Sharding**: Simplify database sharding by generating unique IDs for records on each shard.

3. **Ordered IDs**: Snowflake IDs include a timestamp, aiding in ordering records based on creation time.

4. **Data Integrity**: Prevent data duplication by ensuring each record has a unique ID.

5. **Horizontal Scaling**: Generate IDs independently on each instance when scaling horizontally.

6. **Microservices Architecture**: Use Snowflake IDs for unique identifiers in microservices.

7. **Performance**: Efficiently generate compact Snowflake IDs for high-throughput applications.

8. **Reliability**: Rely on central coordination (e.g., ZooKeeper) for consistent ID generation.

9. **Decentralized Key Generation**: Avoid single points of failure with distributed key generation.

10. **Global Scale**: Snowflake scales globally, suitable for applications spanning regions and time zones.

11. **Avoid Collisions**: Snowflake's composition minimizes the likelihood of ID collisions.

## Postman Collection
[collections](URL)
