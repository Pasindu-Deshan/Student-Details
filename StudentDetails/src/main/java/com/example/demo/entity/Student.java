package com.example.demo.entity;

public class Student {
	private int studentNo;
	private String firstName;
	private String lastName;
	
	
	public Student(int studentNo, String firstName, String lastName) {
		super();
		this.studentNo = studentNo;
		this.firstName = firstName;
		this.lastName = lastName;
		
	}
	
	public int getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(int studentNo) {
		this.studentNo = studentNo;
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

	
}
