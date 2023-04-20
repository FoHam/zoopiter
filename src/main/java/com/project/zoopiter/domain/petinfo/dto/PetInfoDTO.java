package com.project.zoopiter.domain.petinfo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PetInfoDTO {
  private Long petNum;           //  PET_NUM	NUMBER
  private String userID;        //  USER_ID	VARCHAR2(20 BYTE)
  private String petName;       //  PET_NAME	VARCHAR2(40 BYTE)
  private String petType;       //  PET_TYPE	VARCHAR2(20 BYTE)
  private String petGender;     //  PET_GENDER	CHAR(1 BYTE)
  private LocalDate petBirth;   //  PET_BIRTH	DATE
  private String petYn;         //  PET_YN	CHAR(1 BYTE)
  private LocalDate petDate;    //  PET_DATE	DATE
  private String petVac;        //  PET_VAC	VARCHAR2(15 BYTE)
  private String petInfo;       //  PET_INFO	VARCHAR2(60 BYTE)
  private Long uploadfileId;    //  UPLOADFILE_ID

}
