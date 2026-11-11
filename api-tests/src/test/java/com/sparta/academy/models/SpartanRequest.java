package com.sparta.academy.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpartanRequest {

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("university")
	private String university;

	@JsonProperty("degree")
	private String degree;

	@JsonProperty("course")
	private CourseRequest course;

	@JsonProperty("graduated")
	private boolean graduated;

	public SpartanRequest(String firstName, String lastName, String university, String degree,
						  CourseRequest course, boolean graduated) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.university = university;
		this.degree = degree;
		this.course = course;
		this.graduated = graduated;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public void setGraduated(boolean graduated) {
		this.graduated = graduated;
	}

	public CourseRequest getCourse() {
		return course;
	}

	public void setCourse(CourseRequest course) {
		this.course = course;
	}

	public boolean isGraduated() {
		return graduated;
	}
}
