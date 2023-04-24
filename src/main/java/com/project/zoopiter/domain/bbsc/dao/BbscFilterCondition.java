package com.project.zoopiter.domain.bbsc.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class BbscFilterCondition {
  private List<String> category;      //펫태그
  private int startRec;         //시작레코드번호
  private int endRec;           //종료레코드번호
  private String searchType;    //검색유형: 조회수, 최신순

  public BbscFilterCondition(List<String> category, String searchType) {
    this.category = category;
    this.searchType = searchType;
  }
}
