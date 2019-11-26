package com.fx_boot.poc.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class PocHelper {

	public String getDetailsById(String pId) {
		System.out.println("But Resp: " + pId);
		RestClient restClient = new RestClient();
		String printServiceUrl = "http://localhost:6060/demo/getDetails/" + pId;
		String gpsResp = (String) restClient.getForObjects(printServiceUrl, new ParameterizedTypeReference<String>() {
		});
		System.out.println("gpsResp : " + gpsResp);
		return pId;
	}
}
