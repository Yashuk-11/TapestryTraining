package org.apache.tapestry.Sampleproject.pages;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Gmail {

    @Property
    private String email;

    @InjectComponent("Gmail")
    private Form emailForm;

    @OnEvent(value = "success", component = "Gmail")
    Object sendEmail() {
        // Set up email sending logic here using JavaMail or another library
    	
        String toEmail = email;
        String subject = "Login Successful";
        String message = "Dear chethan,Congratulations! You have successfully logged in.Enjoy your day ";

        // Configure SMTP properties (e.g., Gmail SMTP)
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        // Authenticate with your Gmail account
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               
            return new PasswordAuthentication("yashuk1234@gmail.com", "cavciyoaxsegjlis");
          
            }
        };
    
        // Create a session with the configured properties and authentication
        Session session = Session.getInstance(props, auth);
        
        try {
            // Create a MimeMessage
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("yashuk1107@gmail.com"));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            // Send the email
            Transport.send(mimeMessage);

            // Clear the email input field
            email = "";
            return Dashboard.class;            // You can also display a success message here if needed
        } catch (MessagingException e) {
            // Handle email sending failure (e.g., display an error message)
            e.printStackTrace();
        }
        return null;
    }
}


