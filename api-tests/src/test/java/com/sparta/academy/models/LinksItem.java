package com.sparta.academy.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LinksItem{

	@JsonProperty("method")
	private String method;

	@JsonProperty("rel")
	private String rel;

	@JsonProperty("href")
	private String href;

	public String getMethod(){
		return method;
	}

	public String getRel(){
		return rel;
	}

	public String getHref(){
		return href;
	}
}