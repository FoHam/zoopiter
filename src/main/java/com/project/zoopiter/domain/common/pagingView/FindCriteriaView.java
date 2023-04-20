package com.project.zoopiter.domain.common.pagingView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class FindCriteriaView extends PageCriteriaView {

  private String searchType;
  private String keyword;

  public FindCriteriaView(RecordCriteriaView rc, int pageCount) {
    super(rc, pageCount);
  }
}
