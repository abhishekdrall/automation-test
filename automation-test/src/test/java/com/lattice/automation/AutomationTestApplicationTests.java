package com.lattice.automation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AutomationTestApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	@Test
	void contextLoads() {
		Map<String,Object> map=new HashMap<>();
		map.put("data","Shiv");
		map.put("secretKey","Mohan");
		HttpHeaders httpHeaders=new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Map<String,Object>> httpEntity=new HttpEntity<Map<String,Object>>(map,httpHeaders);
		var response= restTemplate.exchange("http://134.209.154.238:8090/trf/security/encrypt/raw/data", HttpMethod.POST,httpEntity,String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

}
