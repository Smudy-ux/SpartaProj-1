package com.sparta.academy.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SpartanItem {

	@JsonProperty("id")
	private int id;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("university")
	private String university;

	@JsonProperty("degree")
	private String degree;

	@JsonProperty("course")
	private String course;

	@JsonProperty("stream")
	private String stream;

	@JsonProperty("graduated")
	private boolean graduated;

	@JsonProperty("links")
	private List<LinksItem> links;

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUniversity() {
		return university;
	}

	public String getDegree() {
		return degree;
	}

	public String getCourse() {
		return course;
	}

	public String getStream() {
		return stream;
	}

	public boolean isGraduated() {
		return graduated;
	}

	public List<LinksItem> getLinks() {
		return links;
	}
}
