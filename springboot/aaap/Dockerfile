FROM openjdk:17-jdk-alpine as builder
ARG BUILD_VERSION
ENV BUILD_VERSION=${BUILD_VERSION}
RUN mkdir -p /app/source
COPY .  /app/source
WORKDIR /app/source
RUN ./mvnw clean package

