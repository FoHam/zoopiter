package com.project.zoopiter.domain.search.svc;


import com.project.zoopiter.domain.entity.BBSH;

import java.util.List;

public interface SearchSVC {

  //검색
  List<BBSH> searchWord(String keyword, int startRec, int endRec);
}
