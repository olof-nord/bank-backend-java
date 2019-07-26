FROM openjdk:11-jre

ARG JAR_FILE=target/backend-1.0.0-SNAPSHOT.jar
ADD ${JAR_FILE} usr/app/backend.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "usr/app/backend.jar"]

