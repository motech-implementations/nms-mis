package com.beehyv.nmsreporting.controller;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.xml.ws.RequestWrapper;

import com.beehyv.nmsreporting.dao.UserDao;
import com.beehyv.nmsreporting.entity.EmailInfo;
import com.beehyv.nmsreporting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Properties;

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
    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/input", method = RequestMethod.GET)
    public @ResponseBody String showForm(ModelMap model) {
        model.addAttribute("mail", new EmailInfo());
        return "AttachEmailInput";
    }

    @RequestMapping(value = "/sendAll", method = RequestMethod.GET)
    public @ResponseBody String sendAllMails(){
        List<User> users = userDao.getAllUsers();
        for(User user: users){
            EmailInfo newMail = new EmailInfo();
            newMail.setFrom("Beehyv");
            newMail.setTo(user.getEmailId());
            String accessLevel = user.getAccessLevel();
            if(accessLevel.equalsIgnoreCase("NATIONAL")){
                //??????????
            }
        }
        return "Successfully sent all mails";
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public @ResponseBody String sendWithAttach(@RequestBody EmailInfo mailInfo) {
        try {
            final JavaMailSenderImpl ms = (JavaMailSenderImpl) mailSender;
            Properties props = ms.getJavaMailProperties();
            final String username = ms.getUsername();
            final String password = ms.getPassword();
            //need authenticate to server
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailInfo.getFrom()));
            message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(mailInfo.getTo()));
            message.setSubject(mailInfo.getSubject(),"UTF-8");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(mailInfo.getBody());
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            String filename = System.getProperty("user.home") + File.separator + mailInfo.getAttachment();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(mailInfo.getAttachment());
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
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
