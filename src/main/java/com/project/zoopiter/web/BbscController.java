package com.project.zoopiter.web;

import com.project.zoopiter.domain.bbsc.dao.BbscFilterCondition;
import com.project.zoopiter.domain.bbsc.svc.BbscSVC;
import com.project.zoopiter.domain.bbscReply.svc.BbscReplySVC;
import com.project.zoopiter.domain.common.file.svc.UploadFileSVC;
import com.project.zoopiter.domain.common.paging.FindCriteria;
import com.project.zoopiter.domain.entity.Bbsc;
import com.project.zoopiter.domain.entity.BbscReply;
import com.project.zoopiter.domain.entity.Member;
import com.project.zoopiter.domain.entity.UploadFile;
import com.project.zoopiter.domain.member.svc.MemberSVC;
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
  private final BbscReplySVC bbscReplySVC;
  private final MemberSVC memberSVC;

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
      Model model,
      HttpServletRequest request
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

    // 댓글 총갯수
    int cntOfReplies = bbscReplySVC.countOfReplies(bbscId);
    model.addAttribute("cntOfReplies",cntOfReplies);
    // 댓글 조회
    Optional<List<BbscReply>> bbscReplies = bbscReplySVC.findByBbscId(bbscId);
    List<BbscReply> findedReplies = bbscReplies.get();
    model.addAttribute("findedReplies",findedReplies);
    log.info("findedReplies={}",findedReplies);

    //회원프로필 조회
    // 댓글 회원닉네임들 list에 저장
    List<String> userNickList = new ArrayList<>();

    for(BbscReply bbscReply  : findedReplies){
      userNickList.add(bbscReply.getUserNick());
    }

    Map<String, Long> profileMap = new LinkedHashMap<>();
    for(String userNick : userNickList){
      // 댓글 닉네임으로 찾은 회원정보
      Optional<Member> byUserNick = memberSVC.findByUserNick(userNick);
      if(byUserNick.isPresent()){
        // 찾은 회원정보에서 프로필 id(userPhoto)값 가져오기
        Long userPhoto = byUserNick.get().getUserPhoto();

        // userPhoto 값으로 프로필 사진 찾기
        List<UploadFile> profiles = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.F0104, userPhoto);

        if(!profiles.isEmpty()){
          profileMap.put(userNick,profiles.get(0).getUploadfileId());
        }
      }
    }

    model.addAttribute("profileMap", profileMap);

    // 로그인한 회원의 프로필사진
    String userNick = null;
    HttpSession session = request.getSession(false);
    if(session != null) {
      LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
      userNick = loginMember.getUserNick();

      Optional<Member> findByUserNick = memberSVC.findByUserNick(userNick);
      Long userPhoto = findByUserNick.get().getUserPhoto();

      List<UploadFile> loginProfiles = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.F0104, userPhoto);
      if(!loginProfiles.isEmpty()){
        model.addAttribute("loginProfiles",loginProfiles);
      }
    }else{
      return "redirect:/login";
    }

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


  // 페이징 구현(목록)
  // searchType: 조회순, 최신순 category: 동물태그
  @GetMapping({"/list",
                "/list/{reqPage}",
                "/list/{reqPage}/",
                "/list/{reqPage}/{searchType}/"})
  public String listAndReqPage(
      @PathVariable(required = false) Optional<Integer> reqPage,
      @RequestParam(required = false) Optional<String> searchType,
      @RequestParam(required = false) Optional<List<String>> category,
      Model model){
    log.info("/list 요청됨{},{},{}",reqPage,searchType,category);

    List<String> cate = getCategory(category);  // 펫태그 배열

    //FindCriteria 값 설정
    fc.getRc().setReqPage(reqPage.orElse(1)); //요청페이지, 요청없으면 1
    fc.setSearchType(searchType.orElse(""));  //검색유형(조회수,최신순)

    List<Bbsc> bbscList = null;
    List<String> arr = null;

    // 게시물 목록 전체
    if(category.isPresent()){
      if(searchType.isPresent()){ //검색어 있음(필터-최신,조회), 펫태그
        arr = category.get();
        log.info("arr={}",arr);
        log.info("searchType={}",searchType.get());
        BbscFilterCondition filterCondition = new BbscFilterCondition(
            arr, fc.getRc().getStartRec(), fc.getRc().getEndRec(),
            searchType.get()
        );

        fc.setTotalRec(bbscSVC.totalCount(filterCondition));
        fc.setSearchType(searchType.get());
        bbscList = bbscSVC.findByPetAndFilter(filterCondition);

      }else{  // 펫태그 있고 셀렉트 박스 값 없을 때
        arr = category.get();

        BbscFilterCondition filterCondition2 = new BbscFilterCondition(
            arr, fc.getRc().getStartRec(), fc.getRc().getEndRec(),
            ""
        );

        fc.setTotalRec(bbscSVC.totalCount(filterCondition2));
        fc.setCategory(category.get());
        bbscList = bbscSVC.findByPetType(filterCondition2);

      }
    }else if(searchType.isPresent()){ // 펫태그 없고 셀렉트 박스 값만 있을때
      List<String> emptyArr = new ArrayList<>();
      emptyArr.add("");
      BbscFilterCondition filterCondition = new BbscFilterCondition(
          emptyArr, fc.getRc().getStartRec(), fc.getRc().getEndRec(),
          searchType.get()
      );

      fc.setTotalRec(bbscSVC.totalCount(filterCondition));
      fc.setSearchType(searchType.get());
      bbscList = bbscSVC.findByFilter(filterCondition);
    }else{  //검색어 없음
      // 총레코드수
      fc.setTotalRec(bbscSVC.totalCount());
      log.info("startRec={},endRec={}",fc.getRc().getStartRec(),fc.getRc().getEndRec());
      bbscList = bbscSVC.findAll(fc.getRc().getStartRec(),fc.getRc().getEndRec());
    }

    // ListForm에 게시글 저장
      List<BbscListForm> partOfList = new ArrayList<>();
      for(Bbsc bbsc : bbscList){
        BbscListForm listForm = new BbscListForm();
        BeanUtils.copyProperties(bbsc, listForm);

        partOfList.add(listForm);
      }


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
          map.put(bbscId,imagedFiles.get(0).getUploadfileId());
        }
      }
      model.addAttribute("imagedFileMap", map);

    //회원프로필 조회
    // 게시글 회원닉네임들 list에 저장
    List<String> userNickList = new ArrayList<>();
    for(Bbsc bbsc : bbscList){
      userNickList.add(bbsc.getUserNick());
    }

    Map<String, Long> profileMap = new LinkedHashMap<>();
    for(String userNick : userNickList){
      // 게시글 닉네임으로 찾은 회원정보
      Optional<Member> byUserNick = memberSVC.findByUserNick(userNick);
      if(byUserNick.isPresent()){
        // 찾은 회원정보에서 프로필 id(userPhoto)값 가져오기
        Long userPhoto = byUserNick.get().getUserPhoto();

        // userPhoto 값으로 프로필 사진 찾기
        List<UploadFile> profiles = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.F0104, userPhoto);
        log.info("profiles={}",profiles);

        if(!profiles.isEmpty()){
          profileMap.put(userNick,profiles.get(0).getUploadfileId());
        }
      }
    }
          model.addAttribute("profileMap", profileMap);


      model.addAttribute("list",partOfList);
      model.addAttribute("fc",fc);
      model.addAttribute("petTag",cate);

    return "board_com/com_main";
  }

  //쿼리스트링 카테고리(펫태그) 읽기, 없으면 ""반환
  private List<String> getCategory(Optional<List<String>> category) {
    List<String> cate = category.isPresent()? category.get(): null;
    log.info("category={}",cate);
    return cate;
  }

}
