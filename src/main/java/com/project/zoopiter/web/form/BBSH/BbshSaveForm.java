package com.project.zoopiter.web.form.BBSH;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BbshSaveForm {
  //  @NotEmpty // null,빈문자열(""),공백문자(" ") 허용안함
  private Long bbshId;      // 공지아이디
  @NotNull //모든 타입에 대해 null 허용 안함
  private String bhTitle;       // 제목
  @NotNull
  private String bhContent;    // 본문
  private Long bhStar;        // 별점
  private String petType; // 반려동물 품종(카테고리)
  private Long bhHit;       // 조회수

  private List<MultipartFile> imageFiles;

  //  private MultipartFile attachFile;
//  @NotNull
//  private String author;      // 작성자
//  @Positive // 양수
//  private Long hit;           // 조회수
//  @NotNull
//  private Long cDate;         // 생성일시
//  @NotNull
//  private Long uDate;         // 변경일시
}
