package com.project.zoopiter.domain.bbsc.svc;

import com.project.zoopiter.domain.bbsc.dao.BbscDAO;
import com.project.zoopiter.domain.bbsc.dao.BbscFilterCondition;
import com.project.zoopiter.domain.common.file.svc.UploadFileSVC;
import com.project.zoopiter.domain.entity.Bbsc;
import com.project.zoopiter.domain.entity.UploadFile;
import com.project.zoopiter.web.common.AttachFileType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BbscSVCImpl implements BbscSVC{

  private final BbscDAO bbscDAO;
  private final UploadFileSVC uploadFileSVC;

  /**
   * 등록
   *
   * @param bbsc
   * @return
   */
  @Override
  public Long save(Bbsc bbsc) {
    return bbscDAO.save(bbsc);
  }

  /**
   * 등록(업로드 파일)
   *
   * @param bbsc
   * @param uploadFiles
   * @return
   */
  @Transactional
  @Override
  public Long save(Bbsc bbsc, List<UploadFile> uploadFiles) {
    Long bbscId = save(bbsc);
    if(uploadFiles.size()>0){
      uploadFiles.stream().forEach(file -> file.setRid(bbscId));
      uploadFileSVC.addFiles(uploadFiles);
    }

    return bbscId;
  }

  /**
   * 조회
   *
   * @param bbscId
   * @return
   */
  @Override
  public Optional<Bbsc> findById(Long bbscId) {
    Optional<Bbsc> bbsc = bbscDAO.findByBbscId(bbscId);
    bbscDAO.increaseHitCount(bbscId);
    return bbsc;
  }

  /**
   * 수정
   *
   * @param bbscId
   * @param bbsc
   * @return
   */
  @Override
  public int update(Long bbscId, Bbsc bbsc) {
    return bbscDAO.updateByBbscId(bbscId,bbsc);
  }

  /**
   * 수정(업로드 파일)
   *
   * @param bbscId
   * @param bbsc
   * @param uploadFiles
   * @return
   */
  @Override
  public int update(Long bbscId, Bbsc bbsc, List<UploadFile> uploadFiles) {
    int cntOfupdate = bbscDAO.updateByBbscId(bbscId, bbsc);
    if(uploadFiles.size()>0){
      uploadFiles.stream().forEach(file -> file.setRid(bbscId));
      uploadFileSVC.addFiles(uploadFiles);
    }
    return cntOfupdate;
  }

  /**
   * 삭제
   *
   * @param bbscId
   * @return
   */
  @Override
  public int delete(Long bbscId) {
    return bbscDAO.deleteByBbscId(bbscId);
  }

  /**
   * 삭제(업로드 파일)
   *
   * @param bbscId
   * @param attachFileType
   * @return
   */
  @Override
  public int delete(Long bbscId, AttachFileType attachFileType) {
    // 1) 게시글 삭제
    int cntOfDelete = bbscDAO.deleteByBbscId(bbscId);

    // 2) 물리파일 삭제
    List<UploadFile> uploadFiles = uploadFileSVC.findFilesByCodeWithRid(attachFileType, bbscId);
    List<String> files = uploadFiles.stream().map(file -> file.getStore_filename()).collect(Collectors.toList());
    uploadFileSVC.deleteFiles(attachFileType, files);

    // 3) 메타정보 삭제
    uploadFileSVC.deleteFileByCodeWithRid(attachFileType, bbscId);

    return cntOfDelete;
  }

  /**
   * 목록
   *
   * @return
   */
  @Override
  public List<Bbsc> findAll() {
    return bbscDAO.findAll();
  }

  @Override
  public List<Bbsc> findAll(int startRec, int endRec) {
    return bbscDAO.findAll(startRec,endRec);
  }

  @Override
  public List<Bbsc> findAll(BbscFilterCondition filterCondition, int startRec, int endRec) {
    return bbscDAO.findAll(filterCondition,startRec,endRec);
  }

  /**
   * 조회수 증가
   *
   * @param bbscId
   * @return
   */
  @Override
  public int increaseHit(Long bbscId) {
    return bbscDAO.increaseHitCount(bbscId);
  }

  /**
   * 검색
   *
   * @param filterCondition 펫태그(강아지,고양이,소동물,기타)
   * @return
   */
  @Override
  public List<Bbsc> findByPetType(BbscFilterCondition filterCondition) {
    return bbscDAO.findByPetType(filterCondition);
  }

  /**
   * 필터 검색
   *
   * @param filterCondition 조회수, 최신순, 좋아요
   * @return
   */
  @Override
  public List<Bbsc> findByFilter(BbscFilterCondition filterCondition) {
    return bbscDAO.findByFilter(filterCondition);
  }

  /**
   * 전체건수
   *
   * @return 게시글 전체건수
   */
  @Override
  public int totalCount() {
    return bbscDAO.totalCount();
  }

  @Override
  public int totalCount(BbscFilterCondition filterCondition) {
    return bbscDAO.totalCount(filterCondition);
  }
}
