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
    - **Path**: [binscale-consumer](https://github.com/fatimazahraelaaziz/Experience/tree/main/variableconsumer)
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

## Contributing

Feel free to submit issues and enhancement requests.
<<<<<<< HEAD

## ðŸ”§ Environment Variables

*This part is auto generated.*

| Name | Description | Default value |
|-----|--------------|-------------------|
| `DI` | DI value in milliseconds for the controller loop sleep time | *(undefined)* |
| `NUMBER_PARTITIONS` | Number of partitions for the topic | *(undefined)* |
| `REB_TIME` | REB_TIME value in seconds for the rebalancing time | *(undefined)* |
| `FUP` | FUP value for the upscaling threshold | *(undefined)* |
| `FDOWN` | FDOWN value for the downscaling threshold | *(undefined)* |
| `BOOTSTRAP_SERVERS` | Bootstrap servers. Example : 'localhost:9092' | *(undefined)* |
| `TOPICS_CONFIG_PATH` | Path to config file | "/config/controller-config.yaml" |
| `REQUEST_TIME_RANGE` | Range time in seconds for the metrics calculation | 2 |
| `REQUEST_TIME_UNIT` | Range time in seconds for the metrics calculation | "s" |
| `SCALING_STRATEGY` | Scaling strategy selector between : 'naive' | ScalingStrategyMapping.BINPACK_NAIVE, ScalingStrategyMapping::getByName |

## ðŸ”§ Environment Variables

*This part is auto generated.*

| Name | Description | Default value |
|-----|--------------|-------------------|
| `PARTITION_WEIGHTS` | List of partition weights, comma separated. Example : '1,1,1,1,1' | *(non dÃ©fini)* |
| `INPUT_WORKLOAD` | Input workload file name. Example : 'defaultArrivalRatesm.csv' | "defaultArrivalRatesm.csv" |
| `BOOTSTRAP_SERVERS` | Bootstrap servers, Example : 'localhost:9092' | *(non dÃ©fini)* |
| `DELAY_MS` | Delay between two messages in milliseconds. Example : 1000 | *(non dÃ©fini)* |
| `MESSAGES_COUNT` | Number of messages to send. Example : 10 | 10L |
| `MESSAGE` | Message content. Example : 'Hello World !' | "Hello World !" |
| `PRODUCER_ACKS` | Producer acks config. Example : '0', '1' or 'all' | "0" |
| `HEADERS` | Headers to add to each message, comma separated. Example : 'header1:value1,header2:value2' | "" |
| `ADDITIONAL_CONFIG` | Additional producer configuration in the form 'key1=value1,key2=value2' | "" |
| `WORKLOAD` | Workload mapping strategy. Example : 'constant' | "constant" |
| `SERVER_PORT` | Server port for the health check endpoint | 5002 |


## ðŸ”§ Environment Variables

*This part is auto generated.*

| Name | Description | Default value |
|-----|--------------|-------------------|
| `SCALE` | Scale parameter | *(undefined)* |
| `TIME_TO_COMMIT` | Time to commit parameter | *(undefined)* |
| `SHAPE` | Shape parameter | *(undefined)* |
| `WSLA_S` | WSLA parameter | *(undefined)* |
| `ASYNC_COMMIT` | Async commit parameter. Have the Kafka commit to be asynchronous? | *(undefined)* |
| `BOOTSTRAP_SERVERS` | Bootstrap servers, Example : 'localhost:9092' | *(undefined)* |
| `SLEEP` | Sleep time | 0 |
| `ADDITIONAL_CONFIG` | Additional consumer configuration in the form 'key1=value1,key2=value2' | "" |
| `MESSAGE_COUNT` | Message count | 10L |
| `CLIENT_RACK` | Client rack | null |
| `MAX_POLL_RECORDS` | Max poll records parameter. Max number of events returned in a call to Kafka topic. | 500 |
| `SESSION_TIMEOUT_MS` | Kafka session timeout in milliseconds | "3000" |
| `HEARTBEAT_INTERVAL_MS` | Heartbeat interval in milliseconds | "1000" |
| `PROCESSING_STRATEGY` | Processing strategy. Example : 'balanced', 'dupplicated', custom' | ProcessStrategyMapping.defaultStrategy, ProcessStrategyMapping::getByName |
| `HEADERS` | Headers to add to each message, comma separated. Example : 'header1:value1,header2:value2' | "" |
| `PRODUCER_ACKS` | Producer acks config. Example : '0', '1' or 'all' | "0" |
| `TOPICS_DISTRIBUTION_CONFIG_PATH` | Config path for Topics distribution | "/config/topics-config.yaml" |

## ðŸ”§ Environment Variables

*This part is auto generated.*

| Name | Description | Default value |
|-----|--------------|-------------------|
| `PARTITION_WEIGHTS` | List of partition weights, comma separated. Example : '1,1,1,1,1' | *(undefined)* |
| `INPUT_WORKLOAD` | Input workload file name. Example : 'defaultArrivalRatesm.csv' | "defaultArrivalRatesm.csv" |
| `BOOTSTRAP_SERVERS` | Bootstrap servers, Example : 'localhost:9092' | *(undefined)* |
| `DELAY_MS` | Delay between two messages in milliseconds. Example : 1000 | *(undefined)* |
| `MESSAGES_COUNT` | Number of messages to send. Example : 10 | 10L |
| `MESSAGE` | Message content. Example : 'Hello World !' | "Hello World !" |
| `PRODUCER_ACKS` | Producer acks config. Example : '0', '1' or 'all' | "0" |
| `HEADERS` | Headers to add to each message, comma separated. Example : 'header1:value1,header2:value2' | "" |
| `ADDITIONAL_CONFIG` | Additional producer configuration in the form 'key1=value1,key2=value2' | "" |
| `WORKLOAD` | Workload mapping strategy. Example : 'constant' | "constant" |
| `SERVER_PORT` | Server port for the health check endpoint | 5002 |

## ðŸ”§ Environment Variables

*This part is auto generated.*

| Name | Description | Default value |
|-----|--------------|-------------------|
| `DI` | DI value in milliseconds for the controller loop sleep time | *(undefined)* |
| `NUMBER_PARTITIONS` | Number of partitions for the topic | *(undefined)* |
| `REB_TIME` | REB_TIME value in seconds for the rebalancing time | *(undefined)* |
| `FUP` | FUP value for the upscaling threshold | *(undefined)* |
| `FDOWN` | FDOWN value for the downscaling threshold | *(undefined)* |
| `BOOTSTRAP_SERVERS` | Bootstrap servers. Example : 'localhost:9092' | *(undefined)* |
| `TOPICS_CONFIG_PATH` | Path to config file | "/config/controller-config.yaml" |
| `REQUEST_TIME_RANGE` | Range time in seconds for the metrics calculation | 2 |
| `REQUEST_TIME_UNIT` | Range time in seconds for the metrics calculation | "s" |
| `SCALING_STRATEGY` | Scaling strategy selector between : 'naive' | ScalingStrategyMapping.BINPACK_NAIVE, ScalingStrategyMapping::getByName |

## ðŸ”§ Environment Variables

*This part is auto generated.*

| Name | Description | Default value |
|-----|--------------|-------------------|
| `SCALE` | Scale parameter | *(undefined)* |
| `TIME_TO_COMMIT` | Time to commit parameter | *(undefined)* |
| `SHAPE` | Shape parameter | *(undefined)* |
| `WSLA_S` | WSLA parameter | *(undefined)* |
| `ASYNC_COMMIT` | Async commit parameter. Have the Kafka commit to be asynchronous? | *(undefined)* |
| `BOOTSTRAP_SERVERS` | Bootstrap servers, Example : 'localhost:9092' | *(undefined)* |
| `SLEEP` | Sleep time | 0 |
| `ADDITIONAL_CONFIG` | Additional consumer configuration in the form 'key1=value1,key2=value2' | "" |
| `MESSAGE_COUNT` | Message count | 10L |
| `CLIENT_RACK` | Client rack | null |
| `MAX_POLL_RECORDS` | Max poll records parameter. Max number of events returned in a call to Kafka topic. | 500 |
| `SESSION_TIMEOUT_MS` | Kafka session timeout in milliseconds | "3000" |
| `HEARTBEAT_INTERVAL_MS` | Heartbeat interval in milliseconds | "1000" |
| `PROCESSING_STRATEGY` | Processing strategy. Example : 'balanced', 'dupplicated', custom' | ProcessStrategyMapping.defaultStrategy, ProcessStrategyMapping::getByName |
| `HEADERS` | Headers to add to each message, comma separated. Example : 'header1:value1,header2:value2' | "" |
| `PRODUCER_ACKS` | Producer acks config. Example : '0', '1' or 'all' | "0" |
| `TOPICS_DISTRIBUTION_CONFIG_PATH` | Config path for Topics distribution | "/config/topics-config.yaml" |

## ðŸ”§ Environment Variables

*This part is auto generated.*

| Name | Description | Default value |
|-----|--------------|-------------------|
| `PARTITION_WEIGHTS` | List of partition weights, comma separated. Example : '1,1,1,1,1' | *(undefined)* |
| `INPUT_WORKLOAD` | Input workload file name. Example : 'defaultArrivalRatesm.csv' | "defaultArrivalRatesm.csv" |
| `BOOTSTRAP_SERVERS` | Bootstrap servers, Example : 'localhost:9092' | *(undefined)* |
| `DELAY_MS` | Delay between two messages in milliseconds. Example : 1000 | *(undefined)* |
| `MESSAGES_COUNT` | Number of messages to send. Example : 10 | 10L |
| `MESSAGE` | Message content. Example : 'Hello World !' | "Hello World !" |
| `PRODUCER_ACKS` | Producer acks config. Example : '0', '1' or 'all' | "0" |
| `HEADERS` | Headers to add to each message, comma separated. Example : 'header1:value1,header2:value2' | "" |
| `ADDITIONAL_CONFIG` | Additional producer configuration in the form 'key1=value1,key2=value2' | "" |
| `WORKLOAD` | Workload mapping strategy. Example : 'constant' | "constant" |
| `SERVER_PORT` | Server port for the health check endpoint | 5002 |
=======
>>>>>>> 646ddaabef899a07ef88cbe6dd3f1c87dfd7b6c8
