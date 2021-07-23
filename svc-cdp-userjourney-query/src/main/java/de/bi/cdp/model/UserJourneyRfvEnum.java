package de.bi.cdp.model;

/**
 * Enum that classifies the RFV's type.
 *
 * @author Antonio Casado
 * @since 14/07/2021
 *
 */
public enum UserJourneyRfvEnum {
    RETURN("CASUAL_READER"), ONE_WAY("LOYAL_READER"), MULTI_CITY("BRAND_LOVER");

    private String value;

    private UserJourneyRfvEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Method that returns the value in the Enum.
     * @param value
     * @return UserJourneyRfvEnum
     */
    public static UserJourneyRfvEnum getEnum(String value) {

        for(UserJourneyRfvEnum t : values()) {
            if(value.equals(t.getValue())) {
                return t;
            }
        }
        throw new RuntimeException("Type not found.");
    }

}