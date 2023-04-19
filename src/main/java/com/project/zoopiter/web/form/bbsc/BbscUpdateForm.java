package com.project.zoopiter.web.form.bbsc;

import com.project.zoopiter.domain.entity.UploadFile;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BbscUpdateForm {
  private Long bbscId;      // 게시글 번호(순번)
  @NotNull
  private String bcTitle;   // 글 제목
  @NotNull
  private String bcContent; // 글 내용
  private String bcPublic;  // 글 공개 유무
  private String petType;   // 반려동물 품종

  private UploadFile attachedFile;        // 일반 파일
  private List<MultipartFile> imageFiles;  // 이미지파일
}
