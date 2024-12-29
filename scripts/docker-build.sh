#!/bin/bash
mvn -B clean package --file pom.xml
app_version=$(ls -t target | grep zeuspol | head -n1)
echo "$app_version"
sudo docker build --no-cache --build-arg JAR_FILE_ARG="$app_version" -t weedesigners/zeuspol:latest .