package com.project.zoopiter.domain.BBSH.svc;

import com.project.zoopiter.domain.BBSH.dao.BbshDAO;
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
public class BbshSVCImpl implements BbshSVC {

//  @Autowired
//  private EntityManager entityManager;

  private final BbshDAO bbshDAO;
  private final UploadFileSVC uploadFileSVC;

  @Override
  public Long save(BBSH bbsh) {
    return bbshDAO.save(bbsh);
  }

  @Transactional
  @Override
  public Long save(BBSH bbsh, List<UploadFile> uploadFiles) {
//    entityManager.persist(bbsh);
//    Long bbshId = bbsh.getBbshId();
//    if(uploadFiles.size() > 0) {
//      uploadFiles.stream().forEach(file -> file.setRid(bbshId));
//      uploadFileSVC.addFiles(uploadFiles);
//    }
//    return bbshId;
    Long bbshId = save(bbsh);
    if(uploadFiles.size() > 0) {
      uploadFiles.stream().forEach(file-> file.setRid(bbshId));
      uploadFileSVC.addFiles(uploadFiles);
    }
    return bbshId;
  }

  @Override
  public Optional<BBSH> findById(Long bbshId) {
    Optional<BBSH> bbsh = bbshDAO.findById(bbshId);
    bbshDAO.updateHit(bbshId);
    return bbsh;
  }

  @Override
  public int update(Long bbshId, BBSH bbsh) {
    return bbshDAO.update(bbshId, bbsh);
  }

  @Override
  public int update(Long bbshId, BBSH bbsh, List<UploadFile> uploadFiles) {
    bbshDAO.update(bbshId,bbsh);
    if(uploadFiles.size() > 0) {
      uploadFiles.stream().forEach(file->file.setRid(bbshId));
      uploadFileSVC.addFiles(uploadFiles);
    }
    return 0;
  }

  @Override
  public int delete(Long bbshId) {
    return bbshDAO.delete(bbshId);
  }

  @Override
  public int delete(Long bbshId, AttachFileType attachFileType) {
    //1) 상품정보 삭제
    int cnt = bbshDAO.delete(bbshId);

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
    return bbshDAO.findAll();
  }

  @Override
  public int increaseHit(Long bbshId) { return bbshDAO.updateHit(bbshId); }
}

