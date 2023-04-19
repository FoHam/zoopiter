package com.project.zoopiter.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetInfo {
  private Long petNum;    // Pet ID
  private String userId;    // 회원 ID
  private byte[] petImg;    // 반려동물 프로필 사진
  private String petType;   //반려동물 품종
  private LocalDate petBirth;    // 반려동물 생일
  private String petGender; //반려동물 성별
  private String petName; // 반려동물 이름
  private String petYn;      // 중성화 여부
  private LocalDate petDate;   // 입양일
  private String petVac;  // 기초접종 여부
  private String petInfo; // 기타사항

  // 필수입력
  public PetInfo(Long petNum, String userId, String petGender, String petName, String petVac) {
    this.petNum = petNum;
    this.userId = userId;
    this.petGender = petGender;
    this.petName = petName;
    this.petVac = petVac;
  }
}
