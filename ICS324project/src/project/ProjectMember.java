package project;

import guest.Student;

public class ProjectMember {
	public Student student;
	public String role;
	
	public ProjectMember(Student student, String role) {
		super();
		this.student = student;
		this.role = role;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
