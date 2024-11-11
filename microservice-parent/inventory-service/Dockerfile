# -------------------
# Build Stage
# -------------------
FROM gradle:8-jdk22-alpine AS builder

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle build -x test

# -------------------
# Packaging Stage
# -------------------
FROM openjdk:22-jdk

RUN mkdir "/app"

COPY --from=builder /home/gradle/src/build/libs/*.jar /app/inventory-service.jar

ENV POSTGRES_DB_USERNAME=admin
ENV POSTGRES_DB_PASSWORD=password

EXPOSE 8093

ENTRYPOINT ["java", "-jar", "/app/inventory-service.jar"]