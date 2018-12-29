package com.roncoo.eshop.cache.ha.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.roncoo.eshop.cache.ha.cache.local.LocationCache;

public class GetCityNameCommand extends HystrixCommand<String> {

	private Long cityId;
	
	/**
	 * 基于信号量线程隔离在技术
	 * @param cityId
	 */
	public GetCityNameCommand(Long cityId) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetCityNameGroup"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetCityNameCommand"))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetCityNamePool"))
		        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
		        		.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)
		        		.withExecutionIsolationSemaphoreMaxConcurrentRequests(10)));
		this.cityId = cityId;
	}

	@Override
	protected String run() throws Exception {
		// TODO Auto-generated method stub
		return LocationCache.getCityName(cityId);
	}

}
