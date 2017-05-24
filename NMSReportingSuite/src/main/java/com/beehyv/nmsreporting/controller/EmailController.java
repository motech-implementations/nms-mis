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

    @RequestMapping(value = "/input", method = RequestMethod.GET)
    public @ResponseBody String showForm(ModelMap model) {
        model.addAttribute("mail", new EmailInfo());
        return "AttachEmailInput";
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
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //get only username to show
//            String frEmail = ms.getJavaMailProperties().get("mail.smtp.email").toString();
//            mailInfo.setFrom(frEmail);
            message.setFrom(new InternetAddress(mailInfo.getFrom()));
            //do not need to setFrom so set in servlet-gmail.xml file
            //set name of account email if want to show name instead account
//            helper.setFrom(new InternetAddress(null, "NMSReportingAdmin"));
            message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(mailInfo.getTo()));
//            helper.setTo(mailInfo.getTo());
            //helper.setReplyTo(mailInfo.getFrom()); //if any
            message.setSubject(mailInfo.getSubject(),"UTF-8");
//            helper.setSubject(mailInfo.getSubject());
//            message.setText(mailInfo.getBody(),"UTF-8");
//            helper.setText(mailInfo.getBody(), false);
//            FileSystemResource attachment = new FileSystemResource(new File(System.getProperty("user.home") + File.separator + mailInfo.getAttachment()));
//            helper.addAttachment("firstFile.jpg", attachment);
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(mailInfo.getBody());

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = System.getProperty("user.home") + File.separator + mailInfo.getAttachment();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("abc.jpg");
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
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
