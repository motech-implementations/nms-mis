package com.beehyv.nmsreporting.enums;

/**
 * Created by beehyv on 3/6/17.
 */
public enum ServiceType {

    ma("M"),
    kilkari("K"),
    all("ALL");


    private String serviceType;

    public String getServiceType() {return  serviceType;}

    ServiceType(String serviceType){this.serviceType=serviceType;}

}
