package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "notices")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column
    private String message;
    
    @Column
    private String status;

    @Column
    private String noticeType;

    public Notice(String noticeType, Date date, String message, String status) {
        this.noticeType = noticeType;
        this.date = date;
        this.message = message;
        this.status = status;
    }

    @Column(name = "date")
    private Date date;

    public Notice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

}
