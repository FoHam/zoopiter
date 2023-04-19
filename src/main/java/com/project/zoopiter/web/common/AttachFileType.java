package com.project.zoopiter.web.common;

public enum AttachFileType {

//  F010201("병원후기일반"),
  F010202("병원후기이미지"),
  F0102("커뮤니티"),
  F0103("회원프로필");
  private String description;

  AttachFileType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
