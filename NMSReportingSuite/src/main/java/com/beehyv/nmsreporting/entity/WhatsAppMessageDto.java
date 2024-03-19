package com.beehyv.nmsreporting.entity;

public class WhatsAppMessageDto {
    private Integer id;
    private String locationType;
    private Long locationId;
    private String locationName;
    private Integer totalSubscribers;
    private Integer coreMessagesSent;
    private Integer coreMessagesDelivered;
    private Integer coreMessagesRead;

    private boolean link = false;

    public WhatsAppMessageDto() {
    }

    public WhatsAppMessageDto(Integer id, String locationType, Long locationId, String locationName, Integer totalSubscribers, Integer coreMessagesSent, Integer coreMessagesDelivered, Integer coreMessagesRead, boolean link) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.locationName = locationName;
        this.totalSubscribers = totalSubscribers;
        this.coreMessagesSent = coreMessagesSent;
        this.coreMessagesDelivered = coreMessagesDelivered;
        this.coreMessagesRead = coreMessagesRead;
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

    public Integer getTotalSubscribers() {
        return totalSubscribers;
    }

    public void setTotalSubscribers(Integer totalSubscribers) {
        this.totalSubscribers = totalSubscribers;
    }

    public Integer getCoreMessagesSent() {
        return coreMessagesSent;
    }

    public void setCoreMessagesSent(Integer coreMessagesSent) {
        this.coreMessagesSent = coreMessagesSent;
    }

    public Integer getCoreMessagesDelivered() {
        return coreMessagesDelivered;
    }

    public void setCoreMessagesDelivered(Integer coreMessagesDelivered) {
        this.coreMessagesDelivered = coreMessagesDelivered;
    }

    public Integer getCoreMessagesRead() {
        return coreMessagesRead;
    }

    public void setCoreMessagesRead(Integer coreMessagesRead) {
        this.coreMessagesRead = coreMessagesRead;
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }
}
