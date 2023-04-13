package com.project.zoopiter.domain.bbsc.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class BbscFilterCondition {
  private int startRec;   //시작레코드번호
  private int endRec;     //종료레코드번호
  private String searchType;  //검색유형
  private String petType;     //검색어(펫태그-강아지,고양이,소동물,기타)

  public BbscFilterCondition(String searchType, String petType) {
    this.searchType = searchType;
    this.petType = petType;
  }
}
