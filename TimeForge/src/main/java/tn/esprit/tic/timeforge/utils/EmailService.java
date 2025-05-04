package tn.esprit.tic.timeforge.utils;


import jakarta.mail.Transport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.Mail;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender ;

    public void sendSimpleMessage (final Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        message.setTo(mail.getTo());
        message.setFrom(mail.getFrom());

        javaMailSender.send(message);
    }
}
