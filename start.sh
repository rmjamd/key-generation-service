#!/bin/bash

# Specify the partial path to the JAR file
JAR_PATH="kgs-server/target/kgs-server.jar"

# Run the Spring Boot JAR in the background and capture its process ID
java -jar "$JAR_PATH" > /dev/null 2>&1 &
APP_PID=$!

# Display a message indicating that the Spring Boot application is started
echo "Spring Boot application started."

# Wait for a short period to ensure that the logs are generated
sleep 5

# Display the logs using 'tail' command
tail -f application.log
