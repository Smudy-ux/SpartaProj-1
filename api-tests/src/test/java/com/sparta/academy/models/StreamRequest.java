package com.sparta.academy.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StreamRequest {

	@JsonProperty("id")
	private final int id;

	@JsonProperty("name")
	private final String name;

	public StreamRequest(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
