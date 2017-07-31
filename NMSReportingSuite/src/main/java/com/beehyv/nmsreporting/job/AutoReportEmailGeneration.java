package com.beehyv.nmsreporting.job;

import com.beehyv.nmsreporting.business.AdminService;
import com.beehyv.nmsreporting.business.EmailService;
import com.beehyv.nmsreporting.controller.EmailController;
import com.beehyv.nmsreporting.enums.ReportType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.beehyv.nmsreporting.enums.ReportType.*;

/**
 * Created by beehyv on 31/5/17.
 */

public class AutoReportEmailGeneration {
    @Autowired
    AdminService adminService;

    @Autowired
    EmailService emailService;
    public boolean executeInternal() {

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.MONTH, -1);
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);

        Date fromDate = aCalendar.getTime();

        aCalendar.add(Calendar.MONTH, 1);

        Date toDate = aCalendar.getTime();

        Date startDate=new Date(0);
        /*adminService.getCircleWiseAnonymousFiles(fromDate,toDate);*/
        System.out.println(fromDate+"tooo"+toDate);
        adminService.createFiles(maCourse.getReportType());
        adminService.createFolders(ReportType.maAnonymous.getReportType());
        adminService.createFiles(ReportType.maInactive.getReportType());
        adminService.createFiles(ReportType.lowUsage.getReportType());
        adminService.createFiles(ReportType.selfDeactivated.getReportType());
        adminService.createFiles(ReportType.sixWeeks.getReportType());

        adminService.getCircleWiseAnonymousFiles(fromDate,toDate);
        adminService.getCumulativeCourseCompletionFiles(toDate);
        adminService.getCumulativeInactiveFiles(toDate);
        adminService.getKilkariSixWeekNoAnswerFiles(fromDate,toDate);
        adminService.getKilkariSelfDeactivationFiles(fromDate,toDate);
        adminService.getKilkariLowUsageFiles(fromDate,toDate);

        return true;
    }

    public HashMap sendFirstMail() {
        return emailService.sendAllMails(ReportType.maAnonymous);
    }

    public HashMap sendSecondMail() {
        return emailService.sendAllMails(ReportType.maCourse);
    }

    public HashMap sendThirdMail() {
        return emailService.sendAllMails(ReportType.maInactive);
    }

    public HashMap sendFourthMail() {
        return emailService.sendAllMails(ReportType.sixWeeks);
    }

    public HashMap sendFifthMail() {
        return emailService.sendAllMails(ReportType.selfDeactivated);
    }

    public HashMap sendSixthMail() {
        return emailService.sendAllMails(ReportType.lowUsage);
    }

    public void test(){
//        System.out.println(new Date());
    }
}
