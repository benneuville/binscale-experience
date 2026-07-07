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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |


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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |
| `PROCESSING_RATE_REQUEST_TIME_RANGE` | Range time in seconds for processing rate request | 30 |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |
| `PROCESSING_RATE_REQUEST_TIME_RANGE` | Range time in seconds for processing rate request | 30 |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |
| `PROCESSING_RATE_REQUEST_TIME_RANGE` | Range time in seconds for processing rate request | 30 |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |
| `PROCESSING_RATE_REQUEST_TIME_RANGE` | Range time in seconds for processing rate request | 30 |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |

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
| `NAMESPACE` | Namespace of the Kubernetes cluster | "default" |
| `WAITING_INTERVAL` | Waiting interval (in ms) before checking consumers readiness after a scaling operation | 250L |
