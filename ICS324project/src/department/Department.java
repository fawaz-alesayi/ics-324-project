package department;

public class Department {

	private int id;
	private DepartmentInfo info;
	
	public Department(int id, DepartmentInfo info) {
		super();
		this.setId(id);
		this.setInfo(info);
	}

	public DepartmentInfo getInfo() {
		return info;
	}

	public void setInfo(DepartmentInfo info) {
		this.info = info;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
