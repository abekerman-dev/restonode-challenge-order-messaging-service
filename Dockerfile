FROM maven:3.5.4-jdk-8-alpine
WORKDIR /usr/src/restonode-order-messaging-service
COPY pom.xml .
COPY src ./src
RUN mvn package
WORKDIR /usr/bin/restonode-order-messaging-service
RUN cp /usr/src/restonode-order-messaging-service/target/*.jar ./app.jar
CMD ["java", "-jar", "app.jar"]