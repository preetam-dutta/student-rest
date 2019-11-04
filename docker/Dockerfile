FROM ubuntu:18.04

RUN addgroup --system --gid 500 appgroup \
  && adduser --system --home /home/appuser --uid 500 --gid 500 appuser \
  && apt-get update \
  && apt-get install dnsutils -y \
  && apt-get install openjdk-11-jdk -y

WORKDIR /usr/local/bin/
COPY student-rest.jar liveness.sh /usr/local/bin/
RUN chmod 755 student-rest.jar liveness.sh

USER appuser:appgroup
WORKDIR /home/appuser
# If you need to override default configuration, replace the below in the ENTRYPOINT
# java -jar student-rest.jar --spring.config.location=classpath:/another-location.properties
ENTRYPOINT java -jar /usr/local/bin/student-rest.jar