package com.beehyv.nmsreporting.entity;

public class WhatsAppSubscriberDto {
    private Integer id;
    private String locationType;
    private Long locationId;
    private String locationName;
    private Integer activeWhatsAppSubscribers;
    private Integer newWhatsAppOptIns;
    private Integer newWhatsAppSubscribers;
    private Integer selfWhatsAppDeactivatedSubscriber;
    private Integer deliveryFailureWhatsAppDeactivatedSubscriber;
    private Integer motherPackCompletedSubscribers;
    private Integer childPackCompletedSubscribers;

    private boolean link = false;

    public WhatsAppSubscriberDto() {
    }

    public WhatsAppSubscriberDto(Integer id, String locationType, Long locationId, String locationName, Integer activeWhatsAppSubscribers, Integer newWhatsAppOptIns, Integer newWhatsAppSubscribers, Integer selfWhatsAppDeactivatedSubscriber, Integer deliveryFailureWhatsAppDeactivatedSubscriber, Integer motherPackCompletedSubscribers, Integer childPackCompletedSubscribers, boolean link) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.locationName = locationName;
        this.activeWhatsAppSubscribers = activeWhatsAppSubscribers;
        this.newWhatsAppOptIns = newWhatsAppOptIns;
        this.newWhatsAppSubscribers = newWhatsAppSubscribers;
        this.selfWhatsAppDeactivatedSubscriber = selfWhatsAppDeactivatedSubscriber;
        this.deliveryFailureWhatsAppDeactivatedSubscriber = deliveryFailureWhatsAppDeactivatedSubscriber;
        this.motherPackCompletedSubscribers = motherPackCompletedSubscribers;
        this.childPackCompletedSubscribers = childPackCompletedSubscribers;
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

    public Integer getActiveWhatsAppSubscribers() {
        return activeWhatsAppSubscribers;
    }

    public void setActiveWhatsAppSubscribers(Integer activeWhatsAppSubscribers) {
        this.activeWhatsAppSubscribers = activeWhatsAppSubscribers;
    }

    public Integer getNewWhatsAppOptIns() {
        return newWhatsAppOptIns;
    }

    public void setNewWhatsAppOptIns(Integer newWhatsAppOptIns) {
        this.newWhatsAppOptIns = newWhatsAppOptIns;
    }

    public Integer getNewWhatsAppSubscribers() {
        return newWhatsAppSubscribers;
    }

    public void setNewWhatsAppSubscribers(Integer newWhatsAppSubscribers) {
        this.newWhatsAppSubscribers = newWhatsAppSubscribers;
    }

    public Integer getSelfWhatsAppDeactivatedSubscriber() {
        return selfWhatsAppDeactivatedSubscriber;
    }

    public void setSelfWhatsAppDeactivatedSubscriber(Integer selfWhatsAppDeactivatedSubscriber) {
        this.selfWhatsAppDeactivatedSubscriber = selfWhatsAppDeactivatedSubscriber;
    }

    public Integer getDeliveryFailureWhatsAppDeactivatedSubscriber() {
        return deliveryFailureWhatsAppDeactivatedSubscriber;
    }

    public void setDeliveryFailureWhatsAppDeactivatedSubscriber(Integer deliveryFailureWhatsAppDeactivatedSubscriber) {
        this.deliveryFailureWhatsAppDeactivatedSubscriber = deliveryFailureWhatsAppDeactivatedSubscriber;
    }

    public Integer getMotherPackCompletedSubscribers() {
        return motherPackCompletedSubscribers;
    }

    public void setMotherPackCompletedSubscribers(Integer motherPackCompletedSubscribers) {
        this.motherPackCompletedSubscribers = motherPackCompletedSubscribers;
    }

    public Integer getChildPackCompletedSubscribers() {
        return childPackCompletedSubscribers;
    }

    public void setChildPackCompletedSubscribers(Integer childPackCompletedSubscribers) {
        this.childPackCompletedSubscribers = childPackCompletedSubscribers;
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }
}
