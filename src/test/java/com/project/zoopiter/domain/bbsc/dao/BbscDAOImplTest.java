package com.project.zoopiter.domain.bbsc.dao;

import com.project.zoopiter.domain.entity.Bbsc;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class BbscDAOImplTest {

  @Autowired
  private BbscDAO bbscDAO;

  @Test
  @DisplayName("글작성")
  void saveWrite() {
    Bbsc bbsc = new Bbsc();

    bbsc.setBcGubun("B0102");
    bbsc.setBcTitle("테스트제목2");
    bbsc.setBcContent("테스트본문2");
    bbsc.setUserNick("회원2");
    bbsc.setPetType("기타");
    bbsc.setBcPublic("Y");

    Long saveWrite = bbscDAO.saveWrite(bbsc);
    log.info("saveWrite={}",saveWrite);
//    Assertions.assertThat(saveWrite).isEqualTo(24);
  }

  @Test
  @DisplayName("글목록")
  void findAll() {
    List<Bbsc> list = bbscDAO.findAll();
    Assertions.assertThat(list.get(0).getBcTitle()).isEqualTo("테스트제목2");

    for(Bbsc bbsc : list){
      log.info(bbsc.toString());
    }
  }

  @Test
  @DisplayName("글검색-펫태그")
  void findByPetType() {
    List<Bbsc> findByPet = bbscDAO.findByPetType("소동물");
    Assertions.assertThat(findByPet.get(0).getPetType()).isEqualTo("소동물");

    log.info("findByPet={}",findByPet);
  }

  @Test
  @DisplayName("글 단건 조회")
  void findByBbscId() {
    Bbsc findById = bbscDAO.findByBbscId(2L);
    Assertions.assertThat(findById.getBbscId()).isEqualTo(2L);
    log.info("findByBbscId={}",findById);
  }

  @Test
  @DisplayName("글 단건 삭제")
  void deleteByBbscId() {
    int cntOfDelete = bbscDAO.deleteByBbscId(1L);
    Assertions.assertThat(cntOfDelete).isEqualTo(1);
  }

  @Test
  @DisplayName("글 수정")
  void updateByBbscId() {
    Bbsc bbsc = new Bbsc();

    bbsc.setBcTitle("테스트제목2_수정");
    bbsc.setBcContent("테스트본문2_수정");
    bbsc.setPetType("소동물");
    bbsc.setBcPublic("Y");

    bbscDAO.updateByBbscId(2L, bbsc);

    String bcTitle = bbsc.getBcTitle();
    Assertions.assertThat(bcTitle).isEqualTo("테스트제목2_수정");
  }

  @Test
  @DisplayName("조회수 증가")
  void increaseHitCount() {
    int hitResultCnt = bbscDAO.increaseHitCount(2L);
    Assertions.assertThat(hitResultCnt).isEqualTo(1);
  }
}