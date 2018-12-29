package com.roncoo.eshop.cache.ha;

import com.roncoo.eshop.cache.ha.http.HttpClientUtils;

public class TimeoutTest {
	
	public static void main(String[] args) throws Exception {
		for(int i=0; i<50; i++) {
			HttpClientUtils.sendGetRequest("http://localhost:8010/user-user/admins");  
		}
	}
	
}
