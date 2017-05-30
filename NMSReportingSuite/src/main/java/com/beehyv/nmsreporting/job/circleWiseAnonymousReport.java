package com.beehyv.nmsreporting.job;

import com.beehyv.nmsreporting.business.AdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by beehyv on 29/5/17.
 */
@Component
public class circleWiseAnonymousReport  {

   /* private AdminService adminService;

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }*/


    @Bean
    public boolean executeInternal() {

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.MONTH, -1);
        aCalendar.set(Calendar.DATE, 1);

        Date fromDate = aCalendar.getTime();

        aCalendar.set(Calendar.DATE,aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        Date toDate = aCalendar.getTime();

        Date startDate=new Date(0);
        /*adminService.getCircleWiseAnonymousFiles(fromDate,toDate);*/
        System.out.println(fromDate+"to"+toDate);
        return true;
    }
}
