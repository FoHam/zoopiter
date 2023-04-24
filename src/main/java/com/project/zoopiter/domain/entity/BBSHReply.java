package com.project.zoopiter.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BBSHReply {
    private Long hcId;  //댓글 번호(순번)
    private Long bbshId;  //게시글 번호
    private String bcContent; //댓글 내용
    private String userNick;  //일반회원 닉네임
    private LocalDateTime bcCdate;  //작성일
    private LocalDateTime bcUdate;  //수정일
}
