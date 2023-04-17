package com.project.zoopiter.web;

import com.project.zoopiter.domain.entity.Member;
import com.project.zoopiter.domain.member.svc.MemberSVC;
import com.project.zoopiter.web.common.LoginMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class RestPetInfoController {

  private final MemberSVC memberSVC;

  //비밀번호체크
  @ResponseBody
  @GetMapping("/pw")
  public RestResponse<Object> isExistPw(@RequestParam("pw") String pw, HttpServletRequest request) {
    log.info("pw={}", pw);
    RestResponse<Object> res = null;

    String userId = null;
    HttpSession session = request.getSession(false);
    if(session != null) {
      LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
      userId = loginMember.getUserId();
    }

    //비밀번호 검증
    boolean exist = memberSVC.isExistPw(userId,pw);
    res = RestResponse.createRestResponse("00", "성공", exist);

    return res;
  }

  //비밀번호 수정
  @ResponseBody
  @GetMapping("/updatePw")
  public RestResponse<Object> updatePw(@RequestParam("pw") String pw, HttpServletRequest request) {
    log.info("pw={}", pw);
    RestResponse<Object> res = null;

    String userId = null;
    HttpSession session = request.getSession(false);
    if(session != null) {
      LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
      userId = loginMember.getUserId();
    }

    Member member = new Member();
    member.setUserPw(pw);


    //비밀번호 수정확인
    boolean exist = memberSVC.updatePw(userId,member);
    res = RestResponse.createRestResponse("00", "성공", exist);

    return res;
  }
}
