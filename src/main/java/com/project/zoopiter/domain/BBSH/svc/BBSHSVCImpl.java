package com.project.zoopiter.domain.BBSH.svc;

import com.project.zoopiter.domain.BBSH.dao.BBSHDAO;
import com.project.zoopiter.domain.BBSH.dao.BBSHFilter;
import com.project.zoopiter.domain.common.file.svc.UploadFileSVC;
import com.project.zoopiter.domain.entity.BBSH;
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
public class BBSHSVCImpl implements BBSHSVC {

//  @Autowired
//  private EntityManager entityManager;

  private final BBSHDAO BbshDAO;
  private final UploadFileSVC uploadFileSVC;

  @Override
  public Long save(BBSH bbsh) {
    return BbshDAO.save(bbsh);
  }

  @Transactional
  @Override
  public Long save(BBSH bbsh, List<UploadFile> uploadFiles) {
    Long bbshId = save(bbsh);
    if(uploadFiles.size() > 0) {
      uploadFiles.stream().forEach(file-> file.setRid(bbshId));
      uploadFileSVC.addFiles(uploadFiles);
    }
    return bbshId;
  }

  @Override
  public Optional<BBSH> findById(Long bbshId) {
    Optional<BBSH> bbsh = BbshDAO.findById(bbshId);
    BbshDAO.updateHit(bbshId);
    return bbsh;
  }

  @Override
  public int update(Long bbshId, BBSH bbsh) {
    return BbshDAO.update(bbshId, bbsh);
  }

  @Override
  public int update(Long bbshId, BBSH bbsh, List<UploadFile> uploadFiles) {
    int cntOfupdate = BbshDAO.update(bbshId, bbsh);
    if(uploadFiles.size() > 0) {
      uploadFiles.stream().forEach(file->file.setRid(bbshId));
      uploadFileSVC.addFiles(uploadFiles);
    }
    return cntOfupdate;
  }

  @Override
  public int delete(Long bbshId) {
    return BbshDAO.delete(bbshId);
  }

  @Override
  public int delete(Long bbshId, AttachFileType attachFileType) {
    // 1) 게시글 삭제
    int cnt = BbshDAO.delete(bbshId);

    //2) 물리파일 삭제
    List<UploadFile> uploadFiles = uploadFileSVC.findFilesByCodeWithRid(attachFileType,bbshId);
    List<String> files = uploadFiles.stream().map(file->file.getStore_filename()).collect(Collectors.toList());
    uploadFileSVC.deleteFiles(attachFileType, files);

    //3) 메타정보삭제
    uploadFileSVC.deleteFileByCodeWithRid(attachFileType,bbshId);
    return cnt;
  }

  @Override
  public List<BBSH> findAll() {
    return BbshDAO.findAll();
  }

  @Override
  public List<BBSH> findAllPaging(int startRec, int endRec) {
    return BbshDAO.findAllPaging(startRec, endRec);
  }

  @Override
  public List<BBSH> findAll(BBSHFilter filterCondition, int startRec, int endRec) {
    return BbshDAO.findAll(filterCondition,startRec,endRec);
  }

  /**
   * 검색
   *
   * @param filterCondition 펫태그(강아지,고양이,소동물,기타)
   * @return
   */
  @Override
  public List<BBSH> findByPetType(BBSHFilter filterCondition) {
    return BbshDAO.findByPetType(filterCondition);
  }

  /**
   * 필터 검색
   *
   * @param filterCondition 조회수, 최신순, 좋아요
   * @return
   */
  @Override
  public List<BBSH> findByFilter(BBSHFilter filterCondition) {
    return BbshDAO.findByFilter(filterCondition);
  }

  @Override
  public int increaseHit(Long bbshId) { return BbshDAO.updateHit(bbshId); }

  //전체건수
  @Override
  public int totalCount() {
    return BbshDAO.totalCount();
  }

//  @Override
//  public int totalCount(String bcategory) {
//    return BbshDAO.totalCount(bcategory);
//  }

  @Override
  public int totalCount(BBSHFilter filterCondition) {
    return BbshDAO.totalCount(filterCondition);
  }
}


