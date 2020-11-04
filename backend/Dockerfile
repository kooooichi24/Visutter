FROM adoptopenjdk/openjdk11:alpine
RUN mkdir /opt/app
COPY target/Visutter-backend-0.0.1-SNAPSHOT.jar /opt/app
CMD ["java", "-jar", "/opt/app/Visutter-backend-0.0.1-SNAPSHOT.jar"]
