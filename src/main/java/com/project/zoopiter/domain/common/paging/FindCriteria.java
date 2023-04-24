package com.project.zoopiter.domain.common.paging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FindCriteria extends PageCriteria {
  private String searchType;
  private List<String> category;      //펫태그

  public FindCriteria(RecordCriteria rc, int pageCount) {
    super(rc, pageCount);
  }
}
