FROM adoptopenjdk/openjdk11:alpine
ARG JAR_FILE=target/app.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar --datadog-api-key=${DATADOG_API_KEY} --datadog-app-key=${DATADOG_APP_KEY}"]
