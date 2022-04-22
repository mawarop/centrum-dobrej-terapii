package com.example.centrum_dobrej_terapii.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.services.AppUserServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public static final String AUTH_HEADER_NAME = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private final String secret;
    private final AppUserServiceImpl appUserService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, String secret, AppUserServiceImpl appUserService) {
        super(authenticationManager);
        this.secret = secret;
        this.appUserService = appUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenWithPrefix = request.getHeader(AUTH_HEADER_NAME);
        if (tokenWithPrefix == null || tokenWithPrefix.isBlank()) {
            chain.doFilter(request, response);
            return;
        }
        String token = tokenWithPrefix.replace(TOKEN_PREFIX, "");
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();

        String username = verifier.verify(token).getSubject();
        if (username != null) {
//            UserDetails userDetails = appUserService.loadUserByUsername(username);
            Optional<AppUser> appUser = appUserService.getAppUser(username);
            if (appUser.isPresent()) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(appUser.get(),
                        null, appUser.get().getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        chain.doFilter(request, response);
    }
}
