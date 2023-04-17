package com.project.zoopiter.web.form.bbsc;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BbscSaveForm {
  private Long bbscId;      // 게시글 번호(순번)
  @NotNull
  private String bcTitle;   // 글 제목
  @NotNull
  private String bcContent; // 글 내용
  private String bcPublic;  // 글 공개 유무
  private Long bcHit;       // 조회수
  private Long bcLike;      // 좋아요수
  private String petType;   // 반려동물 품종
  private String userNick; // 작성자 닉넴
  private List<MultipartFile> imageFiles;
}
