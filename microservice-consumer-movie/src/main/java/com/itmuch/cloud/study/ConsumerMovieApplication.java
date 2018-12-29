package com.itmuch.cloud.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableCircuitBreaker
@EnableEurekaClient
public class ConsumerMovieApplication {
	
	 @Bean
	  @LoadBalanced
	  public RestTemplate restTemplate() {
		  SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new 
				  SimpleClientHttpRequestFactory();
		  simpleClientHttpRequestFactory.setConnectTimeout(5000);
		  simpleClientHttpRequestFactory.setReadTimeout(2000);
		  
		  return new RestTemplate(simpleClientHttpRequestFactory);
	  }

  public static void main(String[] args) {
    SpringApplication.run(ConsumerMovieApplication.class, args);
  }
}
