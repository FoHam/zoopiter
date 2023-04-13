package com.project.zoopiter.domain.common.code;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CodeDAOImplTest {

  @Autowired
  private CodeDAO codeDAO;

  @Test
  void code() {
    List<Code> codeList = codeDAO.code("P01");
    log.info("codeList = {}",codeList);
  }

  @Test
  void codeAll() {
    List<CodeAll> codeAlls = codeDAO.codeAll();
    log.info("codeAlls = {}", codeAlls);
  }
}