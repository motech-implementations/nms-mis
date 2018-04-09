package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="thematic_details")
public class Theme {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "TINYINT")
    private Integer id;

    @Column(name="message_id")
    private String messageId;


    @Column(name="kilkari_message_title", columnDefinition = "BIGINT(20)")
    private String title;

    @Column(name="theme", columnDefinition = "TIMESTAMP")
    private String theme;

    public Theme() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
