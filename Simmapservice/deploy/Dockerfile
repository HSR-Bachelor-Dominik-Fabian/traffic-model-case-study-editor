FROM java:latest
WORKDIR /opt
ADD opt /opt
RUN ["chown", "-R", "daemon:daemon", "."]
EXPOSE 9000
USER daemon
CMD ["java", "-jar", "SimmapserviceConfig-assembly-1.0.jar", "server", "simmapservice.yml"]
LABEL servicetype="simmapservice"