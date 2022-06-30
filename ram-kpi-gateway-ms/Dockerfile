FROM openjdk:8-jdk-alpine
EXPOSE 8090
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ram-kpi-gateway-ms.jar
ENTRYPOINT ["java","-jar","/ram-kpi-gateway-ms.jar"]

