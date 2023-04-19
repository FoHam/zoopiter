package com.project.zoopiter.domain.petinfo.svc;

import com.project.zoopiter.domain.entity.PetInfo;
import com.project.zoopiter.domain.entity.UploadFile;
import com.project.zoopiter.web.common.AttachFileType;

import java.util.List;
import java.util.Optional;

public interface PetInfoSVC {
  // 등록
  String saveInfo(PetInfo petInfo);

  // 등록 (업로드파일)
  String saveInfo(PetInfo petInfo, List<UploadFile> uploadFiles);

  // 조회
  Optional<PetInfo> findInfo(Long id);

  // 수정
  int updateInfo (Long petNum, PetInfo petInfo);

  // 수정 (업로드파일)
  int updateInfo(Long petNum , PetInfo petInfo, List<UploadFile> uploadFiles);

  // 삭제
  int deleteInfo(Long petNum);
  // 삭제 (업로드파일)
  int deleteInfo(Long petNum, AttachFileType attachFileType);

  // 목록
  List<PetInfo> findAll(String userId);
}
