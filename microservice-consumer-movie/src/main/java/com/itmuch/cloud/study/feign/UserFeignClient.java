package com.itmuch.cloud.study.feign;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itmuch.cloud.study.user.entity.Member;
@FeignClient(name = "microservice-provider-user")
public interface UserFeignClient {
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
	public Member findById(@PathVariable("id") String id);
}
