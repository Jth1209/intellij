package edu.du.sb1024.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/myinfo","/board/**","/survey.do","/orders/**","/admin","/shipments")//로그인,메인 페이지,회원가입 페이지를 제외한 모든 부분에 적용.
                .excludePathPatterns("/css/**", "/js/**", "/images/**", "/fonts/**","/fragments/**");
    }
}
