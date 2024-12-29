FROM openjdk:17
ARG JAR_FILE_ARG
COPY ./target /opt
COPY ./target/${JAR_FILE_ARG} /opt/app.jar
WORKDIR /opt
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./app.jar"]
