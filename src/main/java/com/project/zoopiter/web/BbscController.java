package com.project.zoopiter.web;

import com.project.zoopiter.domain.bbsc.svc.BbscSVC;
import com.project.zoopiter.domain.common.file.svc.UploadFileSVC;
import com.project.zoopiter.domain.entity.Bbsc;
import com.project.zoopiter.domain.entity.UploadFile;
import com.project.zoopiter.web.common.AttachFileType;
import com.project.zoopiter.web.common.LoginMember;
import com.project.zoopiter.web.exception.BizException;
import com.project.zoopiter.web.form.bbsc.BbscDetailForm;
import com.project.zoopiter.web.form.bbsc.BbscSaveForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/bbsc")
@RequiredArgsConstructor
public class BbscController {
  private final BbscSVC bbscSVC;
  private final UploadFileSVC uploadFileSVC;

//  @Autowired
//  @Qualifier("fc10") //동일한 타입의 객체가 여러개있을때 빈이름을 명시적으로 지정해서 주입받을때
//  private FindCriteria fc;

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
    bbscDetailForm.setBcCdate(bbsc.getBcCdate());


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

  // 수정

  // 삭제


  //목록
  @GetMapping("/list")
  public String findAll(Model model){
    List<Bbsc> bbscList = bbscSVC.findAll();

    List<BbscDetailForm> bbscDetailForms = new ArrayList<>();
      bbscList.stream().forEach(bbsc -> {
        BbscDetailForm bbscDetailForm = new BbscDetailForm();
        bbscDetailForm.setBcContent(bbsc.getBcContent());
        bbscDetailForm.setBcHit(bbsc.getBcHit());
      });

    model.addAttribute("bbscList", bbscList);
    if(bbscList.size() == 0){
      throw new BizException("등록된 글이 없습니다!");
    }
    return "board_com/com_main";
  }

  // 페이징 구현
//  @GetMapping({"/list",
//                "/list/{reqPage}",
//                "/list/{reqPage}//",
//                "/list/{reqPage}/{searchType}/{keyword}"})
//  public String findAll(
//      @PathVariable(required = false) Optional<Integer> reqPage,
//      @PathVariable(required = false) Optional<String> searchType,
//      @PathVariable(required = false) Optional<String> keyword,
//      @RequestParam(required = false) Optional<String> category,
//      Model model){
//    log.info("/list 요청됨{},{},{},{}",reqPage,searchType,keyword,category);
//
//    String cate = getCategory(category);
//
//    //FindCriteria 값 설정
//    fc.getRc().setReqPage(reqPage.orElse(1)); //요청페이지, 요청없으면 1
//    fc.setSearchType(searchType.orElse(""));  //검색유형
//    fc.setKeyword(keyword.orElse(""));        //검색어
//
////    List<Bbsc> bbscList = bbscSVC.findAll();
//    List<Bbsc> bbscList = null;
//
//
//    List<BbscDetailForm> bbscDetailForms = new ArrayList<>();
//    bbscList.stream().forEach(bbsc -> {
//      BbscDetailForm bbscDetailForm = new BbscDetailForm();
//      bbscDetailForm.setBcContent(bbsc.getBcContent());
//      bbscDetailForm.setBcHit(bbsc.getBcHit());
//    });
//
//    model.addAttribute("bbscList", bbscList);
//    if(bbscList.size() == 0){
//      throw new BizException("등록된 글이 없습니다!");
//    }
//    return "board_com/com_main";
//  }

  //쿼리스트링 카테고리 읽기, 없으면 ""반환
  private String getCategory(Optional<String> category) {
    String cate = category.isPresent()? category.get():"";
    log.info("category={}", cate);
    return cate;
  }

}
