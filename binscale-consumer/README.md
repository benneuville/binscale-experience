# Consumer

The `binscale-consumer` project is a Java-based application designed to consume messages from Kafka topics while
supporting dynamic scaling and workload management. It includes configurable parameters for fine-tuning consumer
behavior and integrates with Prometheus for monitoring.

## Features

- **Dynamic Scaling**: Supports scaling of consumer groups based on workload.
- **Configurable Parameters**: Allows customization of consumer behavior through environment variables.
- **Kafka Integration**: Efficiently consumes messages from Kafka topics.
- **Monitoring**: Provides metrics for Prometheus integration.

## Requirements

- **Java**: Version 11
- **Maven**: For dependency management and build

## Installation

1. Build `binscale-common`
    ```sh
    cd ./binscale-common
    mvn clean install
    ```
2. Build `binscale-consumer`
    ```sh
    cd ./binscale-consumer
    mvn clean compile
    ```

## Usage

Deployed on a Kubernetes cluster.
Refer to the [deployment GitHub repository](https://github.com/benneuville/binscale-deployment).

Deployment file related to the
consumer [here](https://github.com/benneuville/binscale-deployment/blob/master/experience/consumer.yaml).

## 🔧 Environment Variables

*This part is auto generated.*

| Name | Description | Default value |
|-----|--------------|-------------------|
| `SCALE` | Scale parameter | *(non défini)* |
| `TIME_TO_COMMIT` | Time to commit parameter | *(non défini)* |
| `SHAPE` | Shape parameter | *(non défini)* |
| `WSLA_S` | WSLA parameter | *(non défini)* |
| `ASYNC_COMMIT` | Async commit parameter. Have the Kafka commit to be asynchronous? | *(non défini)* |
| `BOOTSTRAP_SERVERS` | Bootstrap servers, Example : 'localhost:9092' | *(non défini)* |
| `SLEEP` | Sleep time | 0 |
| `ADDITIONAL_CONFIG` | Additional consumer configuration in the form 'key1=value1,key2=value2' | "" |
| `MESSAGE_COUNT` | Message count | 10L |
| `CLIENT_RACK` | Client rack | null |
| `MAX_POLL_RECORDS` | Max poll records parameter. Max number of events returned in a call to Kafka topic. | 500 |
| `HEARTBEAT_INTERVAL_MS` | Heartbeat interval in milliseconds | "3000" |
| `PROCESSING_STRATEGY` | Processing strategy. Example : 'balanced', 'dupplicated', custom' | ProcessStrategyMapping.defaultStrategy, ProcessStrategyMapping::getByName |
| `HEADERS` | Headers to add to each message, comma separated. Example : 'header1:value1,header2:value2' | "" |
| `PRODUCER_ACKS` | Producer acks config. Example : '0', '1' or 'all' | "0" |
| `PROMETHEUS_PORT` | Prometheus port | 8080 |
| `TOPICS_DISTRIBUTION_CONFIG_PATH` | Config path for Topics distribution | "/config/topics-config.yaml" |

