package com.project.zoopiter.web.form.BBSH;

import com.project.zoopiter.domain.entity.UploadFile;
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

  private Long bhHit;


  private MultipartFile attachFile;        // 일반 파일
  private List<MultipartFile> imageFiles;  // 이미지파일

  private UploadFile attachedFile;        // 일반 파일
  private List<UploadFile> imagedFiles;  // 이미지파일
}
