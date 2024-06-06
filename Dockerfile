FROM openjdk:17
COPY ./target /opt
WORKDIR /opt
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./zeuspol-0.0.1-SNAPSHOT.jar"]
