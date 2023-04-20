package com.project.zoopiter.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalInfo {

  private Long H_NUM;                   //  병원등록번호?
  private Long HD_ID;                   //  동물병원 데이터번호
  private String H_ID;                  //  병원회원 아이디
  private String H_NAME;                //  병원 상호명
  private String H_TEL;                 //  병원 연락처
  private String H_PLIST;               //  진료동물
  private String H_TIME;                //  진료시간
  private String H_INFO;                //  시설정보
  private String H_ADDINFO;             //  상세설명
  private byte[] H_IMG;                 //  병원사진
  private LocalDateTime H_CREATE_DATE;  //  등록날짜
  private LocalDateTime H_UPDATE;       //  수정날짜

  public HospitalInfo(Long H_NUM, Long HD_ID, String H_ID, String H_NAME) {
    this.H_NUM = H_NUM;
    this.HD_ID = HD_ID;
    this.H_ID = H_ID;
    this.H_NAME = H_NAME;
  }
}
