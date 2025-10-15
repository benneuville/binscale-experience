# Producer

The `produceri3s` project is a Java-based application designed to handle workload management and message production. It
leverages gRPC for communication and includes configurable workload strategies to simulate different production
scenarios.

## Features

- **Workload Management**: Supports multiple workload strategies such as `Biased`, `Constant`, and `Non-Uniform`.
- **Environment Configuration**: Easily configurable via environment variables.
- **gRPC Service**: Provides a gRPC-based `ArrivalService` for managing arrival rates.

## Requirements

- **Java**: Version 11
- **Maven**: For dependency management and build

## Installation

1. Build `binscale-common`
    ```sh
    cd ./binscale-common
    mvn clean install
    ```
2. Build `produceri3s`
    ```sh
    cd ./produceri3s
    mvn clean compile
    ```

## Usage

Deployed on a Kubernetes cluster.
Refer to the [deployment Github repository](https://github.com/benneuville/binscale-deployment).

Deployment file related to the
producer [here](https://github.com/benneuville/binscale-deployment/blob/master/experience/producer.yaml).

## Workload Strategies

The project supports the following workload strategies:

* Biased.
* Constant.
* Non-Uniform.

## 🔧 Environment Variables

*This part is auto generated.*

| Name                | Description                                                                                | Default value              |
|---------------------|--------------------------------------------------------------------------------------------|----------------------------|
| `PARTITION_WEIGHTS` | List of partition weights, comma separated. Example : '1,1,1,1,1'                          | *(non défini)*             |
| `INPUT_WORKLOAD`    | Input workload file name. Example : 'defaultArrivalRatesm.csv'                             | "defaultArrivalRatesm.csv" |
| `BOOTSTRAP_SERVERS` | Bootstrap servers, Example : 'localhost:9092'                                              | *(non défini)*             |
| `TOPIC`             | Topic name. Example : 'test-topic'                                                         | *(non défini)*             |
| `DELAY_MS`          | Delay between two messages in milliseconds. Example : 1000                                 | *(non défini)*             |
| `MESSAGES_COUNT`    | Number of messages to send. Example : 10                                                   | 10L                        |
| `MESSAGE`           | Message content. Example : 'Hello World !'                                                 | "Hello World !"            |
| `PRODUCER_ACKS`     | Producer acks config. Example : '0', '1' or 'all'                                          | "0"                        |
| `HEADERS`           | Headers to add to each message, comma separated. Example : 'header1:value1,header2:value2' | ""                         |
| `ADDITIONAL_CONFIG` | Additional producer configuration in the form 'key1=value1,key2=value2'                    | ""                         |
| `WORKLOAD`          | Workload mapping strategy. Example : 'constant'                                            | "constant"                 |
| `SERVER_PORT`       | Server port for the health check endpoint                                                  | 5002                       |

