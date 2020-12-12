package guest;

public class Student {
	public int id;
	public StudentInfo info;


	public Student(int id, StudentInfo info) {
		super();
		this.id = id;
		this.info = info;
	}


	@Override
	public String toString() {
		return info.firstName + " " + info.lastName;
	}
	
	
	
}
