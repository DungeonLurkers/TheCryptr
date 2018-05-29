FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/libs/cryptr-1.0-SNAPSHOT.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]