package de.bi.cdp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author Antonio Casado
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserJourneyRequest implements Serializable {

    private static final long serialVersionUID = 2270062639915838735L;

    @NotBlank(message = "{notBlank.uid}")
    private String uid;

    private String clientName;

    private Long clientId;

    @NotNull(message = "{notNull.lastSessionTimeStamp}")
    private Long lastSessionTimeStamp;

    @NotNull(message = "{notNull.firstSessionTimeStamp}")
    private Long firstSessionTimeStamp;

    @NotNull(message = "{notNull.rfv}")
    private Object rfv;

    private String fuseValue;

    private List<Long> sessionEventReceivedTimestamps;
}