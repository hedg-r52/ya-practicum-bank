FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
RUN ./gradlew :bank-config-server:bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/bank-config-server/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
