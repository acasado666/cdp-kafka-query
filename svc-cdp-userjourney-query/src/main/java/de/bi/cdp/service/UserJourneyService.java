package de.bi.cdp.service;

import de.id.dataflow.audience.analytics.model.userjourneys.UserJourney;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.errors.NotFoundException;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreType;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserJourneyService {

    private final KafkaStreams kafkaStreams;
//    @Value("${cdp-query-uj.stateStoreName}")
    private String stateStoreName = "de.id.dataflow.audience.analytics.user-journey";

    // Default value when it comes empty TODO
    private String id = "r_QlxgowOWpGF27HZuL400zA8mE0ZAxhpIEuXEawkz0KteU-nl9zHZv-OqxGGaBg";


    public UserJourneyService(KafkaStreams kafkaStreams) {
        log.info("before kafka streams");
        this.kafkaStreams = kafkaStreams;
        log.info("before kafka streams");
    }

    public String getData(String userId) {
        QueryableStoreType<ReadOnlyKeyValueStore<String, UserJourney>> queryableStoreType = QueryableStoreTypes.keyValueStore();

        StoreQueryParameters<ReadOnlyKeyValueStore<String, UserJourney>> storeQueryParameters =
                StoreQueryParameters.fromNameAndType(stateStoreName, queryableStoreType);
        UserJourney userJourney = kafkaStreams.store(storeQueryParameters).get(userId);
        log.info("User Journey Result: {}", userJourney);
        return userJourney.toString();
    }

    public String getEventData(String id) {
        final ReadOnlyKeyValueStore<String, UserJourney> keyValueStore = kafkaStreams.store(this.stateStoreName,
                QueryableStoreTypes.keyValueStore());
        if (keyValueStore == null) {
            throw new NotFoundException("");
        }
        var value = keyValueStore.get(id);
        return value.toString();
    }

    public UserJourney getUserJourney() {
        final ReadOnlyKeyValueStore<String, UserJourney> keyValueStore =
                kafkaStreams.store(this.stateStoreName, QueryableStoreTypes.keyValueStore());
        if (keyValueStore == null) {
            throw new NotFoundException("");
        }
        var value = keyValueStore.get(id);
        return value;
    }

    public List<String> getUserJourneyRange() {
        List<String> userJourneyRangeList = new ArrayList<>();
        final ReadOnlyKeyValueStore<String, UserJourney> keyValueStore =
                kafkaStreams.store(this.stateStoreName, QueryableStoreTypes.keyValueStore());
        if (keyValueStore == null) {
            throw new NotFoundException("");
        }

        KeyValueIterator<String, UserJourney> range = keyValueStore.range(
                "11e70bb5-95ca-2c21-2067-7835be1c4133",
                "14de9e23-b7bf-ef3c-218a-4af34427902e");
        while (range.hasNext()) {
            KeyValue<String, UserJourney> next = range.next();
            log.debug("count for " + next.key + ": " + next.value);
            userJourneyRangeList.add(next.value.toString());
        }
        return userJourneyRangeList;
    }

    public List<UserJourney> getUserJourneyList() {
        List<UserJourney> userJourneyList = getUserJourneyListData();
        return userJourneyList;
    }

    public String getRfvData() {
        StoreQueryParameters<ReadOnlyKeyValueStore<String, UserJourney>> storeQueryParameters =
                StoreQueryParameters.fromNameAndType(stateStoreName, QueryableStoreTypes.keyValueStore());

        if (QueryableStoreTypes.keyValueStore() == null) {
            throw new NotFoundException("");
        }
        UserJourney userJourney = kafkaStreams.store(storeQueryParameters).get(id);
        return userJourney.getRfv().toString();
    }

    public String getClientNameData() {
        StoreQueryParameters<ReadOnlyKeyValueStore<String, UserJourney>> storeQueryParameters =
                StoreQueryParameters.fromNameAndType(stateStoreName, QueryableStoreTypes.keyValueStore());
        if (QueryableStoreTypes.keyValueStore() == null) {
            throw new NotFoundException("");
        }
        UserJourney userJourney = kafkaStreams.store(storeQueryParameters).get(id);
        return userJourney.getClientName();
    }

    public String getSessionTimeStamp() {
        StoreQueryParameters<ReadOnlyKeyValueStore<String, UserJourney>> storeQueryParameters =
                StoreQueryParameters.fromNameAndType(stateStoreName, QueryableStoreTypes.keyValueStore());
        if (QueryableStoreTypes.keyValueStore() == null) {
            throw new NotFoundException("");
        }
        UserJourney userJourney = kafkaStreams.store(storeQueryParameters).get(id);
        return userJourney.getSessionEventReceivedTimestamps().toString();
    }

    private List<UserJourney> getUserJourneyListData() {

        UserJourney userJourney = UserJourney.newBuilder()
                .setUid("679b26bd-c134-a0d4-aa74-1d1aafa56c23")
                .setLastSessionTimeStamp(1623662982736L)
                .setFirstSessionTimeStamp(123456456754L)
                .setClientId(null)
                .setClientName("HNA")
                .setRfv(23)
                .setFuseValue("CASUAL_READER")
                .build();
        UserJourney userJourney1 = UserJourney.newBuilder()
                .setUid("999b26bd-c134-a0d4-aa74-1d1aafa56c99")
                .setLastSessionTimeStamp(9923662982799L)
                .setFirstSessionTimeStamp(523464564L)
                .setClientId(null)
                .setClientName("HNA")
                .setRfv(47)
                .setFuseValue("CASUAL_READER")

                .build();

        List<UserJourney> userJourneyList = new ArrayList<>(List.of(userJourney, userJourney1));
        return userJourneyList;
    }
}
