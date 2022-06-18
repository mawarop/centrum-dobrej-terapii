package com.example.centrum_dobrej_terapii.exceptions;

import com.example.centrum_dobrej_terapii.exceptions.AsyncUncaughtExceptionHandleStrategy.AsyncUncaughtExceptionHandleStrategy;
import com.example.centrum_dobrej_terapii.exceptions.AsyncUncaughtExceptionHandleStrategy.MailSendExceptionHandleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Slf4j
public class GlobalAsyncExceptionHandler extends SimpleAsyncUncaughtExceptionHandler {
    AsyncUncaughtExceptionHandleStrategy strategy;
    private final String emailHost;
    private final String emailPort;

    public GlobalAsyncExceptionHandler(@Value("${spring.mail.host}") String emailHost, @Value("${spring.mail.port}") String emailPort) {
        this.emailHost = emailHost;
        this.emailPort = emailPort;
    }

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {


        if (MailSendException.class.equals(ex.getClass())) {
            strategy = new MailSendExceptionHandleStrategy(emailHost, emailPort);

        }

        if (strategy != null) {
            strategy.handle(ex);
        } else {
            super.handleUncaughtException(ex, method, params);
        }
    }
}
//    private static Logger LOG = LoggerFactory.getLogger(GlobalServiceExceptionHandler.class);
//
//    @ExceptionHandler(MailSendException.class)
//    public void handleMailSenderException(MailSendException mailSendException){
//        LOG.error("Can't send mail;" + mailSendException.getMessage() + ";nested exception message:" + Objects.requireNonNull(mailSendException.getRootCause()).getMessage());

