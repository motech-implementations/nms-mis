package com.beehyv.nmsreporting.job;

import com.beehyv.nmsreporting.business.AdminService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by beehyv on 29/5/17.
 */
public class cumulativeCourseCompletionReport extends QuartzJobBean {

    private AdminService adminService;

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }


    @Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException {

//        adminService.getCumulativeCourseCompletionFiles();
    }
}
