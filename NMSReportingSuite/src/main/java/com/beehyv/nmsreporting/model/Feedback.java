package com.beehyv.nmsreporting.model;


import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="feedback_id")
    private Integer feedbackId;

    @Column(name="name")
    @Pattern(regexp="^[A-Za-z ][A-Za-z0-9 ]*$")
    private String name;

    @Column(name="subject")
    private String subject;

    @Column(name="phone_no")
    private String phoneNumber;

    @Column(name="email_id")
    private String emailId;


    @Column(name="feedback")
    private String feedback;

    public Feedback() {
    }


    public Feedback(String name, String subject, String phoneNumber, String emailId, String feedback) {
        this.name = name;
        this.subject = subject;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.feedback = feedback;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

}
