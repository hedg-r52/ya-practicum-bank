FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY bank-gateway ./bank-gateway
RUN ./gradlew :bank-gateway:bootJar

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/bank-gateway/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
