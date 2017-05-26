package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.EmailService;
import com.beehyv.nmsreporting.entity.EmailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.util.Properties;

/**
 * Created by beehyv on 25/5/17.
 */
@Service("emailService")
@Transactional
public class EmailServiceImpl implements EmailService{

    @Autowired
    JavaMailSender mailSender;
    @Autowired
    ServletContext context;

    @Override
    public String sendMail(EmailInfo mailInfo) {
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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailInfo.getTo()));
            message.setSubject(mailInfo.getSubject(), "UTF-8");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(mailInfo.getBody());
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            String filename = mailInfo.getRootPath();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(mailInfo.getFileName());
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            return "success";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "failure";
        }
    }

    @Override
    public String getBody(String reportName, String monthAndYear, String name) {
        String body = "";
        body+= "Dear "+name+ "\n";
        body+= "This is the "+reportName+" Report for the month "+monthAndYear+ "\n";
        body+= "Thank You \n";
        body+= "This an auto-generated email. Please do not reply";
        return body;
    }
}
