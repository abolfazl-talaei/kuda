#!/bin/bash
echo "Start building the backend application"
cd $PWD/backend
mvn clean package -Dspring.profiles.active=main -DskipTests
echo "Java application was built successfully"
cd ..
echo "Runnng docker compose for bulding and runnng the application"
docker-compose up