package com.project.zoopiter.web;

import com.project.zoopiter.domain.BBSH.svc.BbshSVC;
import com.project.zoopiter.domain.common.file.svc.UploadFileSVC;
import com.project.zoopiter.domain.entity.BBSH;
import com.project.zoopiter.domain.entity.UploadFile;
import com.project.zoopiter.web.common.AttachFileType;
import com.project.zoopiter.web.form.BBSH.BbshDetailForm;
import com.project.zoopiter.web.form.BBSH.BbshSaveForm;
import com.project.zoopiter.web.form.BBSH.BbshUpdateForm;
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
@RequestMapping("/bbsh")
@RequiredArgsConstructor
public class NoticeController {

  private final BbshSVC bbshSVC;
  private final UploadFileSVC uploadFileSVC;

  // 등록양식
  @GetMapping("/add")
  public String saveForm(Model model){
    model.addAttribute("bbshSaveForm", new BbshSaveForm());
    return "board_review/review_saveForm";
  }

  // 등록처리
  @PostMapping("/add")
  public String save(
      @Valid @ModelAttribute BbshSaveForm bbshSaveForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
    ){

    // 데이터 검증
    // 어노테이션 기반 검증
    if (bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "board_review/review_saveForm";
    }

    // 등록
    BBSH bbsh = new BBSH();
//    bbsh.setBbshId(saveForm.getBbshId());
    bbsh.setBhTitle(bbshSaveForm.getBhTitle());
    bbsh.setBhContent(bbshSaveForm.getBhContent());
//    bbsh.setBhAttach(saveForm.getAttachFile());
//    BBSH.setAuthor(saveForm.getAuthor());
//    BBSH.setHit(saveForm.getHit());
//    BBSH.setCDate(saveForm.getCDate());
//    BBSH.setUDate(saveForm.getUDate());

    // 파일첨부에 대한 메타정보추출 & 물리파일저장
//    UploadFile attachFile = uploadFileSVC.convert(saveForm.getAttachFile(), AttachFileType.F010201);
    List<UploadFile> imageFiles = uploadFileSVC.convert(bbshSaveForm.getImageFiles(), AttachFileType.F010202);
//    if(attachFile != null) imageFiles.add(attachFile);

    Long saveId = bbshSVC.save(bbsh,imageFiles);
    redirectAttributes.addAttribute("bbshId", saveId);

    return "redirect:/bbsh/{bbshId}/detail";
  }

  // 조회
  @GetMapping("/{bbshId}/detail")
  public String findById(
          @PathVariable("bbshId") Long bbshId,
          Model model
  ){
    Optional<BBSH> findedNotice = bbshSVC.findById(bbshId);
    BBSH bbsh = findedNotice.orElseThrow();

    BbshDetailForm bbshDetailForm = new BbshDetailForm();
    bbshDetailForm.setBbshId(bbsh.getBbshId());
    bbshDetailForm.setBhTitle(bbsh.getBhTitle());
    bbshDetailForm.setBhContent(bbsh.getBhContent());
    bbshDetailForm.setBhHit(bbsh.getBhHit());
//    detailForm.setAuthor(BBSH.getAuthor());
//    detailForm.setCDate(BBSH.getCDate());
//    detailForm.setUDate(BBSH.getUDate());

    log.info("bbshId={}",bbshId);
    //첨부파일조회
    List<UploadFile> imagedFiles = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.F010202,bbshId);
    bbshDetailForm.setImagedFiles(imagedFiles);

    model.addAttribute("bbshDetailForm", bbshDetailForm);
    return "board_review/review_detailForm";
  }

//   수정양식
  @GetMapping("/{bbshId}/edit")
  public String updateForm(
          @PathVariable("bbshId") Long bbshId,
          Model model
  ){
    Optional<BBSH> findedNotice = bbshSVC.findById(bbshId);
    BBSH bbsh = findedNotice.orElseThrow();

    BbshUpdateForm bbshUpdateForm = new BbshUpdateForm();
    bbshUpdateForm.setBhTitle(bbsh.getBhTitle());
    bbshUpdateForm.setBhContent(bbsh.getBhContent());

    // 파일첨부조회
//    List<UploadFile> attachedFile = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.F010201, bbshId);
    List<UploadFile> imagedFiles = uploadFileSVC.findFilesByCodeWithRid(AttachFileType.F010202, bbshId);

//    updateForm.setAttachedFile(attachedFile.get(0));
    bbshUpdateForm.setImagedFiles(imagedFiles);

    model.addAttribute("bbshUpdateForm", bbshUpdateForm);
    return "board_review/review_updateForm";
  }

  // 수정
  @PostMapping("/{bbshId}/edit")
  public String update(
          @PathVariable("bbshId") Long bbshId,
          @Valid @ModelAttribute BbshUpdateForm bbshUpdateForm,
          BindingResult bindingResult,
          RedirectAttributes redirectAttributes
  ){
    // 데이터 검증
    if (bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "board_review/review_updateForm";
    }

    // 정상처리
    BBSH bbsh = new BBSH();
    bbsh.setBbshId(bbshId);
    bbsh.setBhTitle(bbshUpdateForm.getBhTitle());
    bbsh.setBhContent(bbshUpdateForm.getBhContent());

    // 파일첨부
//    UploadFile attachFile = uploadFileSVC.convert(updateForm.getAttachFile(),AttachFileType.F010201);
    List<UploadFile> imageFiles = uploadFileSVC.convert(bbshUpdateForm.getImageFiles(),AttachFileType.F010202);
//    if(attachFile != null) imageFiles.add(attachFile);

    bbshSVC.update(bbshId, bbsh, imageFiles);

    redirectAttributes.addAttribute("bbshId", bbshId);
    return "redirect:/bbsh/{bbshId}/updateForm";
  }

  // 삭제
  @GetMapping("/{bbshId}/del")
  public String deleteById(@PathVariable("bbshId") Long bbshId){

//    bbshSVC.delete(bbshId,AttachFileType.F010201);
    bbshSVC.delete(bbshId,AttachFileType.F010202);


    return "redirect:/bbsh";
  }

  //목록
  @GetMapping
  public String findAll(Model model){
    List<BBSH> bbshes = bbshSVC.findAll();

    List<BbshDetailForm> bbshDetailForms = new ArrayList<>();
    bbshes.stream().forEach(bbsh->{
      BbshDetailForm bbshDetailForm = new BbshDetailForm();
//      detailForm.setBhTitle(bbsh.getBhTitle());
//      detailForm.setBhTitle(bbsh.getBhHname());
      bbshDetailForm.setBhContent(bbsh.getBhContent());
      bbshDetailForm.setBhHit(bbsh.getBhHit());
//      detailForm.setUserNick(bbsh.getUserNick());
//      detailForm.setBhCdate(bbsh.getBhCdate());
    });

    model.addAttribute("bbshes", bbshes);
    if (bbshes.size() == 0) {
//      throw new BizException("등록된 상품정보가 없습니다");
    }
    return "board_review/review_main";
  }
}
