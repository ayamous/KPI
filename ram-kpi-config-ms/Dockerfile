FROM openjdk:8-jdk-alpine
EXPOSE 8012
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ram-kpi-config-ms.jar
ENTRYPOINT ["java","-jar","/ram-kpi-config-ms.jar"]

