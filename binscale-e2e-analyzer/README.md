# Analyzer

The `binscale-e2e-analyzer` is a Java-based application built with Spring Boot that operates in two distinct modes:
*Consume Mode* and *Export Mode*. It is designed to process and analyze event data from message queues and manage the
data in a PostgreSQL database.

## Features
- **Consume Mode**: Collects messages from event queues, groups events by specific identifiers, and stores the data in a PostgreSQL database.
- **Export Mode**: Exports all stored data from the PostgreSQL database into a JSON file and cleans up the database.
- **Scalable Architecture**: Supports multiple instances in Consume Mode for high scalability and efficient data ingestion.
- **Spring Boot Integration**: Built with Spring Boot for ease of development and deployment.
## Requirements
- **Java**: Version 17 or higher
- **Maven**: For dependency management and build
- **PostgreSQL**: For data storage
- **Kafka**: For message queue integration

## Installation

1. Build `binscale-common`
    ```sh
    cd ./binscale-common
    mvn clean install
    ```
2. Build `binscale-producer`
    ```sh
    cd ./binscale-e2e-analyzer
    mvn clean compile
    ```

## Usage

Deployed on a Kubernetes cluster.  
Refer to the [deployment GitHub repository](https://github.com/benneuville/binscale-deployment).

## Software Architecture
### Modes of Operation

#### 1. Consume Mode

In *Consume Mode*, multiple instances of the service can run concurrently to:

- Collect messages from the specified event queues.
- Group events by a specific identifier.
- Store the processed data in a PostgreSQL database.

This mode is designed for high scalability and efficient data ingestion.

#### 2. Export Mode

In **Export Mode**, a single instance of the service:

- Exports all the data stored in the PostgreSQL database into a JSON file.
- Cleans up the database tables after exporting the data.

This mode is used for data extraction and cleanup.



## Environment Variables

*This part is auto generated.*

| Name | Description | Default value |
|-----|--------------|-------------------|
| `ASYNC_COMMIT` | Async commit parameter. Have the Kafka commit to be asynchronous? | *(undefined)* |
| `BOOTSTRAP_SERVERS` | Bootstrap servers, Example : 'localhost:9092' | *(undefined)* |
| `ADDITIONAL_CONFIG` | Additional consumer configuration in the form 'key1=value1,key2=value2' | "" |
| `TIME_TO_COMMIT` | Time to commit parameter | *(undefined)* |
| `MESSAGE_COUNT` | Message count | 10L |
| `CLIENT_RACK` | Client rack | null |
| `SESSION_TIMEOUT_MS` | Kafka session timeout in milliseconds | "30000" |
| `HEARTBEAT_INTERVAL_MS` | Heartbeat interval in milliseconds | "10000" |
| `TOPICS_CONFIG_PATH` | Path to topic config file | "/config/e2e-analyzer-config.yaml" |
| `EXPORT_PATH` | Path to topic config file | "/export/export-e2e-analyze.json" |
| `MODE` | Mode of the e2e analyzer (CONSUME / EXPORT) | *(undefined)* |

