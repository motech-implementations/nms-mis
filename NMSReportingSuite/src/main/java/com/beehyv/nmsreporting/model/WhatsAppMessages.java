package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "agg_whatsapp_messages")
public class WhatsAppMessages {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer id;

    @Column(name="location_type", columnDefinition = "VARCHAR(45)")
    private String locationType;

    @Column(name="location_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name="date", columnDefinition = "DATE")
    private Date date;

    @Column(name="total_subscribers", columnDefinition = "INT(11)")
    private Integer totalSubscribers;

    @Column(name = "core_messages_sent", columnDefinition = "INT(11)")
    private Integer coreMessagesSent;

    @Column(name = "core_messages_delivered", columnDefinition = "INT(11)")
    private Integer coreMessagesDelivered;

    @Column(name = "core_messages_read", columnDefinition = "INT(11)")
    private Integer coreMessagesRead;

    @Column(name="period_type", columnDefinition = "VARCHAR(45)")
    private String periodType;

    public WhatsAppMessages() {
    }

    public WhatsAppMessages(Integer id, String locationType, Long locationId, Date date, Integer totalSubscribers, Integer coreMessagesSent, Integer coreMessagesDelivered, Integer coreMessagesRead, String periodType) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.date = date;
        this.totalSubscribers = totalSubscribers;
        this.coreMessagesSent = coreMessagesSent;
        this.coreMessagesDelivered = coreMessagesDelivered;
        this.coreMessagesRead = coreMessagesRead;
        this.periodType = periodType;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
}
