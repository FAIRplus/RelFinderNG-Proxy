# Maven 3.5.2
FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD

MAINTAINER Novartis

# cp pom.xml
COPY pom.xml /build/

# cp source files
COPY src /build/src/

WORKDIR /build/

# build jar
RUN mvn clean package

# Java 8
FROM openjdk:8-jdk-alpine

# Add curl tool
RUN apk --no-cache add curl

# Add user relfinder
RUN addgroup -S relfinder && adduser -S relfinder -G relfinder
USER relfinder:relfinder

# cd /opt/app
WORKDIR /opt/app

COPY --from=MAVEN_BUILD /build/target/*.jar proxyserver.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","proxyserver.jar"]

EXPOSE 8080