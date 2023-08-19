#!/bin/bash

# Specify the partial path to the JAR file
JAR_PATH="kgs-server/target/kgs-server.jar"

# Find the process ID (PID) of the running Java application
PID=$(pgrep -f "$JAR_PATH")

if [ -z "$PID" ]; then
    echo "No running Spring Boot application found."
else
    # Stop the Java process
    kill "$PID"
    echo "Spring Boot application stopped."
fi
