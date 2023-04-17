package com.project.zoopiter.web.form.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ModifyForm {
  private String userId;
  @NotBlank
  private String userNick;
//  @NotBlank
//  private String userEmail;
//  private byte[] userPhoto;
}
