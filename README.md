# Overview

This repository contains a set of microservices designed for different scenarios, including a single consumer
microservice and a DAG of multiple consumer microservices. The project utilizes gRPC for communication between the
different projects.

## Repository Structure

### Single Microservice Consumer

1. **Binscale Common**
    - **Path**: [binscale-common](https://github.com/benneuville/binscale-experience/tree/main/binscale-common)

2. **Binscale Controller**
    - **Path**: [binscale-controller](https://github.com/benneuville/binscale-experience/tree/main/binscale-controller)
    - **Description**: This repository contains the controller responsible for implementing the bin packing algorithm.

3. **Consumer**
    - **Path**: [binscale-consumer](https://github.com/benneuville/binscale-experience/tree/main/binscale-consumer)
    - **Description**: This repository contains the consumer microservice implemented using Kafka.

4. **Producer**
    - **Path**: [binscale-producer](https://github.com/benneuville/binscale-experience/tree/main/binscale-producer)
    - **Description**: The producer implementation remains unchanged.

## Getting Started

### Prerequisites

- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (version 11)
- [Kafka](https://kafka.apache.org/)
- [Maven](https://maven.apache.org/) (for building the project)
- [Docker](https://www.docker.com/) (for containerized deployment)
- [gRPC](https://grpc.io/) (for communication between projects)

### Installation

#### Build the project using Maven:

To skip environment variables documentation génération in the README

```sh
mvn -D skip.env.doc=true clean install
```

### Dockerfile

This project includes a [Dockerfile](https://github.com/benneuville/binscale-experience/blob/main/Dockerfile) to
containerize the microservices. Using this Dockerfile, you can build a single Docker image for all the microservices
mentioned in this repository.

1. Build Docker Image:

```sh
docker build -t <username>/<image-name>:<tag> .
```

2. Push Docker Image to Docker Hub:

```sh
docker push <username>/<image-name>:<tag>
```

## Usage

1. **Single Consumer Scenario**:
    - The producer sends messages to the Kafka topic.
    - The variable consumer listens to the Kafka topic and processes the messages.
    - The binscale controller handles the bin packing algorithm logic.

2. **DAG of Multiple Consumers Scenario**:
    - The producer sends messages to the Kafka topic.
    - The graph controller models the graph of multiple consumer microservices.
    - The multiple consumers listen to the Kafka topic and process the messages according to the graph model.
