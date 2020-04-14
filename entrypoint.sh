#!/bin/sh -x
set -e

if [ -z "$SPRING_DATASOURCE_URL" ]; then
  SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5434/omnibots
fi

if [ -z "$SPRING_DATASOURCE_USERNAME" ]; then
  SPRING_DATASOURCE_USERNAME=omnibots
fi

if [ -z "$SPRING_DATASOURCE_PASSWORD" ]; then
  SPRING_DATASOURCE_PASSWORD=123456
fi

if [ -z "$SERVER_PORT" ]; then
  SERVER_PORT=8080
fi

if [ -z "$STORAGE_PATH" ]; then
  STORAGE_PATH=/tmp/imstagram
fi


exec java ${JAVA_OPTS} -jar \
 -Dserver.port=${SERVER_PORT} \
 -Dspring.datasource.url=${SPRING_DATASOURCE_URL} \
 -Dspring.datasource.username=${SPRING_DATASOURCE_USERNAME} \
 -Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD} \
 -Dapplication.storage.path=${STORAGE_PATH} \
 app.jar