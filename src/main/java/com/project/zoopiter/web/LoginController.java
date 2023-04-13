package com.project.zoopiter.web;

import com.project.zoopiter.domain.entity.Member;
import com.project.zoopiter.domain.member.svc.MemberSVC;
import com.project.zoopiter.web.login.LoginForm;
import com.project.zoopiter.web.login.LoginMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
  private final MemberSVC memberSVC;

  //로그인화면
  @GetMapping("/login")
  public String loginForm(Model model){
    model.addAttribute("loginForm", new LoginForm());
    return "member/member_login";
  }

  //로그인처리
  @PostMapping("/login")
  public String login(
      @Valid @ModelAttribute LoginForm loginForm,
      BindingResult bindingResult,
      HttpServletRequest httpServletRequest,
      @RequestParam(value="redirectUrl",required = false, defaultValue = "/") String redirectUrl
  ){

    log.info("redirectUrl={}",redirectUrl);
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "member/member_login";
    }

    //1)아이디 존재유무
    if(!memberSVC.isExistId(loginForm.getUserId())){
      bindingResult.rejectValue("userId","login","아이디가 존재하지 않습니다.");
      return "member/member_login";
    }

    //2)로그인
    Optional<Member> member = memberSVC.login(loginForm.getUserId(), loginForm.getUserPw());
    if(member.isEmpty()){
      bindingResult.rejectValue("userPw","login","비밀번호가 일치하지 않습니다.");
      return "member/member_login";
    }

    //3)세션 생성
    //세션이 있으면 해당 정보를 가져오고 없으면 세션생성
    HttpSession session = httpServletRequest.getSession(true);
    LoginMember loginMember = new LoginMember(
        member.get().getUserId(),
        member.get().getUserNick(),
        member.get().getGubun()
        );
    session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

    return "redirect:"+redirectUrl;
  }

  //로그아웃
  @GetMapping("/logout")
  public String logout(HttpServletRequest httpServletRequest){
    //세션이 있으면 해당 정보를 가져오고 없으면 세션생성 하지 않음
    HttpSession session = httpServletRequest.getSession(false);
    if(session != null){
      session.invalidate();   //세션 제거
    }
    return "redirect:/";
  }

}
