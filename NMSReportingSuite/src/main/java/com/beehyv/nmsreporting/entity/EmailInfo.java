package com.beehyv.nmsreporting.entity;

import java.io.File;

/**
 * Created by beehyv on 16/5/17.
 */
public class EmailInfo {
    private String from = "", to = "", subject = "", body = "";
    private String fileName = "";
    private String rootPath = "";
    private String fileName2="";
    private String rootPathForFile2="";

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getFileName2() {
        return fileName2;
    }

    public void setFileName2(String fileName2) {
        this.fileName2 = fileName2;
    }

    public String getRootPathForFile2() {
        return rootPathForFile2;
    }

    public void setRootPathForFile2(String rootPathForFile2) {
        this.rootPathForFile2 = rootPathForFile2;
    }
}