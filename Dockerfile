FROM ubuntu:22.04

ARG JAR_FILE=target/*.jar
COPY ./target/springelasticsearch-0.0.1.jar app.jar

RUN apt update
RUN apt upgrade -y

#install jdk,jre 17
RUN apt install openjdk-17-jre -y
RUN apt install openjdk-17-jdk -y

ENTRYPOINT ["java","-jar","/app.jar"]

