package com.project.zoopiter.domain.common.mail;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@Slf4j
@SpringBootTest
class MailServiceTest {

  @Autowired
  private MailService mailService;

  @Test
  void sendSimpleMail() {
    // 인증 번호 생성
    Random random = new Random();
    int checkNum = random.nextInt(8888888) + 111111;
    log.info("checkNum={}",checkNum);

    StringBuffer str = new StringBuffer();
    str.append("<html>");
    str.append("<h3>인증번호 확인</h3>");
    str.append("<p>인증번호는" + checkNum + "입니다. 인증을 진행해주세요!</p>");
    str.append("<a href = 'http://localhost/login'>로그인</a>");
    str.append("</html>");
    mailService.sendMail("spodos37@gmail.com","제목",str.toString());
  }
}