FROM openjdk:8-jdk-alpine
EXPOSE 8089
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ram-kpi-business-ms.jar
ENTRYPOINT ["java","-jar","/ram-kpi-business-ms.jar"]
