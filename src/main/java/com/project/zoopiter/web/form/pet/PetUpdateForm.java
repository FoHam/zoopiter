package com.project.zoopiter.web.form.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PetUpdateForm {
  private Long petNum;    // Pet ID
//  private String userId;    // 회원 ID
  private byte[] petImg;    // 반려동물 프로필 사진
  private String petType;   //반려동물 품종
  private LocalDate petBirth;    // 반려동물 생일
  @NotBlank
  private String petGender; //반려동물 성별
  @NotBlank
  @Size(min=1, max=10)
  private String petName; // 반려동물 이름
  private String petYn;      // 중성화 여부
  private LocalDate petDate;   // 입양일
  @NotBlank
  private String petVac;  // 기초접종 여부
  private String petInfo; // 기타사항
  // 이미지파일
  private List<MultipartFile> imageFiles;

  public PetUpdateForm() {
    this.imageFiles = new ArrayList<>();
  }
}
