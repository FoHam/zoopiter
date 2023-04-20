package com.project.zoopiter.web;

import com.project.zoopiter.domain.bbsc.dao.BbscFilterCondition;
import com.project.zoopiter.domain.bbsc.svc.BbscSVC;
import com.project.zoopiter.domain.common.file.svc.UploadFileSVC;
import com.project.zoopiter.domain.common.paging.FindCriteria;
import com.project.zoopiter.domain.entity.Bbsc;
import com.project.zoopiter.domain.entity.UploadFile;
import com.project.zoopiter.web.common.AttachFileType;
import com.project.zoopiter.web.common.LoginMember;
import com.project.zoopiter.web.form.bbsc.BbscDetailForm;
import com.project.zoopiter.web.form.bbsc.BbscListForm;
import com.project.zoopiter.web.form.bbsc.BbscSaveForm;
import com.project.zoopiter.web.form.bbsc.BbscUpdateForm;
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

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/bbsc")
@RequiredArgsConstructor
public class BbscController {
  private final BbscSVC bbscSVC;
  private final UploadFileSVC uploadFileSVC;

  @Autowired
  @Qualifier("fc10") //동일한 타입의 객체가 여러개있을때 빈이름을 명시적으로 지정해서 주입받을때
  private FindCriteria fc;

  // 글작성 양식
  @GetMapping("/add")
  public String saveForm(Model model){
    model.addAttribute("bbscSaveForm",new BbscSaveForm());
    return "board_com/com_saveForm";
  }

  //  글등록 처리
  @PostMapping("/add")
  public String save(
      @Valid @ModelAttribute BbscSaveForm bbscSaveForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      HttpServletRequest request
  ){
    Bbsc bbsc = new Bbsc();

    String userNick = null;
    HttpSession session = request.getSession(false);
    if(session != null) {
      LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
      userNick = loginMember.getUserNick();
      bbsc.setUserNick(userNick);
    }else{
      return "redirect:/login";
    }

    // 어노테이션 기반 검증
    if (bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "board_com/com_saveForm";
    }

    // 등록
    bbsc.setBcTitle(bbscSaveForm.getBcTitle());
    bbsc.setBcContent(bbscSaveForm.getBcContent());
    bbsc.setPetType(bbscSaveForm.getPetType());
    bbsc.setBcPublic(bbscSaveForm.getBcPublic());


    List<UploadFile> imageFiles = uploadFileSVC.convert(bbscSaveForm.getImageFiles(), AttachFileType.F0102);

    Long saveId = bbscSVC.save(bbsc, imageFiles);
    redirectAttributes.addAttribute("bbscId",saveId);

    log.info("imageFiles={}",imageFiles);

    return "redirect:/bbsc/{bbscId}/detail";
  }

  // 글 상세조회
  @GetMapping("/{bbscId}/detail")
  public String findById(
      @PathVariable("bbscId") Long bbscId,
      Model model
  ){
    Optional<Bbsc> findedWrite = bbscSVC.findById(bbscId);
    Bbsc bbsc = findedWrite.orElseThrow();

    BbscDetailForm bbscDetailForm = new BbscDetailForm();
    bbscDetailForm.setBbscId(bbsc.getBbscId());
    bbscDetailForm.setBcTitle(bbsc.getBcTitle());
    bbscDetailForm.setBcContent(bbsc.getBcContent());
    bbscDetailForm.setBcHit(bbsc.getBcHit());
    bbscDetailForm.setPetType(bbsc.getPetType());
    bbscDetailForm.setUserNick(bbsc.getUserNick());
    bbscDetailForm.setBcUdate(bbsc.getBcUdate());


    //첨부파일조회
    List<UploadFile> imagedFiles = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.F0102, bbscId);
    if(imagedFiles.size() > 0){
      log.info("ImagedFiles={}",imagedFiles);
      model.addAttribute("imagedFiles",imagedFiles);
    }
//    bbscDetailForm.setImagedFiles(imagedFiles);

    model.addAttribute("bbscDetailForm",bbscDetailForm);
    return "board_com/com_detailForm";
  }

  // 수정양식
  @GetMapping("/{bbscId}/edit")
  public String updateForm(
      @PathVariable Long bbscId,
      Model model
  ){
    Optional<Bbsc> findedItem = bbscSVC.findById(bbscId);
    Bbsc bbsc = findedItem.orElseThrow();

    BbscUpdateForm bbscUpdateForm = new BbscUpdateForm();
    BeanUtils.copyProperties(bbsc,bbscUpdateForm);
    model.addAttribute("bbscUpdateForm",bbscUpdateForm);

    //첨부파일조회
    List<UploadFile> imagedFiles = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.F0102, bbscId);
    if(imagedFiles.size() > 0){
      log.info("ImagedFiles={}",imagedFiles);
      model.addAttribute("imagedFiles",imagedFiles);
    }
    model.addAttribute("bbscId",bbscId);

    return "board_com/com_editForm";
  }

  // 수정처리
  @PostMapping("/{bbscId}/edit")
  public String update(
      @PathVariable Long bbscId,
      @Valid @ModelAttribute BbscUpdateForm bbscUpdateForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ){
    // 데이터 검증
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "board_com/com_editForm";
    }

    // 정상처리
    Bbsc bbsc = new Bbsc();
    BeanUtils.copyProperties(bbscUpdateForm,bbsc);
    bbscSVC.update(bbscId, bbsc);

    // 파일첨부
    List<UploadFile> imageFiles = uploadFileSVC.convert(bbscUpdateForm.getImageFiles(),AttachFileType.F0102);

    if(bbscUpdateForm.getImageFiles().size() == 0){
      bbscSVC.update(bbscId, bbsc);
    }else{
      bbscSVC.update(bbscId, bbsc, imageFiles);
    }

    redirectAttributes.addAttribute("bbscId",bbscId);

    return "redirect:/bbsc/{bbscId}/detail";
  }

  // 삭제
  @GetMapping("/{bbscId}/del")
  public String deleteById(@PathVariable Long bbscId){
    bbscSVC.delete(bbscId,AttachFileType.F0102);
    return "redirect:/bbsc/list";
  }



  //목록
//  @GetMapping("/list")
//  public String findAll(
//      Model model){
//    List<Bbsc> bbscList = bbscSVC.findAll();
//
//      bbscList.stream().forEach(bbsc -> {
//        BbscDetailForm bbscDetailForm = new BbscDetailForm();
//        bbscDetailForm.setBcContent(bbsc.getBcContent());
//        bbscDetailForm.setBcHit(bbsc.getBcHit());
//        bbscDetailForm.setBcPublic(bbsc.getBcPublic());
//        bbscDetailForm.setBcUdate(bbsc.getBcUdate());
//      });
//
//    model.addAttribute("bbscList", bbscList);
//    if(bbscList.size() == 0){
//      throw new BizException("등록된 글이 없습니다!");
//    }
//
//    // 게시글 id 값들 list에 저장
//    List<Long> bbscIdList = new ArrayList<>();
//    for (Bbsc bbsc : bbscList) {
//      bbscIdList.add(bbsc.getBbscId());
//    }
//
//    // key: 게시글 id value: 첨부파일 id로 맵에 저장
//    Map<Long, Long> map = new LinkedHashMap<>();
//    for (Long bbscId : bbscIdList) {
//      List<UploadFile> imagedFiles = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.F0102, bbscId);
//      if (imagedFiles.size() > 0) {
//        log.info("ImagedFiles={}", imagedFiles);
//        map.put(bbscId,imagedFiles.get(0).getUploadfileId());
//      }
//    }
//    model.addAttribute("imagedFileMap", map);
//    log.info("imagedFileMap={}", map);
//    return "board_com/com_main";
//  }

  // 페이징 구현(목록)
  // searchType: 조회순, 최신순 category: 동물태그
  @GetMapping({"/list",
                "/list/{reqPage}",
                "/list/{reqPage}/",
                "/list/{reqPage}/{searchType}/"})
  public String listAndReqPage(
      @PathVariable(required = false) Optional<Integer> reqPage,
      @PathVariable(required = false) Optional<String> searchType,
      @RequestParam(required = false) Optional<String[]> category,
      Model model){
    log.info("/list 요청됨{},{},{}",reqPage,searchType,category);

    String[] cate = getCategory(category);  // 펫태그 배열
//    log.info("cate={}",cate);

    //FindCriteria 값 설정
    fc.getRc().setReqPage(reqPage.orElse(1)); //요청페이지, 요청없으면 1
    fc.setSearchType(searchType.orElse(""));  //검색유형(조회수,최신순)

    List<Bbsc> bbscList = null;

    // 게시물 목록 전체
    if(!category.isPresent()){
      String[] arr = {};

      if(searchType.isPresent()){ //검색어 있음(필터-최신,조회)
        BbscFilterCondition filterCondition = new BbscFilterCondition(
            arr, fc.getRc().getStartRec(), fc.getRc().getEndRec(),
            searchType.get()
        );

        fc.setTotalRec(bbscSVC.totalCount(filterCondition));
        fc.setSearchType(searchType.get());
        bbscList = bbscSVC.findByFilter(filterCondition);

      } else if (category.isPresent()) { //검색어 있음(펫태그)
        arr = category.get();

        BbscFilterCondition filterCondition2 = new BbscFilterCondition(
            arr, fc.getRc().getStartRec(), fc.getRc().getEndRec(),
            searchType.get()
        );

        fc.setTotalRec(bbscSVC.totalCount(filterCondition2));
        fc.setCategory(category.get());
        bbscList = bbscSVC.findByPetType(filterCondition2);

      }else{  //검색어 없음
        // 총레코드수
        fc.setTotalRec(bbscSVC.totalCount());
        log.info("startRec={},endRec={}",fc.getRc().getStartRec(),fc.getRc().getEndRec());
        bbscList = bbscSVC.findAll(fc.getRc().getStartRec(),fc.getRc().getEndRec());
      }
    }

    // ListForm에 게시글 저장
      List<BbscListForm> partOfList = new ArrayList<>();
      for(Bbsc bbsc : bbscList){
        BbscListForm listForm = new BbscListForm();
        BeanUtils.copyProperties(bbsc, listForm);

        partOfList.add(listForm);
      }
      log.info("partOfList={}",partOfList);


    // 게시글 id 값들 list에 저장
      List<Long> bbscIdList = new ArrayList<>();
      for (Bbsc bbsc : bbscList) {
        bbscIdList.add(bbsc.getBbscId());
      }

      // key: 게시글 id value: 첨부파일 id로 맵에 저장
      Map<Long, Long> map = new LinkedHashMap<>();
      for (Long bbscId : bbscIdList) {
        List<UploadFile> imagedFiles = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.F0102, bbscId);
        if (imagedFiles.size() > 0) {
          log.info("ImagedFiles={}", imagedFiles);
          map.put(bbscId,imagedFiles.get(0).getUploadfileId());
        }
      }
      model.addAttribute("imagedFileMap", map);
      log.info("imagedFileMap={}", map);

      model.addAttribute("list",partOfList);
      model.addAttribute("fc",fc);
      model.addAttribute("petTag",cate);

    return "board_com/com_main";
  }

  //쿼리스트링 카테고리(펫태그) 읽기, 없으면 ""반환
  private String[] getCategory(Optional<String[]> category) {
    String[] result = {};
    if(category.isPresent()){
      result = category.get();
    }else{
      String[] cate = category.orElse(new String[0]); // 값이 없는 경우 길이가 0인 배열 생성
//      String[] emptyArray = new String[cate.length];
      result = Arrays.stream(cate).map(x -> "").toArray(String[]::new); // 배열의 모든 요소를 빈 문자열("")로 초기화
    }
    log.info("category={}", result);
    return result;
  }

}
