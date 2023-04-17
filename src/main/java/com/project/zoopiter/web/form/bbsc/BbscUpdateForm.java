package com.project.zoopiter.web.form.bbsc;

import com.project.zoopiter.domain.entity.UploadFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BbscUpdateForm {
  private Long bbscId;      // 게시글 번호(순번)
  private String bcTitle;   // 글 제목
  private String bcContent; // 글 내용
  private String petType;   // 반려동물 품종
  private Long bcHit;       // 조회수

  private MultipartFile attachFile;        // 일반 파일
  private List<MultipartFile> imageFiles;  // 이미지파일

  private UploadFile attachedFile;        // 일반 파일
  private List<UploadFile> imagedFiles;  // 이미지파일
}
