FROM openjdk:alpine

ENV APP_VERSION 0.0.1-SNAPSHOT

RUN mkdir /usr/share/app
RUN mkdir /usr/share/app/logs

VOLUME /usr/share/app/logs

WORKDIR /usr/share/app/

# set up docker entrypoint script
COPY entrypoint.sh /usr/share/app/entrypoint.sh
RUN chmod +x /usr/share/app/entrypoint.sh

COPY ./build/libs/server-${APP_VERSION}.jar /usr/share/app/app.jar

# application port
EXPOSE 8080
EXPOSE 8083
# debug port
EXPOSE 8000
EXPOSE 5005

ENTRYPOINT ["/usr/share/app/entrypoint.sh"]
