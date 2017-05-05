package com.beehyv.nmsreporting.model;

/**
 * Created by beehyv on 5/5/17.
 */
public enum AccessType {
    ADMIN("ADMIN"),
    USER("USER");

    private String accesType;

    public static boolean isType(String test){
        for (AccessType type: AccessType.values()) {
            return (type.name().equalsIgnoreCase(test));
        }
        return false;
    }

    public static String getType(String test){
        for (AccessType type: AccessType.values()) {
            if(type.name().equalsIgnoreCase(test)){
                return type.name();
            };
        }
        return "not valid";
    }


    private AccessType(String accesType){this.accesType=accesType;}

    public String getAccesType(){return accesType;}



}
