//package com.example.nestco.config;
//
//import com.example.nestco.interceptor.HeaderInterceptor;
//import okhttp3.Interceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
////    @Autowired
////    private HeaderInterceptor headerInterceptor;
////
////    @Autowired
////    public WebConfig(HeaderInterceptor headerInterceptor) {
////        this.headerInterceptor = headerInterceptor;
////    }
////
////    @Override
////    public void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(headerInterceptor)
////                .addPathPatterns("/**")
////                .excludePathPatterns("/static/**", "/loginForm", "/logout");
////    }
//
//}
