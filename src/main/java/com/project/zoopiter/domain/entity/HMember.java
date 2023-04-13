package com.project.zoopiter.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HMember {
  //자동 매핑으로 값을 받오기 때문에 스네이크 케이스로 되어있는 sql문 컬럼이름을
  //자바 객체에서는 카멜케이스로 하여 선언을 한다

  private String h_id;
  private String h_pw;
  private String h_name;
  private String h_email;
  private String h_tel;
  private String h_time;
  private String h_info;
  private String h_addinfo;
  private String h_plist;
  private String gubun;
  private LocalDateTime h_create_date;
  private LocalDateTime h_update;

  //필수 입력사항 따로 표시
  public HMember(String h_id, String h_pw, String h_email) {
    this.h_id = h_id;
    this.h_pw = h_pw;
    this.h_email = h_email;
  }
}
