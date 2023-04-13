package com.project.zoopiter.domain.petinfo.svc;

import com.project.zoopiter.domain.entity.PetInfo;
import com.project.zoopiter.domain.petinfo.dao.PetInfoDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetInfoSVCImpl implements PetInfoSVC{
  private final PetInfoDAO petInfoDAO;

  @Override
  public String saveInfo(PetInfo petInfo) {
    return petInfoDAO.saveInfo(petInfo);
  }

  @Override
  public int updateInfo(Long petNum, PetInfo petInfo) {
    return petInfoDAO.updateInfo(petNum, petInfo);
  }

  @Override
  public Optional<PetInfo> findInfo(Long memberId) {
    return petInfoDAO.findInfo(memberId);
  }

  @Override
  public int deleteInfo(Long petNum) {
    return petInfoDAO.deleteInfo(petNum);
  }

  @Override
  public List<PetInfo> findAll(String userId) {
    return petInfoDAO.findAll(userId);
  }
}
