package com.itmuch.cloud.study.hystrix;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.itmuch.cloud.study.http.HttpClientUtils;
import com.itmuch.cloud.study.user.entity.Member;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class GetUserCommand extends HystrixCommand<Member> {

	private String mid;
	
	public GetUserCommand(String mid) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserService"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetUserCommand"))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetUserPool"))
				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
						.withCoreSize(10)
						.withMaxQueueSize(12)) 
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withCircuitBreakerRequestVolumeThreshold(30)
						.withCircuitBreakerErrorThresholdPercentage(40)
						.withCircuitBreakerSleepWindowInMilliseconds(3000)
						.withExecutionTimeoutInMilliseconds(5000)
						.withFallbackIsolationSemaphoreMaxConcurrentRequests(30))  
				);  
		this.mid = mid;
	}

	@Override
	protected Member run() throws Exception {
		String response = HttpClientUtils.sendGetRequest("http://localhost:8000/" + mid);
		Member member = null;
		if(StringUtils.isNotEmpty(response)) {
			member = JSONObject.parseObject(response, Member.class);
		}
		return member;
	}
	
	@Override
	protected Member getFallback() {
		Member productInfo = new Member();
		productInfo.setName("降级商品");  
		return productInfo;
	}
}
