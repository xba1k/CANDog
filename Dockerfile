FROM adoptopenjdk/openjdk11:alpine
ARG JAR_FILE=target/app.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
