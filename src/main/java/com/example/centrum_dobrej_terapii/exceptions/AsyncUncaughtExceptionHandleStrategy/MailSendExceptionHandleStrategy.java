package com.example.centrum_dobrej_terapii.exceptions.AsyncUncaughtExceptionHandleStrategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class MailSendExceptionHandleStrategy implements AsyncUncaughtExceptionHandleStrategy {
    public MailSendExceptionHandleStrategy(@Value("${spring.mail.host}") String host, @Value("${spring.mail.port}") String port) {
        this.host = host;
        this.port = port;
    }

    private final String host;
    private final String port;

    public void handle(Throwable ex) {
        log.warn("Can't send mail; Check if smtp server is woking correctly on " + host + ":" + port);

    }

}
