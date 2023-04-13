package com.project.zoopiter.web.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FindPWForm {
  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String userId;
}
