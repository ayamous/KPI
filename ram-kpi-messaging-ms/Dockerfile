FROM openjdk:8-jdk-alpine
EXPOSE 9093
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ram-kpi-messaging-ms.jar
ENTRYPOINT ["java","-jar","/ram-kpi-messaging-ms.jar"]

