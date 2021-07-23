package de.bi.cdp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;

/**
 * Enum that classifies the RFV's type.
 *
 * @author Antonio Casado
 * @since 14/07/2021
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserJourneyResponse extends RepresentationModel<UserJourneyResponse> implements Serializable {
    String uid;
    String clientName;
    Long clientId;
    String rfv;
    String fuseValue;
    Long lastSessionTimeStamp;
    Long firstSessionTimeStamp;
    List<Long> sessionEventReceivedTimestamps;
}

