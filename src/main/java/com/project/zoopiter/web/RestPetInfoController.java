package com.project.zoopiter.web;

import com.project.zoopiter.domain.member.svc.MemberSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class RestPetInfoController {

  private final MemberSVC memberSVC;
  @ResponseBody
  @GetMapping("/pw")
  public RestResponse<Object> isExistId(@RequestParam("pw") String pw) {
    log.info("pw={}", pw);
    RestResponse<Object> res = null;

    //비밀번호 검증
    boolean exist = memberSVC.isExistId(pw);
    res = RestResponse.createRestResponse("00", "성공", exist);

    return res;
  }
}
