package club.admin;

import project.IProjectRepository;
import project.Project;

public class UpdateProjectStatusUseCase {
	Project p;
	int newStatus;
	IProjectRepository projectRepository;
	public UpdateProjectStatusUseCase(Project p, int newStatus, IProjectRepository r) throws Exception {
		this.p = p;
		this.newStatus = newStatus;
		this.projectRepository = r;
	}
	public void execute() throws Exception {
		p.getInfo().setStatus(newStatus);
		projectRepository.update(p);
	}

}
