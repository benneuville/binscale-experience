# Exporter

The `binscale-exporter` project is a Java-based application designed to export processed data from Kafka topics and
store it in a persistent storage system. It leverages Kafka Streams for data processing and includes configurable
parameters for stream processing and export strategies.

## Features

- **Data Export**: Extracts and processes messages/data from Kafka topics for storage in external systems.
- **Kafka Streams Integration**: Utilizes Kafka Streams for efficient and real-time processing.

## Requirements

- **Java**: Version 11
- **Maven**: For dependency management and build

## Installation

1. Build `binscale-common`
    ```sh
    cd ./binscale-common
    mvn clean install
    ```
2. Build `binscale-exporter`
    ```sh
    cd ./binscale-exporter
    mvn clean compile
    ```

## Usage

Deployed on a Kubernetes cluster.  
Refer to the [deployment GitHub repository](https://github.com/benneuville/binscale-deployment).

Deployment file template related to the
exporter [here](https://github.com/benneuville/binscale-deployment/blob/master/experience/templates/exporter.yaml.j2).

## Software Architecture

The exporter application is structured around Kafka Streams for processing and exporting data. Kafka Streams is used to:

1. **Stream Processing**: Consume data from input Kafka topics and apply transformations or aggregations as needed.
2. **Stateful Processing**: Maintain state stores for intermediate results during stream processing.
3. **Scalability**: Distribute processing across multiple instances for high throughput.

The processed data is then exported to a persistent storage system, such as a database or file system.

## Environment Variables

*This part is auto generated.*

| Name | Description | Default value |
|-----|--------------|-------------------|
| `HEADERS_CONFIG_PATH` | Path to config file | "/config/exporter-config.yaml" |

