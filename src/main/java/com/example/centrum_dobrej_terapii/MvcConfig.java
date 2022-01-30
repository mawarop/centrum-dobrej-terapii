package com.example.centrum_dobrej_terapii;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public MvcConfig() {
        super();
    }

    // API

//    @Override
//    public void addViewControllers(final ViewControllerRegistry registry) {
////        registry.addViewController("/anonymous.html");
////
////        registry.addViewController("/login.html");
////        registry.addViewController("/homepage.html");
////        registry.addViewController("/admin/adminpage.html");
////        registry.addViewController("/accessDenied");
////        registry.addViewController("/index.html");
//
////            registry.addViewController("/{spring:\\w+}")
////                    .setViewName("forward:/");
////            registry.addViewController("/**/{spring:\\w+}")
////                    .setViewName("forward:/");
////            registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}")
////                    .setViewName("forward:/");
//
//    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("/static/");
//        registry.addResourceHandler("/*.js")
//                .addResourceLocations("/frontend/react/build/");
//        registry.addResourceHandler("/*.json")
//                .addResourceLocations("/frontend/react/build/");
//        registry.addResourceHandler("/*.ico")
//                .addResourceLocations("/frontend/react/build/");
//        registry.addResourceHandler("/index.html")
//                .addResourceLocations("/static/index.html/");
    }

    public class StaticConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        }
    }
}
