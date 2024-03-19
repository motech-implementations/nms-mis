package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="agg_whatsapp_subscribers")
public class WhatsAppSubscribers {
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

    @Column(name="active_whatsapp_subscribers", columnDefinition = "INT(11)")
    private Integer activeWhatsAppSubscribers;

    @Column(name = "new_whatsapp_opt_ins", columnDefinition = "INT(11)")
    private Integer newWhatsAppOptIns;

    @Column(name = "new_whatsapp_subscribers", columnDefinition = "INT(11)")
    private Integer newWhatsAppSubscribers;

    @Column(name = "self_whatsapp_deactivated_subscribers",columnDefinition = "INT(11)")
    private Integer selfWhatsAppDeactivatedSubscriber;

    @Column(name = "delivery_failure_whatsapp_deactivated_subscribers",columnDefinition = "INT(11)")
    private Integer deliveryFailureWhatsAppDeactivatedSubscriber;

    @Column(name = "mother_pack_completed_subscribers", columnDefinition = "INT(11")
    private Integer motherPackCompletedSubscribers;

    @Column(name = "child_pack_completed_subscribers", columnDefinition = "INT(11")
    private Integer childPackCompletedSubscribers;

    @Column(name="period_type", columnDefinition = "VARCHAR(45)")
    private String periodType;

    public WhatsAppSubscribers() {
    }

    public WhatsAppSubscribers(Integer id, String locationType, Long locationId, Date date, Integer activeWhatsAppSubscribers, Integer newWhatsAppOptIns, Integer newWhatsAppSubscribers, Integer selfWhatsAppDeactivatedSubscriber, Integer deliveryFailureWhatsAppDeactivatedSubscriber, Integer motherPackCompletedSubscribers, Integer childPackCompletedSubscribers, String periodType) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.date = date;
        this.activeWhatsAppSubscribers = activeWhatsAppSubscribers;
        this.newWhatsAppOptIns = newWhatsAppOptIns;
        this.newWhatsAppSubscribers = newWhatsAppSubscribers;
        this.selfWhatsAppDeactivatedSubscriber = selfWhatsAppDeactivatedSubscriber;
        this.deliveryFailureWhatsAppDeactivatedSubscriber = deliveryFailureWhatsAppDeactivatedSubscriber;
        this.motherPackCompletedSubscribers = motherPackCompletedSubscribers;
        this.childPackCompletedSubscribers = childPackCompletedSubscribers;
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

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
}
