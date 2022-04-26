package pl.raton.rockpaperscissors.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import pl.raton.rockpaperscissors.config.AdminConfig;
import pl.raton.rockpaperscissors.domain.Mail;

@Service
public class SimpleEmailService {

    private AdminConfig adminConfig;
    private JavaMailSender javaMailSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    @Autowired
    public SimpleEmailService(AdminConfig adminConfig, JavaMailSender javaMailSender) {
        this.adminConfig = adminConfig;
        this.javaMailSender = javaMailSender;
    }

    public SimpleEmailService() {
    }

    public void send(final Mail mail) {

        LOGGER.info("Starting email preparation...");

        try {
            javaMailSender.send(createMailMessage(mail));

            LOGGER.info("Email has been sent.");

        } catch (MailException e) {
            e.printStackTrace();
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }
}
