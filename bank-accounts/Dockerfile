FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
RUN ./gradlew :bank-accounts:bootJar

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/bank-accounts/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
