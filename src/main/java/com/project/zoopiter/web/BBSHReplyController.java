package com.project.zoopiter.web;

import com.project.zoopiter.domain.BBSHReply.svc.BBSHReplySVC;
import com.project.zoopiter.domain.entity.BBSHReply;
import com.project.zoopiter.web.common.LoginMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/BBSHReply")
public class BBSHReplyController {

  private final BBSHReplySVC bbshReplySVC;

  // 댓글 작성
  @ResponseBody
  @PostMapping("/save")
  public RestResponse<Object> save(
      @RequestBody BBSHReply bbshReply,
      HttpServletRequest request
  ){
    log.info("bbshReply={}",bbshReply);
    RestResponse<Object> res = null;

    HttpSession session = request.getSession(false);
    if(session != null) {
      LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
      String userNick = loginMember.getUserNick();
      bbshReply.setUserNick(userNick);
    }

    // 댓글번호
    Long hcId = bbshReplySVC.save(bbshReply);
    res = RestResponse.createRestResponse("00","성공",hcId);

    return res;
  }

  // 댓글 삭제
  @ResponseBody
  @DeleteMapping("/del/{bcId}")
  public RestResponse<Object> delete(
      @PathVariable Long bcId
  ){
    RestResponse<Object> res = null;
    int cntOfDel = bbshReplySVC.deleteByHcid(bcId);
    res = RestResponse.createRestResponse("00","성공",cntOfDel);
    return res;
  }
}
