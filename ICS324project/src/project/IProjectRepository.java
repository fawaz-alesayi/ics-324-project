package project;

import java.util.Optional;

import club.ClubInfo;

public interface IProjectRepository {
	public void insert(ProjectInfo p) throws Exception;
	public void insert(Project p) throws Exception;
	public Optional<Project> findProjectById(int id) throws Exception;
	public Optional<Project> findProjectByInfo(ProjectInfo p) throws Exception;
	public void update(Project p) throws Exception;
	
}
