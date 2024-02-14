package com.beehyv.nmsreporting.entity;

public class KilkariSubscriberRegistrationDateRejectedCountDto {
    private int id;

    private long locationId;

    private Integer subscriberCount;

    public KilkariSubscriberRegistrationDateRejectedCountDto() {
    }


    public KilkariSubscriberRegistrationDateRejectedCountDto(long locationId, Integer subscriberCount) {
        this.locationId = locationId;
        this.subscriberCount = subscriberCount;
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
}
