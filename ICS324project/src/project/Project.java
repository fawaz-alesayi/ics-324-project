package project;

public class Project {
	private int id;
	private ProjectInfo projectInfo;
	
	public Project(int id, ProjectInfo info) {
		super();
		this.id = id;
		this.projectInfo = info;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProjectInfo getInfo() {
		return projectInfo;
	}

	public void setInfo(ProjectInfo info) {
		this.projectInfo = info;
	}
	
	
}
