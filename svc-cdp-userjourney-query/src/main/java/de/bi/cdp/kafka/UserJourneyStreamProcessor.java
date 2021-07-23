package de.bi.cdp.kafka;

import de.id.dataflow.audience.analytics.model.userjourneys.UserJourney;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class UserJourneyStreamProcessor implements Processor<String, UserJourney> {

//    @Value("${cdp-query-uj.stateStoreName}")
    private String stateStoreName = "userJourneyDataStore";

    private KeyValueStore<String, UserJourney> stateStore;

    @Override
    public void init(ProcessorContext context) {
        stateStore = (KeyValueStore<String, UserJourney>) context.getStateStore(stateStoreName);
        Objects.requireNonNull(stateStore, "State store can't be null");
    }

    @Override
    public void process(String key, UserJourney value) {
        log.debug("Streams Request to save process userId : {}", key);
        stateStore.put(key, value);
    }

    @Override
    public void close() {

    }
}
