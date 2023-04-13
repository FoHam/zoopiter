package com.project.zoopiter.domain.common.file.dao;

import com.project.zoopiter.domain.entity.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@Slf4j
@SpringBootTest
class UploadFileDAOImplTest {
  @Autowired
  private UploadFileDAO uploadFileDAO;

  @Test
  @DisplayName("단건첨부")
  void addFile() {
    UploadFile uploadFile = new UploadFile();
    uploadFile.setCode("F0101");
    uploadFile.setRid(10L);  // 참조번호
    uploadFile.setStore_filename(UUID.randomUUID()+".png");
    uploadFile.setUpload_filename("배경이미지.png");
    uploadFile.setFsize("100");
    uploadFile.setFtype("image/png");
    Long fid = uploadFileDAO.addFile(uploadFile);

    Assertions.assertThat(fid).isGreaterThan(0L); //assertj
  }

  @Test
  @DisplayName("여러건첨부")
  void addFiles() {
  }
}