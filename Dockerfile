FROM openjdk:11-jre
MAINTAINER Olof Nord

ARG APP_VERSION=1.0.0
ARG APP_NAME=fake-bank-backend
ARG JAR_FILE=target/${APP_NAME}-${APP_VERSION}.jar

ADD ${JAR_FILE} usr/app/backend.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "usr/app/backend.jar"]

