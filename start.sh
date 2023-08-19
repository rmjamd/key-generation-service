#!/bin/bash

# Specify the partial path to the JAR file
JAR_PATH="kgs-server/target/kgs-server.jar"

# Run the Spring Boot JAR with the appropriate Java command
java -jar "$JAR_PATH" > /dev/null 2>&1 &

echo "Spring Boot application started."
