package com.br.config;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class CORSFilter implements ContainerResponseFilter {
	@Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {

		response.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
		response.getHttpHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization,x-access-token");
		response.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
		response.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		
//		response.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
//	        response.getHttpHeaders().add("Access-Control-Allow-Headers", "CSRF-Token, X-Requested-By, Authorization, Content-Type");
//	        response.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
//	        response.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");

		return response;
	}
	
//	@Override
//	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
//
//		response.getHttpHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000/");
//		response.getHttpHeaders().add("Access-Control-Allow-Headers",
//				"origin, content-type, accept, authorization,x-access-token");
//		response.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
//		response.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
//
//		return response;
//	}
	
}