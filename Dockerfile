FROM openjdk:11-jre-slim

ENV JAVA_OPTS: "-XX:MaxRAMPercentage=80 -XshowSettings:vm -XX:+ExitOnOutOfMemoryError"

RUN addgroup --system --gid 1001 app
RUN adduser --system --uid 1001 --group app
RUN mkdir /opt/app

COPY ./build/libs/cdp-kafka-stream-query-*SNAPSHOT.jar /opt/app/app.jar
RUN chown -R app:app /opt/app
RUN chmod +x /opt/app/app.jar

EXPOSE 8080

USER app

ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
