package com.project.zoopiter.web.form.BBSH;

import com.project.zoopiter.domain.entity.UploadFile;
import lombok.Data;

import java.util.List;

@Data
public class BbshListForm {
  private Long bbshId;
  private String petType;
  private String bhContent;
  private String userNick;
  private String bhCdate;
  private String bhHname;
  private String bhUdate;
  private Long bhHit;
  private List<UploadFile> imagedFiles;
}
