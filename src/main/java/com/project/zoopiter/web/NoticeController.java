package com.project.zoopiter.web;

import com.project.zoopiter.domain.BBSH.svc.BBSHSVC;
import com.project.zoopiter.domain.BBSHReply.svc.BBSHReplySVC;
import com.project.zoopiter.domain.common.file.svc.UploadFileSVC;
import com.project.zoopiter.domain.common.pagingView.FindCriteriaView;
import com.project.zoopiter.domain.entity.BBSH;
import com.project.zoopiter.domain.entity.BBSHReply;
import com.project.zoopiter.domain.entity.UploadFile;
import com.project.zoopiter.web.common.AttachFileType;
import com.project.zoopiter.web.common.LoginMember;
import com.project.zoopiter.web.form.BBSH.BbshDetailForm;
import com.project.zoopiter.web.form.BBSH.BbshListForm;
import com.project.zoopiter.web.form.BBSH.BbshSaveForm;
import com.project.zoopiter.web.form.BBSH.BbshUpdateForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/BBSH")
@RequiredArgsConstructor
public class NoticeController {

  private final BBSHSVC BBSHSVC;
  private final UploadFileSVC uploadFileSVC;
  private final BBSHReplySVC bbshReplySVC;

  @Autowired
  @Qualifier("fc10View") //동일한 타입의 객체가 여러개있을때 빈이름을 명시적으로 지정해서 주입받을때
  private FindCriteriaView fc;


  // 등록양식
  @GetMapping("/add")
  public String saveForm(Model model) {
    model.addAttribute("saveForm", new BbshSaveForm());
    return "board_review/review_saveForm";
  }

  // 등록처리
  @PostMapping("/add")
  public String save(
      @Valid @ModelAttribute BbshSaveForm bbshSaveForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      HttpServletRequest request
  ) {
    BBSH bbsh = new BBSH();

    String userNick = null;
    HttpSession session = request.getSession(false);
    if(session != null) {
      LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
      userNick = loginMember.getUserNick();
      bbsh.setUserNick(userNick);
    }else{
      return "redirect:/login";
    }

    // 어노테이션 기반 검증
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return "board_review/review_saveForm";
    }

    // 등록
    bbsh.setBhTitle(bbshSaveForm.getBhTitle());
    bbsh.setBhContent(bbshSaveForm.getBhContent());
    bbsh.setPetType(bbshSaveForm.getPetType());
    bbsh.setBhStar(bbshSaveForm.getBhStar());

    // 파일첨부에 대한 메타정보추출 & 물리파일저장
    List<UploadFile> imageFiles = uploadFileSVC.convert(bbshSaveForm.getImageFiles(), AttachFileType.F010202);

    Long saveId = BBSHSVC.save(bbsh, imageFiles);
    redirectAttributes.addAttribute("bbshId", saveId);

    return "redirect:/BBSH/{bbshId}/detail";
  }

  // 조회
  @GetMapping("/{bbshId}/detail")
  public String findById(
      @PathVariable("bbshId") Long bbshId,
      Model model
  ) {
    Optional<BBSH> findedNotice = BBSHSVC.findById(bbshId);
    BBSH bbsh = findedNotice.orElseThrow();

    BbshDetailForm bbshDetailForm = new BbshDetailForm();
    bbshDetailForm.setBbshId(bbsh.getBbshId());
    bbshDetailForm.setBhTitle(bbsh.getBhTitle());
    bbshDetailForm.setBhContent(bbsh.getBhContent());
    bbshDetailForm.setPetType(bbsh.getPetType());
    bbshDetailForm.setBhHit(bbsh.getBhHit());
    bbshDetailForm.setBhStar(bbsh.getBhStar());
    bbshDetailForm.setBhCdate(bbsh.getBhCdate());
    bbshDetailForm.setBhUdate(bbsh.getBhUdate());

    //첨부파일조회
    List<UploadFile> imagedFiles = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.F010202, bbshId);
    if (imagedFiles.size() > 0) {
      log.info("ImagedFiles={}", imagedFiles);
      model.addAttribute("imagedFiles", imagedFiles);
    }

    // 댓글 총갯수
    int cntOfReplies = bbshReplySVC.countOfReplies(bbshId);
    model.addAttribute("cntOfReplies",cntOfReplies);
    // 댓글 조회
    Optional<List<BBSHReply>> bbshReplies = bbshReplySVC.findByBBSHId(bbshId);
    List<BBSHReply> findedReplies = bbshReplies.get();
    model.addAttribute("findedReplies",findedReplies);

    model.addAttribute("detailForm", bbshDetailForm);
    return "board_review/review_detailForm";
  }

  //   수정양식
  @GetMapping("/{bbshId}/edit")
  public String updateForm(
      @PathVariable("bbshId") Long bbshId,
      Model model
  ) {
    Optional<BBSH> findedNotice = BBSHSVC.findById(bbshId);
    BBSH bbsh = findedNotice.orElseThrow();

    BbshUpdateForm bbshUpdateForm = new BbshUpdateForm();
    BeanUtils.copyProperties(bbsh, bbshUpdateForm);
    model.addAttribute("updateForm", bbshUpdateForm);


    //첨부파일조회
    List<UploadFile> imagedFiles = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.F010202, bbshId);
    if (imagedFiles.size() > 0) {
      log.info("ImagedFiles={}", imagedFiles);
      model.addAttribute("imagedFiles", imagedFiles);
    }
    model.addAttribute("bbshId", bbshId);
    return "board_review/review_updateForm";
  }

  // 수정
  @PostMapping("/{bbshId}/edit")
  public String update(
      @PathVariable("bbshId") Long bbshId,
      @Valid @ModelAttribute BbshUpdateForm bbshUpdateForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {
    // 데이터 검증
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return "board_review/review_updateForm";
    }

    // 정상처리
    BBSH bbsh = new BBSH();
    BeanUtils.copyProperties(bbshUpdateForm,bbsh);
    BBSHSVC.update(bbshId, bbsh);

    // 파일첨부
    List<UploadFile> imageFiles = uploadFileSVC.convert(bbshUpdateForm.getImageFiles(),AttachFileType.F010202);

    if(bbshUpdateForm.getImageFiles().size() == 0){
      BBSHSVC.update(bbshId, bbsh);
    }else{
      BBSHSVC.update(bbshId, bbsh, imageFiles);
    }

    redirectAttributes.addAttribute("bbshId",bbshId);

    return "redirect:/BBSH/{bbshId}/detail";
  }

  // 삭제
  @GetMapping("/{bbshId}/del")
  public String deleteById(@PathVariable("bbshId") Long bbshId) {
    BBSHSVC.delete(bbshId, AttachFileType.F010202);
    return "redirect:/BBSH";
  }

//  //목록
//  @GetMapping
//  public String findAll(Model model){
//    List<BBSH> bbshes = BBSHSVC.findAll();
//
//    List<DetailForm> detailForms = new ArrayList<>();
//    bbshes.stream().forEach(bbsh->{
//      DetailForm detailForm = new DetailForm();
//      detailForm.setBhContent(bbsh.getBhContent());
//      detailForm.setPetType(bbsh.getPetType());
//      detailForm.setBhHit(bbsh.getBhHit());
//      detailForm.setBhCdate(bbsh.getBhCdate());
//      detailForm.setBhUdate(bbsh.getBhUdate());
////      detailForm.setBhTitle(bbsh.getBhHname());
////      detailForm.setUserNick(bbsh.getUserNick());
//    });
//
//    model.addAttribute("bbshes", bbshes);
//    if (bbshes.size() == 0) {
////      throw new BizException("등록된 상품정보가 없습니다");
//    }
//    return "BBSH/all";
//  }

  // 페이징 구현(목록)
  // searchType: 조회순, 최신순 category: 동물태그
//  @GetMapping({"/list",
//      "/list/{reqPage}",
//      "/list/{reqPage}//",
//      "/list/{reqPage}/{searchType}/"})
//  public String listAndReqPage(
//      @PathVariable(required = false) Optional<Integer> reqPage,
//      @PathVariable(required = false) Optional<String> searchType,
//      @RequestParam(required = false) Optional<String> category,
//      Model model) {
//    log.info("/list 요청됨{},{},{},{}",reqPage,searchType,category);
//
//    String cate = getCategory(category);    // 펫태그 배열
//
//    //FindCriteria 값 설정
//    fc.getRc().setReqPage(reqPage.orElse(1)); //요청페이지, 요청없으면 1
//    fc.setSearchType(searchType.orElse(""));  //검색유형(조회수,최신순)
//
//    List<BBSH> bbshList = null;
//
//    // 게시물 목록 전체
//    if(!category.isPresent()){
//      String[] arr = {};
//
//      if(searchType.isPresent()){ //검색어 있음(필터-최신,조회)
//        BBSHFilter filterCondition = new BBSHFilter(
//            arr, fc.getRc().getStartRec(), fc.getRc().getEndRec(),
//            searchType.get()
//        );
//
//        fc.setTotalRec(BBSHSVC.totalCount(filterCondition));
//        fc.setSearchType(searchType.get());
//        bbshList = BBSHSVC.findByFilter(filterCondition);
//
//      } else if (category.isPresent()) { //검색어 있음(펫태그)
//        arr = category.get();
//
//        BBSHFilter filterCondition2 = new BBSHFilter(
//            arr, fc.getRc().getStartRec(), fc.getRc().getEndRec(),
//            searchType.get()
//        );
//
//        fc.setTotalRec(bbscSVC.totalCount(filterCondition2));
//        fc.setCategory(category.get());
//        bbscList = bbscSVC.findByPetType(filterCondition2);
//
//      }else{  //검색어 없음
//        // 총레코드수
//        fc.setTotalRec(bbscSVC.totalCount());
//        log.info("startRec={},endRec={}",fc.getRc().getStartRec(),fc.getRc().getEndRec());
//        bbscList = bbscSVC.findAll(fc.getRc().getStartRec(),fc.getRc().getEndRec());
//      }
//    }
//
//    List<BbscListForm> partOfList = new ArrayList<>();
//    for(Bbsc bbsc : bbscList){
//      BbscListForm listForm = new BbscListForm();
//      BeanUtils.copyProperties(bbsc, listForm);
//      partOfList.add(listForm);
//    }
//
//    model.addAttribute("list",partOfList);
//    model.addAttribute("fc",fc);
//    model.addAttribute("petTag",cate);
//
//    return "BBSH/list";
//  }
//
//
//
//    //쿼리스트링 카테고리 읽기, 없으면 ""반환
//    private String getCategory(Optional<String> category) {
//      String cate = category.isPresent()? category.get():"";
//      log.info("category={}", cate);
//      return cate;
//    }

  //구인글 목록 페이징
  @GetMapping({"", "/{reqPage}", "/{reqPage}//"})
  public String listPaging(
      @PathVariable(required = false) Optional<Integer> reqPage,
      Model model
  ) {

    fc.getRc().setReqPage(reqPage.orElse(1));
    fc.setTotalRec(BBSHSVC.totalCount());
    log.info("fc={}",fc);
    log.info("fc.getTotalRec={},startPage={},endPage={},fc.reqPage={},fc.startRec={},fc.endRed={}",
        fc.getTotalRec(),fc.getStartPage(),fc.getEndPage(),fc.getRc().getReqPage(),fc.getRc().getEndRec());
    List<BBSH> bbshListsPaging = BBSHSVC.findAllPaging(fc.getRc().getStartRec(), fc.getRc().getEndRec());
    log.info("bbshListsPaging={},{}", bbshListsPaging.size(),bbshListsPaging);


    List<BbshListForm> partOfList = new ArrayList<>();
    for (BBSH bbsh : bbshListsPaging) {
      BbshListForm listForm = new BbshListForm();
      BeanUtils.copyProperties(bbsh, listForm);
//      listForm.setBbshId(bbsh.getBbshId());
//      listForm.setPetType(bbsh.getPetType());
//      listForm.setBhContent(bbsh.getBhContent());
//      listForm.setUserNick(bbsh.getUserNick());
//      listForm.setBhCdate(bbsh.getBhCdate());
//      listForm.setBhUdate(bbsh.getBhUdate());
//      listForm.setBhHit(bbsh.getBhHit());
      partOfList.add(listForm);
    }
    log.info("partOfList={}", partOfList);
    model.addAttribute("bbshLists", partOfList);
    model.addAttribute("fc", fc);

    return "board_review/review_all";
  }
}

