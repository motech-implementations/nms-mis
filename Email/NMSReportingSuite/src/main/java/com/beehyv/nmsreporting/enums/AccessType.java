package com.beehyv.nmsreporting.enums;

/**
 * Created by beehyv on 5/5/17.
 */
public enum AccessType {
    MASTER_ADMIN("MASTER ADMIN"),
    ADMIN("ADMIN"),
    USER("USER");

    private String accessType;

    private AccessType(String accessType) {
        this.accessType = accessType;
    }

    public static boolean isType(String test) {
        for (AccessType type : AccessType.values()) {
            return (type.name().equalsIgnoreCase(test));
        }
        return false;
    }

    public static String getType(String test) {
        for (AccessType type : AccessType.values()) {
            if (type.name().equalsIgnoreCase(test)) {
                return type.name();
            }
            ;
        }
        return "not valid";
    }

    public String getAccessType() {
        return accessType;
    }


}
