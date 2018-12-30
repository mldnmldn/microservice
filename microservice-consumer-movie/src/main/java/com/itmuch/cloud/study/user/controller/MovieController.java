package com.itmuch.cloud.study.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.itmuch.cloud.study.feign.UserFeignClient;
import com.itmuch.cloud.study.hystrix.GetUserCommand;
import com.itmuch.cloud.study.user.entity.Member;
@RestController
public class MovieController {
	  
	
  @GetMapping("/user-user/{id}")
  public Member findByIdUser(@PathVariable String id) {
	  GetUserCommand getUserCommand = new GetUserCommand(id);
	  Member vo  = getUserCommand.execute();
    return vo;
  }
  
  
  @Autowired
  private UserFeignClient userFeignClient;

  @GetMapping("/user/{id}")
  public Member findById(@PathVariable String id) {
    return this.userFeignClient.findById(id);
  }
}
