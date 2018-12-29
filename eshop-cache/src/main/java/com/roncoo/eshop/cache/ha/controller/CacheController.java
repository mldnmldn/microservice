package com.roncoo.eshop.cache.ha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import com.roncoo.eshop.cache.ha.http.HttpClientUtils;
import com.roncoo.eshop.cache.ha.hystrix.command.GetBrandNameCommand;
import com.roncoo.eshop.cache.ha.hystrix.command.GetCityNameCommand;
import com.roncoo.eshop.cache.ha.hystrix.command.GetProductInfoCommand;
import com.roncoo.eshop.cache.ha.hystrix.command.GetProductInfosCommand;
import com.roncoo.eshop.cache.ha.model.ProductInfo;

import rx.Observable;
import rx.Observer;

/**
 * 缓存服务的接口
 * @author Administrator
 *
 */
@Controller
public class CacheController {

	@RequestMapping("/change/product")
	@ResponseBody
	public String changeProduct(Long productId) {
		String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
		String response = HttpClientUtils.sendGetRequest(url);
		System.out.println(response);  
		
		return "success";
	}
	
	
	@RequestMapping("/getProductInfo")
	@ResponseBody
	public ProductInfo getProductInfo(Long productId) {
		HystrixCommand<ProductInfo> getProductInfoCommand = new GetProductInfoCommand(productId);
		ProductInfo productInfo = getProductInfoCommand.execute();
		
		GetCityNameCommand getCityNameCommand = new GetCityNameCommand(productInfo.getCityId());
		String cityName = getCityNameCommand.execute();
		productInfo.setCityName(cityName);
		
		Long brandId = productInfo.getBrandId();
		GetBrandNameCommand getBrandNameCommand = new GetBrandNameCommand(brandId);
		String brandName = getBrandNameCommand.execute();
		productInfo.setBrandName(brandName);
		
		System.out.println(productInfo);  
		return productInfo;
	}
	
	
	@RequestMapping("/getProductInfos")
	@ResponseBody
	public String getProductInfos(String productIds) {
		HystrixObservableCommand<ProductInfo> getProductInfosCommand = 
				new GetProductInfosCommand(productIds.split(","));  
		Observable<ProductInfo> observable = getProductInfosCommand.observe();
		
		observable.subscribe(new Observer<ProductInfo>() {
			@Override
			public void onCompleted() {
				System.out.println("获取完了所有的商品数据");
			}
			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}
			@Override
			public void onNext(ProductInfo productInfo) {
				System.out.println(productInfo); 
			}
		});
		return "success";
	}
	
	@RequestMapping("/getProductInfoCache")
	@ResponseBody
	public String getProductInfoCache(String productIds) {
		
		for(String productId : productIds.split(",")) {
			GetProductInfoCommand getProductInfoCommand = new GetProductInfoCommand(
					Long.valueOf(productId)); 
			ProductInfo productInfo = getProductInfoCommand.execute();
			System.out.println(productInfo);
			//System.out.println(getProductInfoCommand.isResponseFromCache()); 
		}
		return "success";
	}
}
