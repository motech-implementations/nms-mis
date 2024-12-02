package com.beehyv.nmsreporting.entity.smsdelivery;

public class DeliveryInfoNotification {
    String clientCorrelator;
    String callBackData;
    DeliveryInfo deliveryInfo;

    public DeliveryInfoNotification(String clientCorrelator, String callBackData, DeliveryInfo deliveryInfo) {
        this.clientCorrelator = clientCorrelator;
        this.callBackData = callBackData;
        this.deliveryInfo = deliveryInfo;
    }

    public String getClientCorrelator() {
        return clientCorrelator;
    }

    public void setClientCorrelator(String clientCorrelator) {
        this.clientCorrelator = clientCorrelator;
    }

    public String getCallBackData() {
        return callBackData;
    }

    public void setCallBackData(String callBackData) {
        this.callBackData = callBackData;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }
}
