# Controller

The `binscale-controller` project is a Java-based application designed to manage the scaling of consumer groups dynamically. It leverages Kafka for message processing and includes configurable parameters for workload balancing and scaling strategies.

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

Deployment file related to the controller [here](https://github.com/benneuville/binscale-deployment/blob/master/experience/controller.yaml).

## Scaling Parameters

The project supports the following scaling parameters:

- DI (Controller loop sleep time)
- WSLA (Workload Service Level Agreement)
- REB_TIME (Rebalancing time)
- MU (Controller calculations interval)
- FUP (Upscaling threshold)
- FDOWN (Downscaling threshold)


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

