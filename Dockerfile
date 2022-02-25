FROM openjdk:11
VOLUME /tmp

ENV APPLICATION_NAME=fibonacci

COPY target/${APPLICATION_NAME}*.jar ${APPLICATION_NAME}-exec.jar
ENV JAVA_OPTS=""
ENTRYPOINT java \
      -jar ${APPLICATION_NAME}-exec.jar
