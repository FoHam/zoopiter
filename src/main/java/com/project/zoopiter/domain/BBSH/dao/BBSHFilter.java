package com.project.zoopiter.domain.BBSH.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class BBSHFilter {
  private String[] category;      //분류코드
  private int startRec;         //시작레코드번호
  private int endRec;           //종료레코드번호
  private String searchType;    //검색유형
//  private String keyword;       //검색어

  public BBSHFilter(String[] category , String searchType) {
    this.category = category;
    this.searchType = searchType;
//    this.keyword = keyword;
  }
}
