package com.beehyv.nmsreporting.entity.smsdelivery;

public class RequestData {
    DeliveryInfoNotification deliveryInfoNotification;

    public RequestData(DeliveryInfoNotification deliveryInfoNottification) {
        this.deliveryInfoNotification = deliveryInfoNotification;
    }

    public DeliveryInfoNotification getDeliveryInfoNotitification() {
        return deliveryInfoNotification;
    }

    public void setDeliveryInfoNotitification(DeliveryInfoNotification deliveryInfoNotification) {
        this.deliveryInfoNotification = deliveryInfoNotification;
    }
}
