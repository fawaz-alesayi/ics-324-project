package project;

import java.util.Optional;

public interface IProjectMemberRepository {
	public Optional<ProjectMember> find(ProjectMember p);
	public Optional<ProjectMember> insert(ProjectMember p);
	public Optional<ProjectMember> update(ProjectMember p);
	public Optional<ProjectMember> delete(ProjectMember p);
}
