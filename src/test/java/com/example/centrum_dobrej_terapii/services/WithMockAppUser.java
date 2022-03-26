package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.UserRole;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockAppUserUserSecurityContextFactory.class)
public @interface WithMockAppUser {
    String email() default "example@gmail.com";
    UserRole userRole() default UserRole.PATIENT;
    String username() default "username";
    String password() default "password";


}
