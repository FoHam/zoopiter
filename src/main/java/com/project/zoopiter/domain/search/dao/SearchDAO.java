package com.project.zoopiter.domain.search.dao;

import com.project.zoopiter.domain.entity.BBSH;

import java.util.List;

public interface SearchDAO {

  //검색
  List<BBSH> searchWord(String keyword, int startRec, int endRec);
}
