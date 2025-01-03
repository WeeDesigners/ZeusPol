package agh.edu.zeuspol.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class OnetEmailService {

    private JavaMailSender mailSender;

    @Value("${zeuspol.email.address}")
    public String email;
    @Value("${zeuspol.email.password}")
    public String password;

    public void sendSimpleEmail(String to, String subject, String text) {
        setupEmailSending();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(this.email);


        mailSender.send(message);
    }

    private void setupEmailSending() {
        if (this.mailSender == null){
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.poczta.onet.pl");
            mailSender.setPort(587);

            mailSender.setUsername(this.email);
            mailSender.setPassword(this.password);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            this.mailSender = mailSender;
        }
    }
}