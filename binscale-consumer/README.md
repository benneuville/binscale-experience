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

## Configuration

## Software Architecture

This simple consumer is composed of an initialisation phase and the running application.

![consumer_init_sequence_diagram.png](assets%2Fconsumer_init_sequence_diagram.png)\
*Initialisation sequence*

![consumer_run_sequence_diagram.png](assets%2Fconsumer_run_sequence_diagram.png)\
*Running phase*

### Extensible work

Some designs have been implemented to simplify the integration of components or methods.\

#### Message processing methods

In case of adding new processing event methods or distribution strategies, an abstraction have been introduced to
simplify theirs integrations.\
Some existing strategies have been implemented has named at `BalancedProcessStrategy`*(distribute events equally between
next topics)*, `DuplicateProcessStrategy`*(distribute each event in all next topics)* and `CustomProcessStrategy`*(
distribution defined by the `DistributionConfig`)*.

![consumer_process_class_diagram.png](assets%2Fconsumer_process_class_diagram.png)\
*ProcessingStrategy Integration and the abstract method `process()`*

## Environment Variables

*This part is auto generated.*

| Name | Description | Default value |
|-----|--------------|-------------------|
| `TOPIC` | Input topic name. Example : 'testtopic1' | *(undefined)* |
| `GROUP_ID` | Input Group ID for the consumer. Example : 'testgroup1' | "" |
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
| `CONSUMPTION_RATE` | Number of events consumed per seconds. | 200 |
| `SESSION_TIMEOUT_MS` | Kafka session timeout in milliseconds | "3000" |
| `HEARTBEAT_INTERVAL_MS` | Heartbeat interval in milliseconds | "1000" |
| `PROCESSING_STRATEGY` | Processing strategy. Example : 'balanced', 'dupplicated', custom' | ProcessStrategyMapping.defaultStrategy, ProcessStrategyMapping::getByName |
| `HEADERS` | Headers to add to each message, comma separated. Example : 'header1:value1,header2:value2' | "" |
| `PRODUCER_ACKS` | Producer acks config. Example : '0', '1' or 'all' | "0" |
| `TOPICS_DISTRIBUTION_CONFIG_PATH` | Config path for Topics distribution | "/config/topics-config.yaml" |
| `TIME_BEFORE_AVAILABILITY` | Time before consumer availability (in ms). Consumer will be up and running but dont consume events until this time has passed. Could simulate a connection to a db or any other external dependency that takes time. | 0L |

