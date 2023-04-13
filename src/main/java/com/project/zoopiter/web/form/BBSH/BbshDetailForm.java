package com.project.zoopiter.web.form.BBSH;

import com.project.zoopiter.domain.entity.UploadFile;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BbshDetailForm {
  private Long bbshId;      // 공지아이디
  private String bhTitle;       // 제목
  private String bhContent;    // 본문
  private Long bhHit;         //조회수
  private String userNick;    //별칭
  private LocalDateTime bhCdate;      //작성일

//  private UploadFile attachedFile;
  private List<UploadFile> imagedFiles;
//  private String author;      // 작성자
//  private Long hit;           // 조회수
//  private Long cDate;         // 생성일시
//  private Long uDate;         // 변경일시
}
