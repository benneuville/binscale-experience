# Overview

This repository contains a set of microservices designed for different scenarios, including a single consumer
microservice and a DAG of multiple consumer microservices. The project utilizes gRPC for communication between the
different projects.

## Repository Structure

### Single Microservice Consumer

1. **Binscale Controller**
    - **Path**: [binscale-controller](https://github.com/fatimazahraelaaziz/Experience/tree/main/integrationcontroller)
    - **Description**: This repository contains the controller responsible for implementing the bin packing algorithm.

2. **Variable Consumer**
    - **Path**: [variableconsumer](https://github.com/fatimazahraelaaziz/Experience/tree/main/variableconsumer)
    - **Description**: This repository contains the consumer microservice implemented using Kafka.

3. **Producer**
    - **Path**: [binscale-producer](https://github.com/fatimazahraelaaziz/Experience/tree/main/produceri3s)
    - **Description**: This repository models the producer microservice using Apache Kafka.

### DAG of Multiple Microservice Consumers

1. **Graph Controller**
    - **Path**: [graphcontroller2](https://github.com/fatimazahraelaaziz/Experience/tree/main/graphcontroller2)
    - **Description**: This repository contains the controller responsible for modeling the graph for the scenario
      involving multiple consumer microservices.

2. **Multiple Consumers**
    - **Path**: [multipleconsumers](https://github.com/fatimazahraelaaziz/Experience/tree/main/multipleConsumers)
    - **Description**: This repository contains the implementation of the consumer microservices for the DAG scenario.

3. **Producer**
    - **Path**: [binscale-producer](https://github.com/fatimazahraelaaziz/Experience/tree/main/produceri3s)
    - **Description**: The producer implementation remains unchanged.

## Getting Started

### Prerequisites

- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (version 11 or later)
- [Kafka](https://kafka.apache.org/)
- [Maven](https://maven.apache.org/) (for building the project)
- [Docker](https://www.docker.com/) (for containerized deployment)
- [gRPC](https://grpc.io/) (for communication between projects)

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/fatimazahraelaaziz/Experience.git
    cd Experience
    ```

2. Build the project using Maven:
    ```sh
    cd Experience
    mvn clean install
    ```

### Dockerfile

This project includes a [Dockerfile](https://github.com/fatimazahraelaaziz/Experience/blob/main/Dockerfile) to
containerize the microservices. Using this Dockerfile, you can build a single Docker image for all the microservices
mentioned in this repository.

1. Build Docker Image:

```sh
docker build -t <image-name>:<tag> .
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

## Contributing

Feel free to submit issues and enhancement requests.


## 🔧 Environment Variables

*This part is auto generated.*

| Name | Description | Default value |
|-----|--------------|-------------------|
| `DI` | DI value in seconds for the controller loop sleep time | *(non défini)* |
| `WSLA` | WSLA value in seconds | *(non défini)* |
| `REB_TIME` | REB_TIME value in seconds for the rebalancing time | *(non défini)* |
| `MU` | MU value in seconds for the controller calculations | *(non défini)* |
| `FUP` | FUP value for the upscaling threshold | *(non défini)* |
| `FDOWN` | FDOWN value for the downscaling threshold | *(non défini)* |
| `INIT_SIZE` | Initial size of the consumer group | *(non défini)* |
| `BOOTSTRAP_SERVERS` | Bootstrap servers. Example : 'localhost:9092' | *(non défini)* |
| `TOPIC` | Topic name. Example : 'testtopic1' | *(non défini)* |
| `GROUP_ID` | Group id. Example : 'testgroup1' | *(non défini)* |
| `NUMBER_PARTITIONS` | Number of partitions for the topic | *(non défini)* |

