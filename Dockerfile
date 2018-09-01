FROM openjdk:8-jdk-alpine

ENV JAVA_OPTS '-DLOG_LEVEL=INFO'

ADD build/libs/*.jar /app.jar
