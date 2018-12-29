package com.itmuch.cloud.study.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class MovieClientController {
	
  @Value("${profile}")
  private String profile;

  @GetMapping("/profile")
  public String hellos() {
    return this.profile;
  }
}
