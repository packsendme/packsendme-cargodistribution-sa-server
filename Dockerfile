
FROM openjdk:8-jdk-alpine
EXPOSE 9008
COPY /target/packsendme-roadwaymanager-server-0.0.1-SNAPSHOT.jar packsendme-roadwaymanager-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/packsendme-roadwaymanager-server-0.0.1-SNAPSHOT.jar"]