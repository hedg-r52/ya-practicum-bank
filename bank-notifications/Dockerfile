FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
RUN sed -i 's/\r$//' gradlew
RUN ./gradlew :bank-notifications:bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/bank-notifications/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]