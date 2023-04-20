package com.project.zoopiter.domain.Hospital.dao;

import com.project.zoopiter.domain.entity.HospitalInfo;

import java.util.List;

public interface HospitalInfoDAO {
  /** 병원정보 비교용
   *
   * @param
   * @return
   */

  List<HospitalInfo> findAllInfo();
}
