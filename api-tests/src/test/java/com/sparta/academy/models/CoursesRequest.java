package com.sparta.academy.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CoursesRequest{

	@JsonProperty("CoursesRequest")
	private List<CoursesRequestItem> coursesRequest;

	public List<CoursesRequestItem> getCoursesRequest(){
		return coursesRequest;
	}
}