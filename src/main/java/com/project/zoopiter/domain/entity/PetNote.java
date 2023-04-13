package com.project.zoopiter.domain.entity;

import java.time.LocalDate;

public class PetNote {
  private String userId;      // 회원 ID
  private byte[] petImg;      // 펫 사진
  private String petType;     // 반려동물 품종
  private String petGender;   // 반려동물 성별
  private Long petBirth;      // 반려동물 생일
  private String petName;     // 반려동물 이름
  private String YN;          // 중성화 여부
  private String petInfo;     // 반려동물 기타사항
  private Long petWeig;       // 반려동물 몸무게
  private LocalDate petHCheck;   // 병원 방문날짜
  private String petHName;    // 방문 병원이름
  private String petHTeacher; // 담당수의사
  private String petReason;   // 병원 내방이유
  private String petStmp;     // 동물 증상
  private String petSignice;  // 유의사항
  private LocalDate petNextdate;  // 다음 예약일
  private String petVac;      // 기초접종여부

  // 필수입력
  public PetNote(String userId, String petGender, String petName, LocalDate petHCheck) {
    this.userId = userId;
    this.petGender = petGender;
    this.petName = petName;
    this.petHCheck = petHCheck;
  }
}
