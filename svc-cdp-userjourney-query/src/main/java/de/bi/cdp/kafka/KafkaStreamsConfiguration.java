package de.bi.cdp.kafka;

import de.id.dataflow.audience.analytics.model.userjourneys.UserJourney;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import static org.apache.kafka.streams.StreamsConfig.*;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamsConfiguration {

//    @Value("${cdp-query-uj.stateStoreName}")
    private String stateStoreName = "userJourneyDataStore";

//    @Value("${cdp-query-uj.sourceTopicName}")
    private String sourceTopicName = "de.id.dataflow.audience.analytics.user-journey";

  //  @Value("${cdp-query-uj.application-id}")
    private String applicationID;

  //  @Value("${cdp-query-uj.kafka.bootstrapAddress}")
    private String bootstrapAddress;

    //@Value("${cdp-query-uj.kafka.schema.registry.url}")
    private String schemaRegistryUrl;

   // @Value("${cdp-query-uj.schema-registry}")
    private String schemaRegistry;

    //TODO
    final Map<String, String> serdeConfig = Collections.
            singletonMap("schema.registry.url", "http://confluent-schema-registry.bi-kafka-management:8081");
    final Serde<UserJourney> valueSerde = new SpecificAvroSerde<>();

    private String msgOrderLocationTopic = "de.id.dataflow.audience.analytics.user-journey";

//    private String stateStoreName = "userJourneyDataStore";
//

    private final ObjectFactory<UserJourneyStreamProcessor> userJourneyStreamsProcessorObjectFactory;
    private final Deserializer<String> keyDeSerializer = new StringDeserializer();
    private final Serde<String> keySerializer = Serdes.String();

    public KafkaStreamsConfiguration(ObjectFactory<UserJourneyStreamProcessor> userJourneyStreamsProcessorObjectFactory) {
        this.userJourneyStreamsProcessorObjectFactory = userJourneyStreamsProcessorObjectFactory;
    }

    public UserJourneyStreamProcessor getOrderLocationStreamsProcessor() {
        return userJourneyStreamsProcessorObjectFactory.getObject();
    }

    @Bean
    @Primary
    public KafkaStreams kafkaStreams() {
//        log.info("Create Kafka Stream Bean with defined topology");
        Topology topology = this.buildTopology(new StreamsBuilder());
        final KafkaStreams kafkaStreams = new KafkaStreams(topology, createConfigurationProperties());
        kafkaStreams.start();
        return kafkaStreams;
    }

    /**
     * This method is used for defining topology for KafkaStreams
     * Topology:
     * 1. read the topic
     * 2. send to stream processor for processing the message
     * 3. persist message to key-value State Store
     *
     * @param streamsBuilder new Stream Builder
     * @return Topology
     */
    private Topology buildTopology(StreamsBuilder streamsBuilder) {
//        final Map<String, String> serdeConfig = Collections.
//                singletonMap("schema.registry.url", schemaRegistryUrl);
//        final Serde<UserJourney> valueSerde = new SpecificAvroSerde();

        Topology topology = streamsBuilder.build();
        keySerializer.configure(serdeConfig, true);
        valueSerde.configure(serdeConfig, false);

        StoreBuilder<KeyValueStore<String, UserJourney>> stateStoreBuilder =
                Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore(stateStoreName), keySerializer, valueSerde);

        topology.addSource("Source", keySerializer.deserializer(), valueSerde.deserializer(), sourceTopicName)
                .addProcessor("Process", this::getOrderLocationStreamsProcessor, "Source")
                .addStateStore(stateStoreBuilder, "Process");
        return topology;
    }

    /**
     * This method is used for setting the configuration of Kafka Stream
     *
     * @return Properties
     */
    private Properties createConfigurationProperties() {
//        final Properties props = new Properties();
//        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        props.put(APPLICATION_ID_CONFIG, applicationID);
//        props.put(DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);
//        props.put(schemaRegistry, schemaRegistryUrl);
//        return props;
        final Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "PLAINTEXT://strimzi-kafka-cluster-kafka-bootstrap.bi-kafka:9092");
        props.put(APPLICATION_ID_CONFIG, "cdp-user-journey-query");
        props.put(DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);
        props.put("schema.registry.url", "http://confluent-schema-registry.bi-kafka-management:8081");
        return props;

    }
}
