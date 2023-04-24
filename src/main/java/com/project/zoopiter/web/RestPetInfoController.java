package com.project.zoopiter.web;

import com.project.zoopiter.domain.common.file.svc.UploadFileSVC;
import com.project.zoopiter.domain.entity.Member;
import com.project.zoopiter.domain.entity.UploadFile;
import com.project.zoopiter.domain.member.svc.MemberSVC;
import com.project.zoopiter.web.common.AttachFileType;
import com.project.zoopiter.web.common.LoginMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class RestPetInfoController {

  private final MemberSVC memberSVC;
  private final UploadFileSVC uploadFileSVC;

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

  //비밀번호 수정
  @ResponseBody
  @GetMapping("/updateNick")
  public RestResponse<Object> updateNick(@RequestParam("nick") String nick, HttpServletRequest request) {
    log.info("nick={}", nick);
    RestResponse<Object> res = null;

    String userId = null;
    HttpSession session = request.getSession(false);
    if(session != null) {
      LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
      userId = loginMember.getUserId();
    }

    Member member = new Member();
    member.setUserNick(nick);

    boolean exist = memberSVC.updateNick(userId,member);
    if(exist){
      LoginMember loginMember = (LoginMember) session.getAttribute(SessionConst.LOGIN_MEMBER); //세션에 저장된 LoginMember 객체 가져오기
      loginMember.setUserNick(nick); //값 변경
      session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember); //세션에 변경된 LoginMember 객체 저장
    };

//    redirectAttributes.addAttribute("id", modifyForm.getUserId());
//    return "redirect:/mypage";
    res = RestResponse.createRestResponse("00", "성공", exist);

    return res;
  }

  // 회원탈퇴
  @GetMapping("/withdraw")
  public void withdraw(HttpServletRequest request){
    String userId = null;
    HttpSession session = request.getSession(false);
    if(session != null) {
      LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
      userId = loginMember.getUserId();
    }
    memberSVC.delete(userId);
    request.getSession().invalidate();
  }

  // 회원탈퇴 홈으로
  @GetMapping("/withdraw2")
  public void withdraw2(HttpServletRequest request){
    request.getSession().invalidate();
  }

  @PostMapping("/memberedit")
  public void memberedit(@RequestParam("file") MultipartFile file, HttpServletRequest request){
    log.info("실행중1~~~~~~~~~~~~~~~");
    String userId = null;
    HttpSession session = request.getSession(false);
    if(session != null) {
      LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
      userId = loginMember.getUserId();
    }
    Optional<Member> optionalMember = memberSVC.findById(userId);

    Member member = optionalMember.get();
    List<MultipartFile> image = new ArrayList<MultipartFile>();
    image.add(file);
    Long PhotoNum = member.getUserPhoto();
    log.info("실행중2~~~~~~~~~~~~~~~");

    // 파일첨부
    List<UploadFile> imagedFiles = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.F0104, PhotoNum);
    log.info("실행중3~~~~~~~~~~~~~~~");

    if (imagedFiles.size() > 0) {
      // 새로운 사진 파일이 있을 경우, 기존 사진 파일 삭제
      uploadFileSVC.deleteFileByUploadFildId(imagedFiles.get(0).getUploadfileId());

      //넣기
      List<UploadFile> imageFiles = uploadFileSVC.convert(image,AttachFileType.F0104);
      if(imageFiles.size()>0){
        imageFiles.stream().forEach(uploadFile -> uploadFile.setRid(PhotoNum));
        uploadFileSVC.addFiles(imageFiles);
      }
    } else {
      List<UploadFile> imageFiles = uploadFileSVC.convert(image,AttachFileType.F0104);
      if(imageFiles.size()>0){
        imageFiles.stream().forEach(uploadFile -> uploadFile.setRid(PhotoNum));
        uploadFileSVC.addFiles(imageFiles);
      }
    }
  }
}
