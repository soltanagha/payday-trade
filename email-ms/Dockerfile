FROM eclipse-temurin:17

ARG APP_NAME="email-ms"
ARG APP_VERSION="0.0.5"
ARG JAR_FILE="/build/libs/${APP_NAME}-${APP_VERSION}.jar"

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "app.jar"]