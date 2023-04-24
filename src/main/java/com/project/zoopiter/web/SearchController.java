package com.project.zoopiter.web;

import com.project.zoopiter.domain.BBSH.svc.BBSHSVC;
import com.project.zoopiter.domain.common.paging.FindCriteria;
import com.project.zoopiter.domain.entity.BBSH;
import com.project.zoopiter.domain.search.svc.SearchSVC;
import com.project.zoopiter.web.form.BBSH.BbshListForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/searches")
@RequiredArgsConstructor
public class SearchController {

  private final SearchSVC searchSVC;
  private final BBSHSVC bbshsvc;

  @Autowired
  @Qualifier("fc10")
  private FindCriteria fc;

  //검색-텍스트
  @GetMapping({"/{keyword}","/{keyword}/{reqPage}","/{keyword}/{reqPage}//"})
  public String searchWord(
    @PathVariable("keyword") String keyword,
    @PathVariable(required = false) Optional<Integer> reqPage,
    Model model
  ) {

    fc.getRc().setReqPage(reqPage.orElse(1));
    fc.setTotalRec(bbshsvc.totalCount());
    List<BBSH> searchWords = searchSVC.searchWord(keyword, fc.getRc().getStartRec(), fc.getRc().getEndRec());

    List<BbshListForm> partOfList = new ArrayList<>();
    for (BBSH bbsh : searchWords) {
      BbshListForm listForm = new BbshListForm();
      BeanUtils.copyProperties(bbsh, listForm);
      partOfList.add(listForm);
    }
    model.addAttribute("bbshLists", partOfList);
    model.addAttribute("fc", fc);
    model.addAttribute("searchWords", searchWords);

    return "board_review/review_search";
  }

}
