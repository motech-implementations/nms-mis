package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.LoginTrackerService;
import com.beehyv.nmsreporting.model.LoginTracker;
import com.beehyv.nmsreporting.utils.Global;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import static com.beehyv.nmsreporting.utils.Global.retrieveUiAddress;

/**
 * Created by beehyv on 21/3/17.
 */
@Controller
@RequestMapping("/nms/logout")
public class LogoutController extends AbstractController{

    @Autowired
    private LoginTrackerService loginTrackerService;
    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Session session = SecurityUtils.getSubject().getSession();
            String unique_id = (String) session.getAttribute("unique_id");
            LoginTracker loginTracker = loginTrackerService.getLoginTrackerByUniqueId(unique_id);
            loginTracker.setActive(false);
            loginTrackerService.updateLoginDetails(loginTracker);
        }
        catch (Exception e){
            System.out.println("test - exception found");
            e.printStackTrace();
        }

        SecurityUtils.getSubject().logout();
        return new ModelAndView("redirect:"+ retrieveUiAddress() +"login");
    }
}
