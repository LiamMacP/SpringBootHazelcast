FROM gradle:7.1.1-jdk11 AS BUILD

COPY . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle assemble --no-daemon

FROM openjdk:11.0.12-jre-slim-buster

ENV USER java_user
ENV USER_ID 1000
ENV GROUP group_java_user

RUN addgroup --system ${GROUP} && \
    adduser --system --home /app -uid ${USER_ID} --ingroup ${GROUP} ${USER} && \
    mkdir -p /app && \
    chown -R ${USER}:${GROUP} /app

EXPOSE 8080

USER ${USER}

COPY --from=BUILD /home/gradle/src/build/libs/SpringBootHazelcast-0.0.1-SNAPSHOT.jar /app/SpringBootHazelcast-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "/app/SpringBootHazelcast-0.0.1-SNAPSHOT.jar"]
