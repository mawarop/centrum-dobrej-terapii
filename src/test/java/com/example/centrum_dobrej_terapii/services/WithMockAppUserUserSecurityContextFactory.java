package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockAppUserUserSecurityContextFactory implements WithSecurityContextFactory<WithMockAppUser> {

    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;
    @Override
    public SecurityContext createSecurityContext(WithMockAppUser annotation) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        AppUser principal = new AppUser(annotation.username(), annotation.password(), "90011757125",
                "firstname",
                "lastname",
                annotation.email(),
                "734723590",
                annotation.userRole(),
                true,
                true);
        Authentication authentication= new UsernamePasswordAuthenticationToken(principal, principal.getPassword(),
                principal.getAuthorities());
        securityContext.setAuthentication(authentication);
        return securityContext;
    }
}
