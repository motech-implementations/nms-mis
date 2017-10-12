package com.beehyv.nmsreporting.entity;

/**
 * Created by beehyv on 10/10/17.
 */
public class ListeningMatrixDto {

    String percentageCalls;
    Long content_75_100;
    Long content_50_75;
    Long content_25_50;
    Long content_1_25;
    Long total;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getPercentageCalls() {
        return percentageCalls;
    }

    public void setPercentageCalls(String percentageCalls) {
        this.percentageCalls = percentageCalls;
    }

    public Long getContent_75_100() {
        return content_75_100;
    }

    public void setContent_75_100(Long content_75_100) {
        this.content_75_100 = content_75_100;
    }

    public Long getContent_50_75() {
        return content_50_75;
    }

    public void setContent_50_75(Long content_50_75) {
        this.content_50_75 = content_50_75;
    }

    public Long getContent_25_50() {
        return content_25_50;
    }

    public void setContent_25_50(Long content_25_50) {
        this.content_25_50 = content_25_50;
    }

    public Long getContent_1_25() {
        return content_1_25;
    }

    public void setContent_1_25(Long content_1_25) {
        this.content_1_25 = content_1_25;
    }
}
