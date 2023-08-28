FROM  eclipse-temurin:20
ARG JAR_FILE=target/*.jar
COPY target /
COPY ${JAR_FILE} jira-1.0.jar
ENTRYPOINT ["java","-jar","/jira-1.0.jar"]