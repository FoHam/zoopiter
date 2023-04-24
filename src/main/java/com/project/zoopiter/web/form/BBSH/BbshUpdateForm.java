package com.project.zoopiter.web.form.BBSH;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BbshUpdateForm {
  private Long bbshId;
  @NotNull //모든 타입에 대해 null 허용 안함
  private String bhTitle;       // 제목
  @NotNull
  private String bhContent;    // 본문
  private Long bhStar;        // 별점
  private String petType; // 반려동물 품종(카테고리)
  private Long bhHit;

  private String bhCdate ; // 작성일
  private String bhUdate;  // 수정일

  private MultipartFile attachFile;        // 일반 파일
  private List<MultipartFile> imageFiles;  // 이미지파일

}
