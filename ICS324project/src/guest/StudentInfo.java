package guest;

public class StudentInfo {
	String firstName;
	String lastName;
	public String phoneNum;
	AcademicStanding standing;
	
	public StudentInfo(String firstName, String lastName, String phoneNum, AcademicStanding standing) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNum = phoneNum;
		this.standing = standing;
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

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public AcademicStanding getStanding() {
		return standing;
	}

	public void setStanding(AcademicStanding standing) {
		this.standing = standing;
	}
	
	
}
