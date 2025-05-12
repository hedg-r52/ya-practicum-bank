FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
RUN ./gradlew :bank-cash:bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/bank-cash/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
