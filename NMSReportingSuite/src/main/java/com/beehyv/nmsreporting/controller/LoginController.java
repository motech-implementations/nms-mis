package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.utils.LoginUser;
import com.beehyv.nmsreporting.utils.LoginValidator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by beehyv on 15/3/17.
 */
@Controller
public class LoginController {

    private LoginValidator validator = new LoginValidator();

    @RequestMapping(value = {"/", "/nms", "/nms/login"}, method = RequestMethod.GET)
    protected String returnLoginView(Model model, @ModelAttribute LoginUser loginUser) {
//        System.out.println("\n\n" + SecurityUtils.getSubject().getSession()+ "!!!!!!!!!!!\n\n");
        ensureUserIsLoggedOut();
        return "redirect:http://localhost:8080/app/#!/login";
    }

    @RequestMapping(value={"/nms/login"}, method= RequestMethod.POST)
    public String login(Model model, @ModelAttribute LoginUser loginUser, BindingResult errors) {
        validator.validate(loginUser, errors);
        System.out.println("username = " + loginUser.getUsername());
        System.out.println("password = " + loginUser.getPassword());
        System.out.println("rememberme " + loginUser.isRememberMe());
        if( errors.hasErrors() ) {
            return returnLoginView(model, loginUser);
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginUser.getUsername(), loginUser.getPassword(), loginUser.isRememberMe());
        try {
            ensureUserIsLoggedOut();
            subject.login(token);
        } catch (AuthenticationException e) {
            errors.reject( "error.login.generic", "Invalid username or password.  Please try again." );
        }

        if( errors.hasErrors() ) {
            return returnLoginView(model, loginUser);
        } else {
            return "redirect:http://localhost:8080/app/#!/userManagement";
        }
    }

    @RequestMapping(value = {"/nms/index"}, method = RequestMethod.GET)
    protected String returnHomeView(Model model) {
//        System.out.println("\n\n" + SecurityUtils.getSubject().getSession()+ "!!!!!!!!!!!\n\n");
        return "redirect:http://localhost:8080/app/#!/userManagement";
    }

    @RequestMapping(value = {"/nms/loginDummy"}, method = RequestMethod.GET)
    protected @ResponseBody LoginUser dummy() {
//        System.out.println("\n\n" + SecurityUtils.getSubject().getSession()+ "!!!!!!!!!!!\n\n");
        return new LoginUser();
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