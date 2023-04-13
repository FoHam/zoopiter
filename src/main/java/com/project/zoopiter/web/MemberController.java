package com.project.zoopiter.web;

import com.project.zoopiter.domain.member.svc.MemberSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

  private final MemberSVC memberSVC;

  //회원가입약관동의
  @GetMapping("/join")
  public String clauseForm(){
    return "member/member_join";
  }

  //회원가입 양식
  @GetMapping("/add")
  public String joinForm1(){
    return "member/member_signup";
  }

  //회원가입처리
//  @PostMapping("/add")
//  public String join(@Valid @ModelAttribute JoinForm joinForm, BindingResult bindingResult){
//    Member member = new Member();
//    member.setUserId(joinForm.getUserId());
//    member.setUserPw(joinForm.getUserPw());
//    member.setUserNick(joinForm.getUserNick());
//    member.setGubun(joinForm.getGubun());
//    member.setUserEmail(joinForm.getUserEmail());
//
//    memberSVC.save(member);
//    return "member/member_joinSuccess";
//  }

  //병원회원 가입 양식
  @GetMapping("/add2")
  public String joinForm2(){
    return "member/member_signup2";
  }
}
