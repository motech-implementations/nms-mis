package com.beehyv.nmsreporting.controller;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import com.beehyv.nmsreporting.entity.EmailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;

/**
 * Created by beehyv on 16/5/17.
 */
@Controller
@RequestMapping("/nms/mail")
public class EmailController {
    @Autowired
    ServletContext context;
    @Autowired
    JavaMailSender mailSender;

    @RequestMapping(value = "/input", method = RequestMethod.GET)
    public @ResponseBody String showForm(ModelMap model) {
        model.addAttribute("mail", new EmailInfo());
        return "AttachEmailInput";
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public @ResponseBody String sendWithAttach(@RequestBody EmailInfo mailInfo) {
        try {
            final JavaMailSenderImpl ms = (JavaMailSenderImpl) mailSender;
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //get only username to show
            String frEmail = ms.getUsername();
            mailInfo.setFrom(frEmail);

            //do not need to setFrom so set in servlet-gmail.xml file
            //set name of account email if want to show name instead account
            helper.setFrom(new InternetAddress(null, "NMSReportingAdmin"));
            helper.setTo(mailInfo.getTo());
            //helper.setReplyTo(mailInfo.getFrom()); //if any
            helper.setSubject(mailInfo.getSubject());
            helper.setText(mailInfo.getBody(), false);
            FileSystemResource attachment = new FileSystemResource(new File(System.getProperty("user.home") + File.separator + mailInfo.getAttachment()));
            helper.addAttachment("firstFile", attachment);
            mailSender.send(message);
            return "success";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "failure";
        }
    }

    @RequestMapping(value = "/dummy", method = RequestMethod.GET)
    public @ResponseBody EmailInfo dummy(){
        return new EmailInfo();
    }
}
