package com.beehyv.nmsreporting.entity;

public class WhatsAppReportDto {
    private Integer id;
    private String locationType;
    private Long locationId;
    private String locationName;
    private Integer welcomeCallSuccessfulCalls;
    private Integer welcomeCallCallsAnswered;
    private Integer welcomeCallEnteredAnOption;
    private Integer welcomeCallProvidedOptIn;
    private Integer welcomeCallProvidedOptOut;
    private Integer optInSuccessfulCalls;
    private Integer optInCallsAnswered;
    private Integer optInEnteredAnOption;
    private Integer optInProvidedOptIn;
    private Integer optInProvidedOptOut;
    private boolean link = false;

    public WhatsAppReportDto() {
    }

    public WhatsAppReportDto(Integer id, String locationType, Long locationId, String locationName, Integer welcomeCallSuccessfulCalls, Integer welcomeCallCallsAnswered, Integer welcomeCallEnteredAnOption, Integer welcomeCallProvidedOptIn, Integer welcomeCallProvidedOptOut, Integer optInSuccessfulCalls, Integer optInCallsAnswered, Integer optInEnteredAnOption, Integer optInProvidedOptIn, Integer optInProvidedOptOut, boolean link) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.locationName = locationName;
        this.welcomeCallSuccessfulCalls = welcomeCallSuccessfulCalls;
        this.welcomeCallCallsAnswered = welcomeCallCallsAnswered;
        this.welcomeCallEnteredAnOption = welcomeCallEnteredAnOption;
        this.welcomeCallProvidedOptIn = welcomeCallProvidedOptIn;
        this.welcomeCallProvidedOptOut = welcomeCallProvidedOptOut;
        this.optInSuccessfulCalls = optInSuccessfulCalls;
        this.optInCallsAnswered = optInCallsAnswered;
        this.optInEnteredAnOption = optInEnteredAnOption;
        this.optInProvidedOptIn = optInProvidedOptIn;
        this.optInProvidedOptOut = optInProvidedOptOut;
        this.link = link;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getWelcomeCallSuccessfulCalls() {
        return welcomeCallSuccessfulCalls;
    }

    public void setWelcomeCallSuccessfulCalls(Integer welcomeCallSuccessfulCalls) {
        this.welcomeCallSuccessfulCalls = welcomeCallSuccessfulCalls;
    }

    public Integer getWelcomeCallCallsAnswered() {
        return welcomeCallCallsAnswered;
    }

    public void setWelcomeCallCallsAnswered(Integer welcomeCallCallsAnswered) {
        this.welcomeCallCallsAnswered = welcomeCallCallsAnswered;
    }

    public Integer getWelcomeCallEnteredAnOption() {
        return welcomeCallEnteredAnOption;
    }

    public void setWelcomeCallEnteredAnOption(Integer welcomeCallEnteredAnOption) {
        this.welcomeCallEnteredAnOption = welcomeCallEnteredAnOption;
    }

    public Integer getWelcomeCallProvidedOptIn() {
        return welcomeCallProvidedOptIn;
    }

    public void setWelcomeCallProvidedOptIn(Integer welcomeCallProvidedOptIn) {
        this.welcomeCallProvidedOptIn = welcomeCallProvidedOptIn;
    }

    public Integer getWelcomeCallProvidedOptOut() {
        return welcomeCallProvidedOptOut;
    }

    public void setWelcomeCallProvidedOptOut(Integer welcomeCallProvidedOptOut) {
        this.welcomeCallProvidedOptOut = welcomeCallProvidedOptOut;
    }

    public Integer getOptInSuccessfulCalls() {
        return optInSuccessfulCalls;
    }

    public void setOptInSuccessfulCalls(Integer optInSuccessfulCalls) {
        this.optInSuccessfulCalls = optInSuccessfulCalls;
    }

    public Integer getOptInCallsAnswered() {
        return optInCallsAnswered;
    }

    public void setOptInCallsAnswered(Integer optInCallsAnswered) {
        this.optInCallsAnswered = optInCallsAnswered;
    }

    public Integer getOptInEnteredAnOption() {
        return optInEnteredAnOption;
    }

    public void setOptInEnteredAnOption(Integer optInEnteredAnOption) {
        this.optInEnteredAnOption = optInEnteredAnOption;
    }

    public Integer getOptInProvidedOptIn() {
        return optInProvidedOptIn;
    }

    public void setOptInProvidedOptIn(Integer optInProvidedOptIn) {
        this.optInProvidedOptIn = optInProvidedOptIn;
    }

    public Integer getOptInProvidedOptOut() {
        return optInProvidedOptOut;
    }

    public void setOptInProvidedOptOut(Integer optInProvidedOptOut) {
        this.optInProvidedOptOut = optInProvidedOptOut;
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }
}
