FROM openjdk:11-jdk
LABEL maintainer="email"
ARG JAR_FILE=build/libs/fkream-0.0.1-SNAPSHOT.jar
ARG CONFIG=application.yml
ADD ${JAR_FILE} docker-springboot.jar
ADD ${CONFIG} application.yml
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/docker-springboot.jar", "--spring.config.location=application.yml]
