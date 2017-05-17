package com.beehyv.nmsreporting.entity;

import java.io.File;

/**
 * Created by beehyv on 16/5/17.
 */
public class EmailInfo {
    private String from = "", to = "", subject = "", body = "";
    private String attachment = "";

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {

        this.body = body;
    }

}