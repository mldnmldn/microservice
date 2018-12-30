package com.itmuch.cloud.study.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.collect.Maps;
import com.itmuch.cloud.study.aggregation.AggregationService;
import com.itmuch.cloud.study.entity.Member;

import rx.Observable;
import rx.Observer;

@RestController
public class AggregationController {

	 public static final Logger LOGGER = LoggerFactory.getLogger(AggregationController.class);
	 
	 @Autowired
	 private AggregationService aggregationService;
	 
	 @GetMapping("/aggregate/{id}")
	 public DeferredResult<HashMap<String, Member>> aggregate(@PathVariable String id) {
	    Observable<HashMap<String, Member>> result = this.aggregateObservable(id);
	    return this.toDeferredResult(result);
	  }
	 
	 public Observable<HashMap<String, Member>> aggregateObservable(String id) {
		    // 合并两个或者多个Observables发射出的数据项，根据指定的函数变换它们
		    return Observable.zip(
		            this.aggregationService.getUserById(id),
		            this.aggregationService.getMovieUserByUserId(id),
		            (user, movieUser) -> {
		              HashMap<String, Member> map = Maps.newHashMap();
		              map.put("user", user);
		              map.put("movieUser", movieUser);
		              return map;
		            }
		    );
		  }

		  public DeferredResult<HashMap<String,Member>> toDeferredResult(Observable<HashMap<String, Member>> details) {
		    DeferredResult<HashMap<String, Member>> result = new DeferredResult<>();
		    // 订阅
		    details.subscribe(new Observer<HashMap<String, Member>>() {
		      @Override
		      public void onCompleted() {
		        LOGGER.info("完成...");
		      }

		      @Override
		      public void onError(Throwable throwable) {
		        LOGGER.error("发生错误...", throwable);
		      }

		      @Override
		      public void onNext(HashMap<String, Member> movieDetails) {
		        result.setResult(movieDetails);
		      }
		    });
		    return result;
		  }
}
