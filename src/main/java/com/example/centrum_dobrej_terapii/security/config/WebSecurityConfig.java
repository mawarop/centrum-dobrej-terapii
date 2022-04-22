package com.example.centrum_dobrej_terapii.security.config;

import com.example.centrum_dobrej_terapii.services.AppUserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableWebSecurity
@Configuration
@CrossOrigin

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserServiceImpl appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ObjectMapper objectMapper;
    private final String secret;
    private final JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler;
    private final JsonAuthenticationFailureHandler jsonAuthenticationFailureHandler;

    public WebSecurityConfig(AppUserServiceImpl appUserService, BCryptPasswordEncoder bCryptPasswordEncoder, ObjectMapper objectMapper, @Value("${jwt.secret}") String secret
            , JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler, JsonAuthenticationFailureHandler jsonAuthenticationFailureHandler) {
        this.appUserService = appUserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.objectMapper = objectMapper;
        this.secret = secret;
        this.jsonAuthenticationSuccessHandler = jsonAuthenticationSuccessHandler;
        this.jsonAuthenticationFailureHandler = jsonAuthenticationFailureHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Brak autoryzacji do testów
//        http.cors().and().csrf().disable().authorizeRequests().antMatchers("/").permitAll();

        //Prawidłowa autoryzacja www-x-form-urlencoded
//        http.cors().and().csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/registration/**").permitAll()
//                .antMatchers("/api/patient/**").hasAuthority("PATIENT")
//                .antMatchers("/api/doctor/**").hasAuthority("DOCTOR")
//                .antMatchers("/api/admin/**").hasAuthority("ADMIN")
//                .antMatchers("/api/documents/**").hasAnyAuthority("ADMIN", "DOCTOR")
//
//                .anyRequest()
//                .authenticated()
//                .and().formLogin()
//                .loginPage("/login")
////                .loginProcessingUrl("/login")
//                .successHandler(new MyAuthenticationSuccessHandler())
//                .failureHandler(new MyAuthenticationFailureHandler())
//                .and().logout()
//                .logoutUrl("/logout")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID");


        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/registration/**").permitAll()
                .antMatchers("/api/patient/**").hasAuthority("PATIENT")
                .antMatchers("/api/doctor/**").hasAuthority("DOCTOR")
                .antMatchers("/api/admin/**").hasAuthority("ADMIN")
                .antMatchers("/api/documents/**").hasAnyAuthority("ADMIN", "DOCTOR")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(doAuthentication())
                .addFilter(new JWTAuthorizationFilter(this.authenticationManager(), secret, appUserService))
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        ;

    }

    private JsonAuthenticationFilter doAuthentication() throws Exception {
        JsonAuthenticationFilter jsonAuthenticationFilter = new JsonAuthenticationFilter(objectMapper);
        jsonAuthenticationFilter.setAuthenticationSuccessHandler(jsonAuthenticationSuccessHandler);
        jsonAuthenticationFilter.setAuthenticationFailureHandler(jsonAuthenticationFailureHandler);
        jsonAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
        return jsonAuthenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
////        configuration.setAllowedMethods(Arrays.asList("*"));
//        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }


//    @Component
//    public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//        @Override
//        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//            AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String responseText = String.format("{\"role\": \"%s\", \"email\": \"%s\"}", principal.getUserRole().name(),
//                    principal.getEmail());
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.getWriter().write(responseText);
//            response.getWriter().flush();
//        }
//    }
//
//    @Component
//    public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
//
//        @Override
//        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }
//    }


}
