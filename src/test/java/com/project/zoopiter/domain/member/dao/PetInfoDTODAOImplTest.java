package com.project.zoopiter.domain.member.dao;

import com.project.zoopiter.domain.entity.PetInfo;
import com.project.zoopiter.domain.petinfo.dao.PetInfoDAO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PetInfoDTODAOImplTest {
  @Autowired
  private PetInfoDAO petInfoDAO;

  @Test
  @Order(1)
  @DisplayName("반려동물 등록")
  void saveInfo() {
    byte[] petImg = "test image".getBytes();
    PetInfo petInfo = new PetInfo();
    petInfo.setUserId("test1");
    petInfo.setPetImg(petImg);
    petInfo.setPetType("시츄");
    petInfo.setPetName("강아지1");
    petInfo.setPetBirth(LocalDate.parse("2022-01-01"));
    petInfo.setPetGender("M");
    petInfo.setPetYn("Y");
    petInfo.setPetDate(LocalDate.parse("2022-01-01"));
    petInfo.setPetVac("P0101");
    petInfo.setPetInfo("다이어트");

    String petNum = String.valueOf(petInfoDAO.saveInfo(petInfo));
    log.info("petNum={}", petNum);

    assertThat(petNum).isGreaterThan(String.valueOf(0L));
  }


  @Test
  @Order(2)
  @DisplayName("반려동물 정보 조회")
  void findInfo() {
    Long petNum = 1L;
    Optional<PetInfo> petInfo = petInfoDAO.findInfo(petNum);
    PetInfo findPetInfo = petInfo.orElseThrow();
    Assertions.assertThat(findPetInfo.getUserId()).isEqualTo("test1");
  }

  @Test
  @Order(3)
  @DisplayName("반려동물 정보 수정")
  void updateInfo() {
    byte[] petImg = "test image".getBytes();

    Long petNum = 1L;
    PetInfo petInfo = new PetInfo();
    petInfo.setUserId("test1");
    petInfo.setPetImg(petImg);
    petInfo.setPetType("시츄수정");
    petInfo.setPetBirth(LocalDate.parse("2022-01-01"));
    petInfo.setPetGender("M");
    petInfo.setPetName("강아지수정2");
    petInfo.setPetYn("Y");
    petInfo.setPetDate(LocalDate.parse("2022-01-01"));
    petInfo.setPetVac("P0104");
    petInfo.setPetInfo("다이어트해야함");
    int updateRowCount = petInfoDAO.updateInfo(petNum,petInfo);
    Optional<PetInfo> findPetInfo = petInfoDAO.findInfo(petNum);

    Assertions.assertThat(updateRowCount).isEqualTo(1);
    Assertions.assertThat(findPetInfo.get().getPetImg()).isEqualTo(petInfo.getPetImg());
    Assertions.assertThat(findPetInfo.get().getPetType()).isEqualTo(petInfo.getPetType());
    Assertions.assertThat(findPetInfo.get().getPetBirth()).isEqualTo(petInfo.getPetBirth());
    Assertions.assertThat(findPetInfo.get().getPetGender()).isEqualTo(petInfo.getPetGender());
    Assertions.assertThat(findPetInfo.get().getPetName()).isEqualTo(petInfo.getPetName());
    Assertions.assertThat(findPetInfo.get().getPetYn()).isEqualTo(petInfo.getPetYn());
    Assertions.assertThat(findPetInfo.get().getPetDate()).isEqualTo(petInfo.getPetDate());
    Assertions.assertThat(findPetInfo.get().getPetVac()).isEqualTo(petInfo.getPetVac());
    Assertions.assertThat(findPetInfo.get().getPetInfo()).isEqualTo(petInfo.getPetInfo());

  }

  @Test
  @Order(4)
  @DisplayName("반려동물 정보 삭제")
  void deleteInfo() {
    Long petNum = 1L;
    int deleteRowCount = petInfoDAO.deleteInfo(petNum);

    Optional<PetInfo> findPetInfo = petInfoDAO.findInfo(petNum);
    Assertions.assertThatThrownBy(()->findPetInfo.orElseThrow())
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  @Order(5)
  @DisplayName("반려동물 목록")
  void findAll() {
    String userId = "test1";
    List<PetInfo> list = petInfoDAO.findAll(userId);
    Assertions.assertThat(list.size()).isGreaterThan(0);
  }
}