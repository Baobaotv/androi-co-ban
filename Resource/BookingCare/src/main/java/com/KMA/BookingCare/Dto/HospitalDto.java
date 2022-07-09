package com.KMA.BookingCare.Dto;

import javax.persistence.Column;

public class HospitalDto {
	
	private Long id;
	private String name;
	private String location;
	private String description;
	private String img;
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public HospitalDto() {
	}

	public HospitalDto(Long id, String name, String location, String description, String img) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.description = description;
		this.img = img;
	}
}
