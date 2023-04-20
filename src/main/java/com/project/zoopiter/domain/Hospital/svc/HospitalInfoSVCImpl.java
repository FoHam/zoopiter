package com.project.zoopiter.domain.Hospital.svc;

import com.project.zoopiter.domain.Hospital.dao.HospitalInfoDAO;
import com.project.zoopiter.domain.entity.HospitalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalInfoSVCImpl implements HospitalInfoSVC {
  @Autowired
  private HospitalInfoDAO hospitalInfoDAO;

  @Override
  public List<HospitalInfo> findAllHospitalInfo() {
    return hospitalInfoDAO.findAllInfo();
  }
}
