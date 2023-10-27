FROM logstash:6.6.1

RUN bin/logstash-plugin install logstash-input-jdbc

COPY ./mysql-connector-java-8.0.15.jar /opt/mysql-connector-java-8.0.15.jar