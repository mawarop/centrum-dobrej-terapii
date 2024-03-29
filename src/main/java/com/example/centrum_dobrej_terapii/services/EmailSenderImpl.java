package com.example.centrum_dobrej_terapii.services;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailSenderImpl implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailSenderImpl.class);
    public static final String FROM = "cdt@gmail.com";
    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String subject, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(email, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(FROM);
            mailSender.send(mimeMessage);

        } catch (MessagingException exception) {
            LOGGER.error("failed to send email", exception);
            throw new IllegalStateException("failed to send email");
        }
    }
}
