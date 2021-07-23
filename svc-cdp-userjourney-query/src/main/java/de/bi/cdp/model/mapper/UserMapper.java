package de.bi.cdp.model.mapper;

import de.bi.cdp.model.UserJourneyRequest;
import de.bi.cdp.model.UserJourneyResponse;
import de.id.dataflow.audience.analytics.model.userjourneys.UserJourney;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum that classifies the RFV's type.
 *
 * @author Antonio Casado
 * @since 14/07/2021
 *
 */
@Component
public class UserMapper {

    public static UserJourney mapToUserDomain(UserJourneyRequest request) {
        return UserJourney.newBuilder()
                .setUid(request.getUid())
                .setLastSessionTimeStamp(request.getLastSessionTimeStamp())
                .setFirstSessionTimeStamp(request.getFirstSessionTimeStamp())
                .setClientId(request.getClientId())
                .setClientName(request.getClientName())
                .setRfv((Integer) request.getRfv())
                .setFuseValue(request.getFuseValue())
                .build();
    }

    public UserJourneyResponse mapToUserResponse(UserJourney user) {
        return UserJourneyResponse.builder()
                .uid(user.getUid())
                .firstSessionTimeStamp(user.getFirstSessionTimeStamp())
                .lastSessionTimeStamp(user.getLastSessionTimeStamp())
                .sessionEventReceivedTimestamps(user.getSessionEventReceivedTimestamps())
                .clientId(user.getClientId())
                .clientName(user.getClientName())
                .fuseValue(user.getFuseValue().toString())
                .build();
    }

    public List<UserJourneyResponse> mapToListUserResponse(List<UserJourney> l) {
        return (l).stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }
}
