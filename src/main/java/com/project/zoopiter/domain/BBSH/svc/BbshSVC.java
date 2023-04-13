package com.project.zoopiter.domain.BBSH.svc;

import com.project.zoopiter.domain.entity.BBSH;
import com.project.zoopiter.domain.entity.UploadFile;
import com.project.zoopiter.web.common.AttachFileType;

import java.util.List;
import java.util.Optional;

public interface BbshSVC {

  // 등록
  Long save(BBSH bbsh);
  Long save(BBSH bbsh, List<UploadFile> uploadFiles);

  // 조회
  Optional<BBSH> findById(Long bbshId);

  // 수정
  int update(Long bbshId, BBSH bbsh);
  int update(Long bbshId, BBSH bbsh, List<UploadFile> uploadFiles);

  // 삭제
  int delete(Long bbshId);
  int delete(Long bbshId, AttachFileType attachFileType);

  // 목록
  List<BBSH> findAll();

  //조회수증가
  int increaseHit(Long bbshId);
}
