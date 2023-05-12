FROM openjdk:11-jdk
LABEL maintainer="email"
ARG JAR_FILE=build/libs/fkream-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} docker-springboot.jar
COPY pinpoint-agent-2.2.2/pinpoint-bootstrap-2.2.2.jar /pinpoint-agent-2.2.2.jar
COPY pinpoint-agent-2.2.2/pinpoint-root.config /pinpoint-root.config
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/docker-springboot.jar"]
#ENTRYPOINT ["java", "-jar", "-javaagent:/pinpoint-agent-2.2.2.jar",\
# "-Dpinpoint.agentId=gjgs01", "-Dpinpoint.applicationName=gjgs",\
# "-Dpinpoint.config=/pinpoint-root.config", "docker-springboot.jar"]