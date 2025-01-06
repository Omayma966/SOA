package com.omicrone.response;

import com.omicrone.entity.Student;
import java.util.ArrayList;
import java.util.List;
public class StudentResponse {

	private long id;

	private String firstName;

	private String lastName;

	private String email;
	private String street;
	private String city;

	private AddressResponse addressResponse;

	public StudentResponse(Student student) {
		this.id = student.getId();
		this.firstName = student.getFirstName();
		this.lastName = student.getLastName();
		this.email = student.getEmail();
	}

	public StudentResponse(Student student, AddressResponse addressResponse) {
		this.id = student.getId();
		this.firstName = student.getFirstName();
		this.lastName = student.getLastName();
		this.email = student.getEmail();
		this.street = addressResponse.getStreet();
		this.city = addressResponse.getCity();
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AddressResponse getAddressResponse() {
		return addressResponse;
	}

	public void setAddressResponse(AddressResponse addressResponse) {
		this.addressResponse = addressResponse;
	}
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}



	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public static List<StudentResponse> toArrayList(List<Student> all) {
		List<StudentResponse> list = new ArrayList<>();
		for (Student student : all) {
			list.add(new StudentResponse(student));
		}
		return list;
	}
}
