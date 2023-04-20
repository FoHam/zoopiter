package com.project.zoopiter.domain.petinfo.svc;

import com.project.zoopiter.domain.common.file.svc.UploadFileSVC;
import com.project.zoopiter.domain.entity.PetInfo;
import com.project.zoopiter.domain.entity.UploadFile;
import com.project.zoopiter.domain.petinfo.dao.PetInfoDAO;
import com.project.zoopiter.domain.petinfo.dto.PetInfoDTO;
import com.project.zoopiter.web.common.AttachFileType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetInfoSVCImpl implements PetInfoSVC{
  private final PetInfoDAO petInfoDAO;
  private final UploadFileSVC uploadFileSVC;

  @Override
  public String saveInfo(PetInfo petInfo) {
    return petInfoDAO.saveInfo(petInfo);
  }

  /**
   * 등록 (업로드파일)
   *
   * @param petInfo
   * @param uploadFiles
   * @return
   */
  @Transactional
  @Override
  public String saveInfo(PetInfo petInfo, List<UploadFile> uploadFiles) {
    String petNum = saveInfo(petInfo);
    if(uploadFiles.size()>0){
      uploadFiles.stream().forEach(uploadFile -> uploadFile.setRid(Long.valueOf(petNum)));
      uploadFileSVC.addFiles(uploadFiles);
    }
    return petNum;
  }

  @Override
  public int updateInfo(Long petNum, PetInfo petInfo) {
    return petInfoDAO.updateInfo(petNum, petInfo);
  }

  /**
   * 수정 (업로드 파일)
   * @param petNum
   * @param petInfo
   * @param uploadFiles
   * @return
   */
  @Override
  public int updateInfo(Long petNum, PetInfo petInfo, List<UploadFile> uploadFiles) {
    int cntOfUpdate = petInfoDAO.updateInfo(petNum,petInfo);
    if(uploadFiles.size()>0){
      uploadFiles.stream().forEach(uploadFile -> uploadFile.setRid(petNum));
      uploadFileSVC.addFiles(uploadFiles);
    }
    return cntOfUpdate;
  }

  @Override
  public Optional<PetInfo> findInfo(Long memberId) {
    return petInfoDAO.findInfo(memberId);
  }

  @Override
  public int deleteInfo(Long petNum) {
    return petInfoDAO.deleteInfo(petNum);
  }

  /**
   * 삭제 (업로드 파일)
   * @param petNum
   * @param attachFileType
   * @return
   */
  @Override
  public int deleteInfo(Long petNum, AttachFileType attachFileType) {
    // 펫정보삭제
    int cntOfDelete = petInfoDAO.deleteInfo(petNum);

    // 물리파일삭제
    List<UploadFile> uploadFiles = uploadFileSVC.findFilesByCodeWithRid(attachFileType, petNum);
    List<String> files = uploadFiles.stream().map(uploadFile -> uploadFile.getStore_filename()).collect(Collectors.toList());
    uploadFileSVC.deleteFileByCodeWithRid(attachFileType,petNum);

    // 메타정보 삭제
    return cntOfDelete;
  }

  @Override
  public List<PetInfo> findAll(String userId) {
    return petInfoDAO.findAll(userId);
  }

  /**
   * 반려동물 이미지 번호 가져오기
   *
   * @param userId 회원 아이디
   * @return 이미지 번호들
   */
  @Override
  public List<PetInfoDTO> findPetByuserId(String userId) {
    return petInfoDAO.findPetByuserId(userId);
  }
}
