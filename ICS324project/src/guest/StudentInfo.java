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
}
