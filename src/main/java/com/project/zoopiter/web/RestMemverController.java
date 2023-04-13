package com.project.zoopiter.web;

import com.project.zoopiter.domain.common.mail.MailService;
import com.project.zoopiter.domain.entity.Member;
import com.project.zoopiter.domain.member.svc.MemberSVC;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Slf4j
@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class RestMemverController {

    private final MemberSVC memberSVC;
    private final MailService mailService;

    //회원닉네임 체크
    @ResponseBody
    @GetMapping("/nickname")
    public RestResponse<Object> isExistNick(@RequestParam("nickname") String nickname){
        log.info("nickname={}",nickname);
        RestResponse<Object> res = null;

        //아이디 검증
        boolean exist = memberSVC.isExistNick(nickname);
        res = RestResponse.createRestResponse("00","성공", exist);

        return res;
    }

    //회원아이디 체크
    @ResponseBody
    @GetMapping("/id")
    public RestResponse<Object> isExistId(@RequestParam("id") String id){
        log.info("id={}",id);
        RestResponse<Object> res = null;

        //아이디 검증
        boolean exist = memberSVC.isExistId(id);
        res = RestResponse.createRestResponse("00","성공", exist);

        return res;
    }

    //회원이메일 체크
    @ResponseBody
    @GetMapping("/email")
    public RestResponse<Object> isExistEmail(@RequestParam("email") String email){
        log.info("email={}",email);
        RestResponse<Object> res = null;

        //이메일 검증
        boolean exist = memberSVC.isExistEmail(email);
        res = RestResponse.createRestResponse("00","성공", exist);

        return res;
    }

    //이메일 인증 체크
    @ResponseBody
    @GetMapping("/emailChk")
    public RestResponse<Object> sendSimpleMail(@RequestParam("email") String email, HttpServletRequest httpServletRequest){
        log.info("email={}",email);
        RestResponse<Object> res = null;

        //랜덤번호 생성
        Random random = new Random();
        int checkNum = random.nextInt(8888888) + 111111;
        log.info("checkNum={}",checkNum);

        //이메일 보내기
        StringBuffer str = new StringBuffer();
        str.append("<html>");
        str.append("<h3>인증번호 확인</h3>");
        str.append("<p>인증번호는 " + checkNum + " 입니다. 인증을 진행해주세요!</p>");
        str.append("</html>");

        mailService.sendMail(email,"인증번호",str.toString());

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("checkNum", String.valueOf(checkNum));

        res = RestResponse.createRestResponse("00","성공", "이메일로 보낸 인증번호를 입력해 주세요.");

        return res;
    }

    @ResponseBody
    @GetMapping("/emailChk2")
    public RestResponse<Object> sendSimpleMail2(@RequestParam("num") int num, HttpServletRequest httpServletRequest){
        RestResponse<Object> res = null;
        // 입력된 인증번호와 세션에 저장된 인증번호 비교
        String numchk = String.valueOf(num);
        HttpSession session = httpServletRequest.getSession();
        String checkNum = (String) session.getAttribute("checkNum");
        if (checkNum == null || !checkNum.equals(numchk)) {
            res = RestResponse.createRestResponse("01","실패", "인증번호가 틀립니다.");
        } else if (checkNum.equals(numchk)) {
            // 세션에 저장된 인증번호 삭제
            session.removeAttribute("checkNum");
            res = RestResponse.createRestResponse("00","성공", "인증성공");
        };
        return res;
    }

    @ResponseBody
    @PostMapping("/signup1")
    public RestResponse<Object> signupSave(@RequestBody Member member){
        RestResponse<Object> res = null;

        //회원기입
        Member member2 = memberSVC.save(member);
        res = RestResponse.createRestResponse("00","성공",member2);

        return res;
    }

    @ResponseBody
    @PostMapping("/signup2")
    public RestResponse<Object> signupSave2(@RequestBody Member member){
        RestResponse<Object> res = null;

        //회원기입
        Member member2 = memberSVC.save2(member);
        res = RestResponse.createRestResponse("00","성공",member2);

        return res;
    }
}
