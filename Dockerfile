FROM java:8
RUN mkdir -p /usr/src/DockerServiceManager
COPY target/*jar-with-dependencies.jar /usr/src/DockerServiceManager
WORKDIR /usr/src/DockerServiceManager
CMD ["java -jar ", "DockerServiceManager-1.0-SNAPSHOT-jar-with-dependencies.jar"]