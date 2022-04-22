package com.example.centrum_dobrej_terapii.configs;

import com.example.centrum_dobrej_terapii.exceptions.GlobalAsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfiguration implements AsyncConfigurer {
    private final String emailHost;
    private final String emailPort;


    public AsyncConfiguration(@Value("${spring.mail.host}") String emailHost, @Value("${spring.mail.port}") String emailPort) {
        this.emailHost = emailHost;
        this.emailPort = emailPort;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new GlobalAsyncExceptionHandler(emailHost, emailPort);
    }
}
