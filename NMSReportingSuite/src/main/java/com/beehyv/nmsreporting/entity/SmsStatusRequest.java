package com.beehyv.nmsreporting.entity;

import com.beehyv.nmsreporting.entity.smsdelivery.RequestData;

public class SmsStatusRequest {
    RequestData requestData;

    public SmsStatusRequest(RequestData requestData) {
        this.requestData = requestData;
    }

    public RequestData getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }
}
