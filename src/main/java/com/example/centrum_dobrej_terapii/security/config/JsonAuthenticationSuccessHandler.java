package com.example.centrum_dobrej_terapii.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    public static final String AUTH_HEADER_NAME = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final String secret;
    private final long expirationDuration;

    //    private final ObjectMapper objectMapper;
    public JsonAuthenticationSuccessHandler(@Value("${jwt.secret}") String secret,
                                            @Value("${jwt.duration-to-expiraton-in-minutes}") long expirationDuration) {
        this.secret = secret;
        this.expirationDuration = expirationDuration;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        AppUser principal = (AppUser) authentication.getPrincipal();
        String principalEmail = principal.getEmail();
        String token = JWT.create()
                .withSubject(principalEmail)
                .withExpiresAt(Date.from(LocalDateTime.now().plusMinutes(expirationDuration).atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC256(secret));
        response.setHeader(AUTH_HEADER_NAME, TOKEN_PREFIX + token);

        String responseText = String.format("{\"role\": \"%s\", \"email\": \"%s\"}", principal.getUserRole().name(),
                principal.getEmail());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(responseText);
        response.getWriter().flush();
    }
}
