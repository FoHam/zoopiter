package com.project.zoopiter.web.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginMember {
  private String userId;
  private String userNick;
  private String gubun;
}
