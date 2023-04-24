package com.project.zoopiter.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BbscReply {
    private Long ccId;  //댓글 번호(순번)
    private Long bbscId;  //게시글 번호
    private String ccContent; //댓글 내용
    private String userNick;  //일반회원 닉네임
    private LocalDateTime ccCdate;  //작성일
    private LocalDateTime ccUdate;  //수정일
}
