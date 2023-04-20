package com.project.zoopiter.domain.Hospital.dao;

import com.project.zoopiter.domain.entity.Hospital;

import java.util.List;

public interface HospitalDAO {
  /** 병원정보 비교용
   *
   * @param
   * @return
   */
//  List<Hospital> getAllHospitals(     );

  List<Hospital> findAll();
}
