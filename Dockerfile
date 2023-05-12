FROM openjdk:11-jdk
LABEL maintainer="email"
ARG JAR_FILE=build/libs/fkream-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} docker-springboot.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/docker-springboot.jar"]

ENTRYPOINT ["java","-jar",\
"-javaagent:./pinpoint/pinpoint-bootstrap-2.2.2.jar",\
"-Dpinpoint.agentId=gjgs01","-Dpinpoint.applicationName=gjgs",\
"-Dpinpoint.config=./pinpoint/pinpoint-root.config"\
,"docker-springboot.jar"]