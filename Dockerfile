FROM openjdk:8
COPY karmaquest-service-0.0.1-SNAPSHOT.jar /opt/
EXPOSE 9060
CMD ["java", "-XX:+PrintFlagsFinal", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/opt/karmaquest-service-0.0.1-SNAPSHOT.jar"]

