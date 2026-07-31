FROM eclipse-temurin:17-jdk

RUN apt-get update && apt-get install -y --no-install-recommends \
    openssl \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk-amd64

# Set default command and entrypoint
CMD ["echo", "No default command specified"]
ENTRYPOINT ["echo", "No entrypoint specified"]

# Copy producer JAR
COPY binscale-producer/target/binscale-producer-1.0-SNAPSHOT.jar /app/producer.jar

# Copy binscale-consumer JAR
COPY binscale-consumer/target/binscale-consumer-1.0-SNAPSHOT.jar /app/consumer.jar

# Copy log4j2.properties file
COPY binscale-controller/src/main/resources/log4j2.properties /bin/log4j2.properties
# Copy binscale-controller JAR
ADD binscale-controller/target/binscale-controller-1.0-SNAPSHOT.jar /app/Controller.jar

COPY binscale-e2e-analyzer/target/binscale-e2e-analyzer-1.0-SNAPSHOT.jar /app/e2e-analyzer.jar

COPY binscale-exporter/target/binscale-exporter-1.0-SNAPSHOT.jar /app/exporter.jar






