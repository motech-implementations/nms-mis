package com.beehyv.nmsreporting.enums;

public enum MessageType {
    WEEKLY_CALLS_NOT_ANSWERED("WEEKLY_CALLS_NOT_ANSWERED"),
    LOWLISTENERSHIP("LOWLISTENERSHIP"),
    REGISTRATION("REGISTRATION"),
    ASHACERTIFICATE("ASHACERTIFICATE");

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static boolean isValidType(String test) {
        for (MessageType type : MessageType.values()) {
            if (type.name().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }

    public static String getTypeEnum(String test) {
        for (MessageType type : MessageType.values()) {
            if (type.name().equalsIgnoreCase(test)) {
                return type.name();
            }
        }
        return "not valid";
    }
}
