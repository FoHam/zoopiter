package com.project.zoopiter.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
  //자동 매핑으로 값을 받오기 때문에 스네이크 케이스로 되어있는 sql문 컬럼이름을
  //자바 객체에서는 카멜케이스로 하여 선언을 한다
  private String userId;
  private String userPw;
  private String userEmail;
  private String userNick;
  private String gubun;
  private Long userPhoto;
  private LocalDateTime userCreateDate;
  private LocalDateTime userUpdate;
  //필수 입력사항 따로 표시
  public Member(String userId, String userPw, String userEmail) {
    this.userId = userId;
    this.userPw = userPw;
    this.userEmail = userEmail;
  }
}
