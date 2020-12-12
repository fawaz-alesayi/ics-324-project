package club;

import department.Department;

public class ClubInfo {
	private String name;
	private String address;
	private String phoneNumber;
	private Department department;
	private String description;
	private int status;
	
	public static final int ACTIVE = 10;
	public static final int INACTIVE = 11;
	
	public ClubInfo(String name, String address, String phoneNumber, String description, Department department, int status) {
		super();
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.department = department;
		this.description = description;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
