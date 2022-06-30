FROM openjdk:8-jdk-alpine
EXPOSE 8761
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ram-kpi-discovery-ms.jar
ENTRYPOINT ["java","-jar","/ram-kpi-discovery-ms.jar"]
