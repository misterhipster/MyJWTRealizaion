FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
# Просто пропускаем тесты и надеемся, что соберется
RUN mvn clean compile -DskipTests && mvn package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]