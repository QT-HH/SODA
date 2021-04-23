FROM openjdk:8-jdk-alpine 
VOLUME /tmp
ADD ./target/soda-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
