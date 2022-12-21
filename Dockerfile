FROM openjdk:18-jdk-alpine as builder

RUN mkdir -p /app/source
COPY .  /app/source
WORKDIR /app/source
RUN ./mvnw clean package


FROM openjdk:18-jdk-alpine

RUN mkdir -p /app
COPY --from=builder /app/source/target/*.jar /app/app.jar
EXPOSE 8010
ENTRYPOINT ["java", "-jar", "/app/app.jar","-Duser.timezone=UTC" ]
