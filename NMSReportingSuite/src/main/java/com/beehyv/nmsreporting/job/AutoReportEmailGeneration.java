package com.beehyv.nmsreporting.job;

import com.beehyv.nmsreporting.business.AdminService;
import com.beehyv.nmsreporting.business.EmailService;
import com.beehyv.nmsreporting.enums.ReportType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.beehyv.nmsreporting.enums.ReportType.maCourse;

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
        System.out.println("Report generation started");
        adminService.createFiles(maCourse.getReportType());
        adminService.createFolders(ReportType.maAnonymous.getReportType());
        adminService.createFiles(ReportType.maInactive.getReportType());
        adminService.createFiles(ReportType.lowUsage.getReportType());
        adminService.createFiles(ReportType.selfDeactivated.getReportType());
        adminService.createFiles(ReportType.sixWeeks.getReportType());
        adminService.createFiles(ReportType.lowListenership.getReportType());

        adminService.getCircleWiseAnonymousFiles(fromDate,toDate);
        System.out.println("MA_Anonymous reports generated");
        adminService.getCumulativeCourseCompletionFiles(toDate);
        System.out.println("MA_Course_Completion reports generated");
        adminService.getCumulativeInactiveFiles(toDate);
        System.out.println("MA_Inactive reports generated");
        adminService.getKilkariSixWeekNoAnswerFiles(fromDate,toDate);
        System.out.println("KilkariSixWeekNoAnswer reports generated");
        adminService.getKilkariLowListenershipDeactivationFiles(fromDate,toDate);
        System.out.println("LowListenershipDeactivation reports generated");
        adminService.getKilkariSelfDeactivationFiles(fromDate,toDate);
        System.out.println("KilkariSelfDeactivation reports generated");
        adminService.getKilkariLowUsageFiles(fromDate,toDate);
        System.out.println("KilkariLowUsage reports generated");
        System.out.println("Report generation done");

        return true;
    }

    public HashMap sendFirstMail() {
        HashMap reports = emailService.sendAllMails(ReportType.maAnonymous);
        System.out.println("MA_Anonymous: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendSecondMail() {
        HashMap reports = emailService.sendAllMails(ReportType.maCourse);
        System.out.println("MA_Course: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendThirdMail() {
        HashMap reports = emailService.sendAllMails(ReportType.maInactive);
        System.out.println("MA_Inactive: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendFourthMail() {
        HashMap reports = emailService.sendAllMails(ReportType.sixWeeks);
        System.out.println("Kilkari_SixWeeks: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendFifthMail() {
        HashMap reports = emailService.sendAllMails(ReportType.selfDeactivated);
        System.out.println("Kilkari_SelfDeactivated: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendSixthMail() {
        HashMap reports = emailService.sendAllMails(ReportType.lowUsage);
        System.out.println("Kilkari_LowUsage: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendWeeklyFirstMail() {
        HashMap reports = emailService.sendAllMails(ReportType.childRejected);
        System.out.println("Child Rejected reports: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendWeeklySecondMail() {
        HashMap reports = emailService.sendAllMails(ReportType.motherRejected);
        System.out.println("Mother Rejected reports: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendWeeklyThirdMail() {
        HashMap reports = emailService.sendAllMails(ReportType.flwRejected);
        System.out.println("Flw Rejected reports: ");
        System.out.println(reports.toString());
        return reports;
    }

    public void test(){
//        System.out.println(new Date());
    }
}
