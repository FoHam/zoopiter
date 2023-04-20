package com.project.zoopiter.web;

import com.project.zoopiter.domain.Hospital.svc.HospitalSVC;
import com.project.zoopiter.domain.entity.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class HospitalController {

  @Autowired
  private HospitalSVC hospitalSVC;


  @GetMapping("/getHospitals")
  public List<Hospital> getAllHospitals() {
    System.out.println("Request received at /api/getHospitals");
    List<Hospital> hospitals = hospitalSVC.findAllHospitals();
    System.out.println("Returning " + hospitals.size() + " hospitals");
    return hospitals;
  }

}
