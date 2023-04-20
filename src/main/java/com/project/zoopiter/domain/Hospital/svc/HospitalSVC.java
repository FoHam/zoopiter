package com.project.zoopiter.domain.Hospital.svc;

import com.project.zoopiter.domain.entity.Hospital;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HospitalSVC {

  List<Hospital> findAllHospitals();
}
