package com.project.zoopiter.web.form.bbsc;

import com.project.zoopiter.domain.entity.UploadFile;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BbscDetailForm {
  private Long bbscId;      // 게시글 번호(순번)
  private String bcTitle;   // 글 제목
  private String bcContent; // 글 내용
  private String petType;   // 반려동물 품종
  private Long bcHit;       // 조회수
  private Long bcLike;      // 좋아요수
  private String userNick;   // 일반회원 닉네임
  private LocalDateTime bcCdate;  // 작성일
  private List<UploadFile> imagedFiles;
}
