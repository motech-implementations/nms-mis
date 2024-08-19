package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Notice;

import java.sql.Date;
import java.util.List;

public interface NoticeDao {
    public List<Notice> findByNoticeTypeAndDate(String noticeType, Date date, String status);
    public void saveAll(List<Notice> notices);
    public List<Notice> findByNoticeTypeAndMessageAndYear(String noticeType, int year, String message);
    List<Notice> findNoticeForLast28Days();

}
