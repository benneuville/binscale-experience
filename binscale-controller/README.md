# Controller

The `binscale-controller` project is a Java-based application designed to manage the scaling of consumer groups
dynamically. It leverages Kafka for message processing and includes configurable parameters for workload balancing and
scaling strategies.

## Features

- **Dynamic Scaling**: Supports upscaling and downscaling of consumer groups based on workload.
- **Environment Configuration**: Easily configurable via environment variables.
- **Kafka Integration**: Manages consumer groups and partitions for efficient message processing.

## Requirements

- **Java**: Version 11
- **Maven**: For dependency management and build

## Installation

1. Build `binscale-common`
    ```sh
    cd ./binscale-common
    mvn clean install
    ```
2. Build `binscale-controller`
    ```sh
    cd ./binscale-controller
    mvn clean compile
    ```

## Usage

Deployed on a Kubernetes cluster.  
Refer to the [deployment GitHub repository](https://github.com/benneuville/binscale-deployment).

Deployment file related to the
controller [here](https://github.com/benneuville/binscale-deployment/blob/master/experience/controller.yaml).

## Scaling Parameters

The project supports the following scaling parameters:

- DI (Controller loop sleep time)
- REB_TIME (Rebalancing time)
- FUP (Upscaling threshold)
- FDOWN (Downscaling threshold)

It also takes a YAML configuration file to define topics and their number of partitions, graph links between nodes (
consumer groups).

## Code Structure / Software architecture

### Introduction

The controller application is structured to manage the scaling of consumer groups dynamically based on specified
metrics.
It monitors and adjusts the number of consumer instances foreach consumer groups, following decision taken.

To facilitate scaling strategy integration (decisions taken, computation, predictions...), the controller have a special
design to integrate different scaling strategies.

### Data Resilience

To ensure data resilience, 3 categories of data are runtime-stored in controller application :

- **Static data** : data to describe the topology of the system (topics, consumer groups, partitions, consumers and
  partitions repartition, graph definition)
- **Calculation data** : data to compute decisions, make calculations needed for decision making
- **Meta data** : data describing the state of the system at a certain time (partition metadata, consumer groups
  metadata, consumers metadata)

### Metrics Collection

The metrics collected come from Prometheus and are given by Kafka exporter and/or experimental consumers in dedicated
Prometheus topics.

### Processing

ScalerProcessor is an interface which impulse scaling decision from input **metadata** (& graph definition) to a list of
decision data foreach consumer group.\
Different strategy could be introduced by implementing the interface.

### Scaling Assignment

In first purpose, scaling assignment is made after scaling decision for all consumer group.

### Sequence diagram

##### //TODO

## 🔧 Environment Variables

*This part is auto generated.*

| Name                 | Description                                                 | Default value                                                           |
|----------------------|-------------------------------------------------------------|-------------------------------------------------------------------------|
| `DI`                 | DI value in milliseconds for the controller loop sleep time | *(undefined)*                                                           |
| `NUMBER_PARTITIONS`  | Number of partitions for the topic                          | *(undefined)*                                                           |
| `REB_TIME`           | REB_TIME value in seconds for the rebalancing time          | *(undefined)*                                                           |
| `FUP`                | FUP value for the upscaling threshold                       | *(undefined)*                                                           |
| `FDOWN`              | FDOWN value for the downscaling threshold                   | *(undefined)*                                                           |
| `BOOTSTRAP_SERVERS`  | Bootstrap servers. Example : 'localhost:9092'               | *(undefined)*                                                           |
| `TOPICS_CONFIG_PATH` | Path to config file                                         | "/config/controller-config.yaml"                                        |
| `REQUEST_TIME_RANGE` | Range time in seconds for the metrics calculation           | 2                                                                       |
| `REQUEST_TIME_UNIT`  | Range time in seconds for the metrics calculation           | "s"                                                                     |
| `SCALING_STRATEGY`   | Scaling strategy selector between : 'naive'                 | ScalingStrategyMapping.BINPACK_NAIVE, ScalingStrategyMapping::getByName |
