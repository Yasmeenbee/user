package New.example.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendConfirmationEmail(String to) {
        if (to == null || to.isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be null or empty.");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Registration Success");
        message.setText("Your registration was successful. Welcome to our platform!");
        javaMailSender.send(message);
    }
}
