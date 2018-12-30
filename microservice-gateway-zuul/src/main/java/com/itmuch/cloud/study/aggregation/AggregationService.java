package com.itmuch.cloud.study.aggregation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.itmuch.cloud.study.entity.Member;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import rx.Observable;

@Service
public class AggregationService {

	@Autowired
	private RestTemplate restTemplate;
	
	 @HystrixCommand(fallbackMethod = "fallback")
	  public Observable<Member> getUserById(String id) {
	    // 创建一个被观察者
	    return Observable.create(observer -> {
	      // 请求用户微服务的/{id}端点
	    	Member user = restTemplate.getForObject("http://microservice-provider-user/{id}", Member.class, id);
	      observer.onNext(user);
	      observer.onCompleted();
	    });
	  }

	  @HystrixCommand(fallbackMethod = "fallback")
	  public Observable<Member> getMovieUserByUserId(String id) {
	    return Observable.create(observer -> {
	      // 请求电影微服务的/user/{id}端点
	    	Member movieUser = restTemplate.getForObject("http://microservice-consumer-movie/user/{id}", Member.class, id);
	      observer.onNext(movieUser);
	      observer.onCompleted();
	    });
	  }
	
	 public Member fallback(String id) {
		 Member user = new Member();
		    user.setMid("mid");;
		    return user;
		  }
}
