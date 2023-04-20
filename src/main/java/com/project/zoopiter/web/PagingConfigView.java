package com.project.zoopiter.web;

import com.project.zoopiter.domain.common.pagingView.FindCriteriaView;
import com.project.zoopiter.domain.common.pagingView.PageCriteriaView;
import com.project.zoopiter.domain.common.pagingView.RecordCriteriaView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagingConfigView {

  private static final  int REC_COUNT_10_PER_PAGE = 10;
  private static final  int PAGE_COUNT_10_PER_PAGE = 10;

  private static final  int REC_COUNT_5_PER_PAGE = 5;
  private static final  int PAGE_COUNT_5_PER_PAGE = 5;

  @Bean
  public RecordCriteriaView rc10View(){
    return new RecordCriteriaView(REC_COUNT_10_PER_PAGE);
  }

  @Bean
  public PageCriteriaView pc10View(){
    return new PageCriteriaView(rc10View(), PAGE_COUNT_10_PER_PAGE);
  }

  @Bean
  public RecordCriteriaView rc5View(){
    return new RecordCriteriaView(REC_COUNT_5_PER_PAGE);
  }

  @Bean
  public PageCriteriaView pc5View(){
    return new PageCriteriaView(rc10View(), PAGE_COUNT_5_PER_PAGE);
  }

  @Bean
  public FindCriteriaView fc10View() {
    return new FindCriteriaView(rc10View(),PAGE_COUNT_10_PER_PAGE);
  }

  @Bean
  public FindCriteriaView fc5View() {
    return  new FindCriteriaView(rc5View(),PAGE_COUNT_5_PER_PAGE);
  }
}
