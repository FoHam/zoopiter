package com.project.zoopiter.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {

  private int hdId;
  private Integer hdCode;
  private String hdManage;
  private Date hdPerdate;
  private Integer hdStatuscode;
  private String hdStatusname;
  private Integer hdDetailcode;
  private String hdDetailname;
  private String hdTel;
  private String hdAddressGeneral;
  private String hdAddressRoad;
  private Integer hdAddressRoadnum;
  private String hdName;
  private String hdAditDate;
  private String hdAditGubun;
  private String hdAditResdate;
  private double hdLng;
  private double hdLat;

  public Hospital(String hdName, String hdAddressGeneral, String hdAddressRoad, Double hdLng, Double hdLat) {
    this.hdName = hdName;
    this.hdAddressGeneral = hdAddressGeneral;
    this.hdAddressRoad = hdAddressRoad;
    this.hdLng = hdLng;
    this.hdLat = hdLat;
  }
}
