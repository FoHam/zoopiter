package com.project.zoopiter;

import com.project.zoopiter.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //세션체크
    registry.addInterceptor(new LoginCheckInterceptor())
//        .order(1)                 // 인터페이스 실행순서 지정
        .addPathPatterns("/**")   // 인터셉터에 포함시키는 url패턴, 루트경로부터 하위경로 모두
        .excludePathPatterns(
            "/",          //초기화면
            "/login",     //로그인
            "/logout",    //로그아웃
            "/members/add", //회원가입
            "/members/add2", //병원회원가입
            "/members/join", //약관페이지
            "/css/**",
            "/js/**",
            "/img/**",
            "/api/**"
        );  //인터셉터에서 제외되는 url패턴
  }
}
