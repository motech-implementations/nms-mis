package com.beehyv.nmsreporting.job;

import com.beehyv.nmsreporting.business.AdminService;
import com.beehyv.nmsreporting.controller.EmailController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

import static com.beehyv.nmsreporting.enums.ReportType.*;

/**
 * Created by beehyv on 31/5/17.
 */

public class AutoReportEmailGeneration {
    @Autowired
    AdminService adminService;

    @Autowired
    EmailController emailController;
    public boolean executeInternal() {

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.MONTH, -1);
        aCalendar.set(Calendar.DATE, 1);

        Date fromDate = aCalendar.getTime();

        aCalendar.set(Calendar.DATE,aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        Date toDate = aCalendar.getTime();

        Date startDate=new Date(0);
        /*adminService.getCircleWiseAnonymousFiles(fromDate,toDate);*/
        System.out.println(fromDate+"tooo"+toDate);
        adminService.createFiles(maCourse.getReportType());
        adminService.getCumulativeCourseCompletionFiles(toDate);

        adminService.createFiles(maInactive.getReportType());
        adminService.getCumulativeInactiveFiles(toDate);
        adminService.createFolders(maAnonymous.getReportType());
        adminService.getCircleWiseAnonymousFiles(fromDate,toDate);
        /*adminService.createFiles(maAnonymous.getReportType());
        adminService.createFiles(maInactive.getReportType());
        adminService.createFiles(sixWeeks.getReportType());
        adminService.createFiles(lowUsage.getReportType());
        adminService.createFiles(selfDeactivated.getReportType());*/
        return true;
    }
}
