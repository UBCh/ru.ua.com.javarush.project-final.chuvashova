#FROM openjdk:21-buster
#ARG JAR_FILE=target/*.jar
#COPY target /
#COPY ${JAR_FILE} jira-1.0.jar
#ENTRYPOINT ["java","-jar","/jira-1.0.jar"]

#FROM openjdk:20-buster
#COPY target/ /opt/app/
#ENTRYPOINT ["java","-jar","/opt/app/jira-1.0.jar"]

#FROM openjdk:20-buster
#RUN apt-get update  && DEBIAN_FRONTEND=noninteractive   apt-get install -y maven   && apt-get clean   && rm -rf /var/lib/apt/lists/*
#RUN mvn package
#ENTRYPOINT ["java","-jar","/opt/app/jira-1.0.jar"]

FROM  eclipse-temurin:20
ARG JAR_FILE=target/*.jar
COPY target /
COPY ${JAR_FILE} jira-1.0.jar
ENTRYPOINT ["java","-jar","/jira-1.0.jar"]