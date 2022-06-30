FROM openjdk:8-jdk-alpine
EXPOSE 8091
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ram-kpi-user-management-ms.jar
ENTRYPOINT ["java","-jar","/ram-kpi-user-management-ms.jar"]

