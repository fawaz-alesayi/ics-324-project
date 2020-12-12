package tests;

import club.IClubMemberRepository;
import club.IClubRepository;
import club.MySQLClubMemberRepository;
import club.MySQLClubRepository;
import guest.IStudentRepository;
import guest.MySQLStudentRepository;
import project.IProjectMemberRepository;
import project.IProjectRepository;
import project.MySQLProjectRepository;

public class TestConfiguration {
	private TestDatabase testDB;
	private IClubRepository clubRepository;
	private IStudentRepository studentRepository;
	private IClubMemberRepository clubMemberRepository;
	private IProjectRepository projectRepository;
	private IProjectMemberRepository projectMemberRepository;
	
	public TestConfiguration() throws Exception {
		 testDB = TestDatabase.getInstance();
		 clubRepository = new MySQLClubRepository(testDB.getConnection());
		 studentRepository = new MySQLStudentRepository(testDB.getConnection());
		 clubMemberRepository = new MySQLClubMemberRepository(testDB.getConnection());
		 projectRepository = new MySQLProjectRepository(testDB.getConnection(), clubRepository);
	}

	public IClubRepository getClubRepository() {
		return clubRepository;
	}

	public IStudentRepository getStudentRepository() {
		return studentRepository;
	}

	public IClubMemberRepository getClubMemberRepository() {
		return clubMemberRepository;
	}

	public IProjectRepository getProjectRepository() {
		return projectRepository;
	}
	
	
}
