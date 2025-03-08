FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /MonitorSensors
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17
COPY --from=build /MonitorSensors/target/MonitorSensors-1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080