FROM openjdk:18-jdk-alpine
#ARG JAR_FILE=target/../../centrum-dobrej-terapii-0.0.1-SNAPSHOT.jar
WORKDIR /home/spring-app
COPY centrum-dobrej-terapii-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/spring-app/app.jar"]
#CMD nc -l 5000