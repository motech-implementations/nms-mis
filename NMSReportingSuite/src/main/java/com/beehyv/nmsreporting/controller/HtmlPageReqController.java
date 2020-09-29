package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.htmlpages.*;
import com.beehyv.nmsreporting.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/page")
public class HtmlPageReqController {

    @Autowired
    private UserService userService;



    // No authentication Required open
    @RequestMapping(value = {"/contactUs"}, method = RequestMethod.GET)
    public ResponseEntity aboutUsView() {

        String content= ContactUs.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/contactUsResponse"}, method = RequestMethod.GET)
    public ResponseEntity contactUsResponseView() {

        String content= ContactUsResponse.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/sitemap"}, method = RequestMethod.GET)
    public ResponseEntity sitemapView() {

        String content= Sitemap.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/forgotPassword"}, method = RequestMethod.GET)
    public ResponseEntity forgotPasswordView() {

        String content= ForgotPassword.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/helpPage"}, method = RequestMethod.GET)
    public ResponseEntity helpPageView() {

        String content= HelpPage.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }


    // No authentication Required close

//--------------------------------------------------------------------------------------

    // Only login required pages open

    @RequestMapping(value = {"/userManualWebsiteInformation"}, method = RequestMethod.GET)
    protected ResponseEntity userManualWebsiteformationView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= UserManualWebsiteInformation.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/userManualProfile"}, method = RequestMethod.GET)
    protected ResponseEntity userManualProfileView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= UserManualProfile.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/userManualMobileAcademyAgg"}, method = RequestMethod.GET)
    protected ResponseEntity userManualMobileAcademyAggView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= UserManualMobileAcademyAgg.pageContent;
        String content2= UserManualMobileAcademyAgg.pageContent2;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        m.put("pagecontent2",content2);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/userManualMobileAcademy"}, method = RequestMethod.GET)
    protected ResponseEntity userManualMobileAcademyView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= UserManualMobileAcademy.pageContent;
        String content2= UserManualMobileAcademy.pageContent2;
        String content3= UserManualMobileAcademy.pageContent3;
        String content4= UserManualMobileAcademy.pageContent4;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        m.put("pagecontent2",content2);
        m.put("pagecontent3",content3);
        m.put("pagecontent4",content4);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/userManualKilkariAgg"}, method = RequestMethod.GET)
    protected ResponseEntity userManualKilkariAggView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= UserManualKilkariAgg.pageContent;
        String content2= UserManualKilkariAgg.pageContent2;
        String content3= UserManualKilkariAgg.pageContent3;
        String content4= UserManualKilkariAgg.pageContent4;
        String content5= UserManualKilkariAgg.pageContent5;
        String content6= UserManualKilkariAgg.pageContent6;
        String content7= UserManualKilkariAgg.pageContent7;
        String content8= UserManualKilkariAgg.pageContent8;
        String content9= UserManualKilkariAgg.pageContent9;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        m.put("pagecontent2",content2);
        m.put("pagecontent3",content3);
        m.put("pagecontent4",content4);
        m.put("pagecontent5",content5);
        m.put("pagecontent6",content6);
        m.put("pagecontent7",content7);
        m.put("pagecontent8",content8);
        m.put("pagecontent9",content9);

        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/userManualKilkari"}, method = RequestMethod.GET)
    protected ResponseEntity userManualKilkariView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= UserManualKilkari.pageContent;
        String content2= UserManualKilkari.pageContent2;
        String content3= UserManualKilkari.pageContent3;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        m.put("pagecontent2",content2);
        m.put("pagecontent3",content3);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/userManual"}, method = RequestMethod.GET)
    protected ResponseEntity userManualView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= UserManual.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/aboutKilkari"}, method = RequestMethod.GET)
    protected ResponseEntity aboutKilkariView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= AboutKilkari.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/aboutMA"}, method = RequestMethod.GET)
    protected ResponseEntity aboutMAView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= AboutMA.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/changePassword"}, method = RequestMethod.GET)
    protected ResponseEntity changePasswordView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= ChangePassword.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/faq"}, method = RequestMethod.GET)
    protected ResponseEntity faqView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= Faq.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/faqAggregateInfo"}, method = RequestMethod.GET)
    protected ResponseEntity faqAggregateInfoView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= FaqAggregateInfo.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/faqGeneralInfo"}, method = RequestMethod.GET)
    protected ResponseEntity faqGeneralInfoView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= FaqGeneralInfo.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/faqLineListingInfo"}, method = RequestMethod.GET)
    protected ResponseEntity faqLineListingInfoView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= FaqLineListingInfo.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/faqLoginInfo"}, method = RequestMethod.GET)
    protected ResponseEntity faqLoginInfoView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= FaqLoginInfo.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/faqReportsInfo"}, method = RequestMethod.GET)
    protected ResponseEntity faqReportsInfoView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= FaqReportsInfo.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/feedbackForm"}, method = RequestMethod.GET)
    protected ResponseEntity feedbackFormView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= FeedbackForm.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/feedbackResponse"}, method = RequestMethod.GET)
    protected ResponseEntity feedbackResponseView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= FeedbackResponse.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    protected ResponseEntity profileView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= Profile.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/reports"}, method = RequestMethod.GET)
    protected ResponseEntity reportsView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= Reports.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/downloads"}, method = RequestMethod.GET)
    protected ResponseEntity downloadsView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= Downloads.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    // Only login required pages close

//---------------------------------------------------------------------------------------

    // Only admin are allowed for the following api open

    @RequestMapping(value = {"/bulkUser"}, method = RequestMethod.GET)
    protected ResponseEntity bulkUserView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        if(currentUser.getRoleName().equals("USER")){
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= BulkUserHtml.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/createUser"}, method = RequestMethod.GET)
    protected ResponseEntity createUserView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        if(currentUser.getRoleName().equals("USER")){
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= CreateUser.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/editUser"}, method = RequestMethod.GET)
    protected ResponseEntity editUserView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        if(currentUser.getRoleName().equals("USER")){
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= EditUser.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/userManualManagement"}, method = RequestMethod.GET)
    protected ResponseEntity userManualManagementView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        if(currentUser.getRoleName().equals("USER")){
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= UserManualManagement.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    @RequestMapping(value = {"/userTable"}, method = RequestMethod.GET)
    protected ResponseEntity userTableView() {
        User currentUser = userService.getCurrentUser();

        if(currentUser==null) {
            return ResponseEntity.badRequest().body("Access Denied");
        }
        if(currentUser.getRoleName().equals("USER")){
            return ResponseEntity.badRequest().body("Access Denied");
        }
        String content= UserTable.pageContent;

        Map<String, String> m = new HashMap<>();
        m.put("pagecontent",content);
        return ResponseEntity.ok().body(m);
    }

    // Only admin are allowed for the following api close


}
