package com.sparta.academy.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CoursesRequestItem{

	@JsonProperty("spartans")
	private List<String> spartans;

	@JsonProperty("stream")
	private String stream;

	@JsonProperty("endDate")
	private String endDate;

	@JsonProperty("trainer")
	private String trainer;

	@JsonProperty("name")
	private String name;

	@JsonProperty("links")
	private List<LinksItem> links;

	@JsonProperty("id")
	private int id;

	@JsonProperty("startDate")
	private String startDate;

	public List<String> getSpartans(){
		return spartans;
	}

	public String getStream(){
		return stream;
	}

	public String getEndDate(){
		return endDate;
	}

	public String getTrainer(){
		return trainer;
	}

	public String getName(){
		return name;
	}

	public List<LinksItem> getLinks(){
		return links;
	}

	public int getId(){
		return id;
	}

	public String getStartDate(){
		return startDate;
	}
}