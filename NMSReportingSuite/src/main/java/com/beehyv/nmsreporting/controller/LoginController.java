package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.LoginTrackerService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.entity.BasicValidationResult;
import com.beehyv.nmsreporting.model.LoginTracker;
import com.beehyv.nmsreporting.model.User;
import com.beehyv.nmsreporting.utils.LoginUser;
import com.beehyv.nmsreporting.utils.LoginValidator;
import com.beehyv.nmsreporting.utils.ServiceFunctions;
import com.captcha.botdetect.web.servlet.SimpleCaptcha;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.beehyv.nmsreporting.utils.CryptoService.decrypt;
import static com.beehyv.nmsreporting.utils.Global.retrieveUiAddress;

/**
 * Created by beehyv on 15/3/17.
 */
@Controller
public class LoginController extends HttpServlet{

    private LoginValidator validator = new LoginValidator();

    @Autowired
    private LoginTrackerService loginTrackerService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/nms", "/nms/login"}, method = RequestMethod.GET)
    protected String returnLoginView(Model model, @ModelAttribute LoginUser loginUser) {
//        System.out.println("\n\n" + SecurityUtils.getSubject().getSession()+ "!!!!!!!!!!!\n\n");
        ensureUserIsLoggedOut();
        return "redirect:"+ retrieveUiAddress() +"login";
    }

    @ResponseBody
    @RequestMapping(value={"/nms/login"}, method= RequestMethod.POST)
    public String login(@RequestBody LoginUser loginUser, BindingResult errors, HttpServletResponse response) throws Exception {
        ServiceFunctions serviceFunctions = new ServiceFunctions();
        if(serviceFunctions.validateCaptcha(loginUser.getCaptchaResponse()).equals("success"))
        {
            User user = userService.findUserByUsername(loginUser.getUsername());
            if(user ==  null){
                return   retrieveUiAddress() + "login?error";
            }
            if (user!= null && user.getUnSuccessfulAttempts() == null) {
                user.setUnSuccessfulAttempts(0);
                userService.setUnSuccessfulAttemptsCount(user.getUserId(), 0);
            }
            if (user!= null && user.getUnSuccessfulAttempts() == 3) {
                Date lastLogin = loginTrackerService.getLastLoginTime(user.getUserId());
                if (lastLogin != null) {
                    long diff = TimeUnit.DAYS.convert(new Date().getTime() - lastLogin.getTime(), TimeUnit.MILLISECONDS);
                    if ( diff >= 1) {
                        user.setUnSuccessfulAttempts(0);
                        userService.setUnSuccessfulAttemptsCount(user.getUserId(), 0);
                    }

                }
            }

            if (user!= null && (user.getUnSuccessfulAttempts() < 3 || user.getUnSuccessfulAttempts() == null)) {
                validator.validate(loginUser, errors);
                if (errors.hasErrors()) {
                    ensureUserIsLoggedOut();
                    return "redirect:" + retrieveUiAddress() + "login?error";
                }
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(loginUser.getUsername(), decrypt(loginUser), loginUser.isRememberMe());
                try {
                    ensureUserIsLoggedOut();
                    subject.login(token);
                } catch (AuthenticationException e) {
                    errors.reject("error.login.generic", "Invalid username or password.  Please try again.");
                }
                user = userService.findUserByUsername(loginUser.getUsername());
                if (errors.hasErrors()) {
                    LoginTracker loginTracker = new LoginTracker();
                    if ((user) != null) {
                        userService.setUnSuccessfulAttemptsCount(user.getUserId(), null);
                        loginTracker.setUserId(user.getUserId());
                        loginTracker.setLoginSuccessful(false);
                        loginTracker.setLoginTime(new Date());
                        loginTrackerService.saveLoginDetails(loginTracker);
                    }
                    ensureUserIsLoggedOut();

                    return retrieveUiAddress() + "login?error";
                } else {
                    Calendar calendar = Calendar.getInstance();
                    Date currentDate = calendar.getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                    String formattedDateTime = dateFormat.format(currentDate);
                    String unique_id = formattedDateTime + "_" + user.getUsername();

                    user.setUnSuccessfulAttempts(0);
                    LoginTracker loginTracker = new LoginTracker();
                    loginTracker.setUserId(user.getUserId());
                    loginTracker.setLoginSuccessful(true);
                    loginTracker.setLoginTime(new Date());
                    loginTracker.setActive(true);
                    loginTracker.setUniqueId(unique_id);
                    loginTrackerService.saveLoginDetails(loginTracker);
                    Session session = SecurityUtils.getSubject().getSession();
                    session.setAttribute( "userName", user.getUsername());
                    session.setAttribute("unique_id" , unique_id);
                    if (user.getDefault() == null) {
                        user.setDefault(true);
                    }
                    if (user.getDefault()) {
                        return retrieveUiAddress() + "changePassword";
                    }
                    userService.setLoggedIn();
                    if (!user.getDefault() && (loginUser.getFromUrl() == null || loginUser.getFromUrl().equals(""))) {
                        return retrieveUiAddress() + "reports";
                    }
                    return loginUser.getFromUrl();

                }
            } else {
                return retrieveUiAddress() + "login?blocked";
            }
        }
        return "invalid captcha";

    }

    @RequestMapping(value = {"/nms/index"}, method = RequestMethod.GET)
    protected String returnHomeView(Model model) {
//        System.out.println("\n\n" + SecurityUtils.getSubject().getSession()+ "!!!!!!!!!!!\n\n");
        return "redirect:" + retrieveUiAddress() +"userManagement";
    }

    @RequestMapping(value = {"/nms/loginDummy"}, method = RequestMethod.GET)
    protected @ResponseBody LoginUser dummy() {
//        System.out.println("\n\n" + SecurityUtils.getSubject().getSession()+ "!!!!!!!!!!!\n\n");
        return new LoginUser();
    }

    @Override
    @RequestMapping(value={"/nms/captcha"}, method= RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();

        response.setContentType("application/json; charset=utf-8");


        JsonParser parser = new JsonParser();
        JsonObject formDataObj = (JsonObject) parser.parse(request.getReader());
        String captchaId = formDataObj.get("captchaId").getAsString();
        String captchaCode = formDataObj.get("captchaCode").getAsString();

        // validate captcha
        SimpleCaptcha captcha = SimpleCaptcha.load(request);
        boolean isHuman = captcha.validate(captchaCode, captchaId);
       // the object that stores validation result
        BasicValidationResult validationResult = new BasicValidationResult();
        validationResult.setSuccess(isHuman);

        try {
            // write the validation result as json string for sending it back to client
            out.write(gson.toJson(validationResult));
        } catch(Exception ex) {
            out.write(ex.getMessage());
        } finally {
            out.close();
        }

    }


    private void ensureUserIsLoggedOut() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if(currentUser == null) {
                return;
            }

            currentUser.logout();
            Session session = currentUser.getSession(false);
            if(session == null) {
                return;
            }
            session.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}