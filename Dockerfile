FROM maven:3-openjdk-11 as builder
WORKDIR /home/mvn/project
COPY . .
RUN --mount=type=cache,target=/root/.m2 mvn clean install

FROM openjdk:11.0.7-jre-slim-buster
ARG BUILDER_WORKDIR=/home/mvn/project
ARG ARTIFACT_NAME=demo-0.0.1-SNAPSHOT.jar
WORKDIR /home/app

COPY --from=builder $BUILDER_WORKDIR/target/$ARTIFACT_NAME app.jar

ENTRYPOINT ["java","-jar","app.jar"]