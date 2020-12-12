package guest;

public class Student {
	public int id;
	String firstName;
	String lastName;
	public String phoneNum;
	AcademicStanding standing;

	public Student(int id, String firstName, String lastName, String phoneNum, AcademicStanding standing) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNum = phoneNum;
		this.standing = standing;
	}
}
