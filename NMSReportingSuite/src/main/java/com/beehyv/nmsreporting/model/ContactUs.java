package com.beehyv.nmsreporting.model;


import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="contactus")
public class ContactUs {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="contactus_id")
    private Integer contactUsId;

    @Column(name="name")
    @Pattern(regexp="^[A-Za-z ][A-Za-z0-9 ]*$")
    private String name;

    @Column(name="phone_no")
    private String phoneNumber;

    @Column(name="email_id")
    private String emailId;


    @Column(name="message")
    private String message;

    public ContactUs() {
    }

    public ContactUs(String name, String phoneNumber, String emailId, String message) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.message = message;
    }

    public Integer getContactUsId() {
        return contactUsId;
    }

    public void setContactUsId(Integer contactUsId) {
        this.contactUsId = contactUsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
