package com.project.zoopiter.domain.Hospital.svc;

import com.project.zoopiter.domain.Hospital.dao.HospitalDAO;
import com.project.zoopiter.domain.entity.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HospitalSVCImpl implements HospitalSVC {
  @Autowired
  private HospitalDAO hospitalDAO;

  @Override
  public List<Hospital> findAllHospitals() {
    return hospitalDAO.findAll();
  }
}