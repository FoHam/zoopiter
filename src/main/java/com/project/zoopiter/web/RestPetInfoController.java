//package com.project.zoopiter.web;
//
//import com.project.zoopiter.domain.member.svc.MemberSVC;
//import com.project.zoopiter.web.common.LoginMember;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController // @Controller + @ResponseBody
//@RequiredArgsConstructor
//@RequestMapping("/api/mypage")
//public class RestPetInfoController {
//
//  private final MemberSVC memberSVC;
//  @ResponseBody
//  @GetMapping("/pw")
//  public RestResponse<Object> isExistPw(@RequestParam("pw") String pw, HttpServletRequest request) {
//    log.info("pw={}", pw);
//    RestResponse<Object> res = null;
//
//    String userId = null;
//    HttpSession session = request.getSession(false);
//    if(session != null) {
//      LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
//      userId = loginMember.getUserId();
//    }
//
//    //비밀번호 검증
//    boolean exist = memberSVC.isExistPw(userId,pw);
//    res = RestResponse.createRestResponse("00", "성공", exist);
//
//    return res;
//  }
//}
