package com.beehyv.nmsreporting.enums;

public enum FileType {

    SMS_TARGET_FILE("SMS_TARGET_FILE");

    private final String type;

    FileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static boolean isValidType(String test) {
        for (FileType type : FileType.values()) {
            if (type.name().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }

    public static String getTypeEnum(String test) {
        for (FileType type : FileType.values()) {
            if (type.name().equalsIgnoreCase(test)) {
                return type.name();
            }
        }
        return "not valid";
    }
}
