package com.beehyv.nmsreporting.entity;

public class OTPRequest {
    private Long msisdn;
    private String captchaResponse;

    public Long getMsisdn() { return msisdn;}
    public void setMsisdn(Long msisdn) {this.msisdn = msisdn;}
    public String getCaptchaResponse() {return captchaResponse;}
    public void setCaptchaResponse(String captchaResponse) {this.captchaResponse = captchaResponse;}
}
