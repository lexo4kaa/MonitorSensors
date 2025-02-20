FROM openjdk:17
COPY target/MonitorSensors-1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080