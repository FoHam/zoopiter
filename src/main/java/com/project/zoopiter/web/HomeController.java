package com.project.zoopiter.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {
  @GetMapping
  public String home(HttpServletRequest request){
    //인증번호 세션 있으면 오류나서 홈화면 올때는 이메일 인증번호 세션 삭제함
    HttpSession session = request.getSession(false);
    if(session != null && session.getAttribute("checkNum") != null){
      session.removeAttribute("checkNum");
    }
    return "index";
  }
}
