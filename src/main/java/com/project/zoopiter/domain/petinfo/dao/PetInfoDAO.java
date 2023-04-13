package com.project.zoopiter.domain.petinfo.dao;

import com.project.zoopiter.domain.entity.PetInfo;

import java.util.List;
import java.util.Optional;

public interface PetInfoDAO {
  /**
   * 등록
   *
   * @param petInfo
   * @return
   */
  String saveInfo(PetInfo petInfo);

  /** 조회
   *
   * @param memberId
   * @return
   */
  Optional<PetInfo> findInfo(Long memberId);

  /**수정
   *
   * @param PetNum
   * @param petInfo
   * @return
   */
  int updateInfo (Long PetNum, PetInfo petInfo);

  /** 삭제
   *
   * @param PetNum
   * @return
   */
  int deleteInfo(Long PetNum);

  /** 목록
   *
   * @return
   */
  List<PetInfo> findAll(String userId);

  // 회원유무

}
