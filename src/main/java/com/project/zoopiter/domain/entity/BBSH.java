package com.project.zoopiter.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BBSH {
  private Long bbshId;       // 게시글 번호
  private String bhTitle;   // 게시글 제목
  private String bhContent;   // 게시글 내용
  private String petType; // 반려동물 품종(카테고리)
  private byte[] bhAttach;  // 첨부파일
  private String bhHname;   // 병원이름
  private Long bhHit;       // 조회수
  private String bhGubun;   // 게시판 구분(병원후기: B0101, 커뮤니티: B0102)
  private String userNick;    // 일반회원 닉네임
  private LocalDateTime bhCdate ; // 작성일
  private LocalDateTime bhUdate;  // 수정일
}
