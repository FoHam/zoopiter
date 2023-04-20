package com.project.zoopiter.web;


import com.project.zoopiter.domain.Hospital.svc.HospitalInfoSVC;
import com.project.zoopiter.domain.entity.HospitalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HospitalInfoController {
  @Autowired
  private HospitalInfoSVC hospitalInfoSVC;

  @GetMapping("/getHospitalinfo")
  public List<HospitalInfo> getAllHospitalinfo() {
    System.out.println("Request received at /api/getHospitalinfo");
    List<HospitalInfo> hospitalInfoAll = hospitalInfoSVC.findAllHospitalInfo();
    System.out.println("Returning " + hospitalInfoAll.size() + " hospitalInfoAll");
    return hospitalInfoAll;
  }
}
