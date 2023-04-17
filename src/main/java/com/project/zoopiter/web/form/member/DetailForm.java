package com.project.zoopiter.web.form.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DetailForm {
  private String userId;
  @NotBlank
  private String userPw;
  @NotBlank
  private String userNick;
  @NotBlank
  private String userEmail;
//  private byte[] userPhoto;
}
