package com.beehyv.nmsreporting.entity;

public class KilkariSubscriberRegistrationDateRejectedCountDto {
    private int id;

    private long locationId;

    private Integer subscriberCount;
    private Integer subscriberCount_PW;
    private Integer subscriberCount_Child;
    private Integer subscriberCount_Ineligible;

    public KilkariSubscriberRegistrationDateRejectedCountDto() {
    }


    public KilkariSubscriberRegistrationDateRejectedCountDto(long locationId, Integer subscriberCount) {
        this.locationId = locationId;
        this.subscriberCount = subscriberCount;
    }

    public KilkariSubscriberRegistrationDateRejectedCountDto(long locationId, Integer subscriberCount, Integer subscriberCount_PW, Integer subscriberCount_Child, Integer subscriberCount_Ineligible) {
        this.locationId = locationId;
        this.subscriberCount = subscriberCount;
        this.subscriberCount_PW = subscriberCount_PW;
        this.subscriberCount_Child = subscriberCount_Child;
        this.subscriberCount_Ineligible = subscriberCount_Ineligible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public Integer getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(Integer subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public Integer getSubscriberCount_PW() {
        return subscriberCount_PW;
    }

    public void setSubscriberCount_PW(Integer subscriberCount_PW) {
        this.subscriberCount_PW = subscriberCount_PW;
    }

    public Integer getSubscriberCount_Child() {
        return subscriberCount_Child;
    }

    public void setSubscriberCount_Child(Integer subscriberCount_Child) {
        this.subscriberCount_Child = subscriberCount_Child;
    }

    public Integer getSubscriberCount_Ineligible() {
        return subscriberCount_Ineligible;
    }

    public void setSubscriberCount_Ineligible(Integer subscriberCount_Ineligible) {
        this.subscriberCount_Ineligible = subscriberCount_Ineligible;
    }
}
