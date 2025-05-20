FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
RUN ./gradlew :bank-exchange-generator:bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/bank-exchange-generator/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]