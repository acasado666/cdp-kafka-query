# Kafka Streams User Data Profile Example
The definition of the repository is here.

![Kafka Query User Data Profile logo](/img/KafkaStreaming.png "Querying Kakfa User Data Profile")

## Requirements and Dependencies

- Kafka Cluster

- Java 11

- Spring Boot 2.5.1

- Spring Kafka Library

- application.properties
    ```
kafka.bootstrapAddress = http://localhost:9092
kafka.topics.msgUserData.name = userData

kafka.streams.application-id = userDataApplication
kafka.streams.stateStoreName = userDataStore

    ```

## Running the application
```./gradlew clean bootRun```
