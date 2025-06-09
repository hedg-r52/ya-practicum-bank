FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app
COPY . .
RUN sed -i 's/\r$//' gradlew
RUN ./gradlew :bank-front-ui:bootJar

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/bank-front-ui/build/libs/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
