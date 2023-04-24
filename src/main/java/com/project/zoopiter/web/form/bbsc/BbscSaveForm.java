package com.project.zoopiter.web.form.bbsc;

import com.project.zoopiter.domain.entity.UploadFile;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BbscSaveForm {
  private Long bbscId;      // 게시글 번호(순번)
  @NotBlank
  private String bcTitle;   // 글 제목
  @NotBlank
  private String bcContent; // 글 내용
  private String bcPublic;  // 글 공개 유무
  private Long bcHit;       // 조회수
  private Long bcLike;      // 좋아요수
  private String petType;   // 반려동물 품종
  private String userNick; // 작성자 닉넴

  private UploadFile attachedFile;        // 일반 파일
  private List<MultipartFile> imageFiles;
}
