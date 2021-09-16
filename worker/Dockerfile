FROM --platform=linux/x86_64 adoptopenjdk/openjdk11:alpine-jre

LABEL maintainer="Gustavo Flôr <ogustaflor@gmail.com>"

ENV JAVA_OPTS=""

WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup && chown -R appuser:appgroup /app

USER appuser

COPY --chown=appuser:appgroup /target/*.jar /app/worker.jar

EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar worker.jar $0 $@