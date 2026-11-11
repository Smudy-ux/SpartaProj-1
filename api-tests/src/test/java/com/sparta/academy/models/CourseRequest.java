package com.sparta.academy.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseRequest {

	@JsonProperty("id")
	private final int id;

	@JsonProperty("name")
	private final String name;

	@JsonProperty("stream")
	private final StreamRequest stream;

	@JsonProperty("streamId")
	private final int streamId;

	@JsonProperty("trainer")
	private final String trainer;

	@JsonProperty("startDate")
	private final String startDate;

	@JsonProperty("endDate")
	private final String endDate;

	public CourseRequest(int id, String name, StreamRequest stream, String trainer, String startDate, String endDate) {
		this.id = id;
		this.name = name;
		this.stream = stream;
		this.streamId = stream.getId();
		this.trainer = trainer;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public static CourseRequest tech300() {
		return new CourseRequest(1, "TECH 300", new StreamRequest(1, "C# Dev"),
				"Phil Windridge", "2023-03-01T00:00:00", "2023-05-01T00:00:00");
	}

	public static CourseRequest tech301() {
		return new CourseRequest(2, "TECH 301", new StreamRequest(2, "Java Dev"),
				"Catherine French", "2023-04-01T00:00:00", "2023-06-01T00:00:00");
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public StreamRequest getStream() {
		return stream;
	}

	public int getStreamId() {
		return streamId;
	}

	public String getTrainer() {
		return trainer;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}
}
