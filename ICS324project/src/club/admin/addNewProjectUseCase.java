package club.admin;

import project.IProjectRepository;
import project.ProjectInfo;

public class addNewProjectUseCase {
	ProjectInfo p;
	IProjectRepository projectRepository;
	public addNewProjectUseCase(ProjectInfo p, IProjectRepository r) throws Exception {
		this.p = p;
		this.projectRepository = r;
	}
	public void execute() throws Exception {
		projectRepository.insert(p);
	}

}
