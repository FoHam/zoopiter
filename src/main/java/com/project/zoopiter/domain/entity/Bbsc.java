package com.project.zoopiter.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bbsc {
  private Long bbscId;      // 게시글 번호(순번)
  private String bcTitle;   // 글 제목
  private String bcContent; // 글 내용
  private String petType;   // 반려동물 품종
  private byte[] bcAttach;  // 첨부파일
  private Long bcHit;       // 조회수
  private Long bcLike;      // 좋아요수
  private String bcPublic;  // 게시글 공개여부(공개: Y, 비공개: N)
  private String bcGubun;    // 게시판 구분(병원후기: B0101, 커뮤니티: B0102)
  private String userNick;   // 일반회원 닉네임
  private LocalDateTime bcCdate;  // 작성일
  private LocalDateTime bcUdate;  // 수정일

}
