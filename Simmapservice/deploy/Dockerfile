FROM java:latest
WORKDIR /opt
ADD opt /opt
RUN ["chown", "-R", "daemon:daemon", "."]
EXPOSE 9001
USER daemon
CMD ["java", "-jar", "simmapservice.jar", "server", "simmapservice.yml"]
LABEL servicetype="simmapservice"