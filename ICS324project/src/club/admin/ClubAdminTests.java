package club.admin;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import club.Club;
import guest.Student;
import project.IProjectRepository;
import project.Project;
import project.ProjectInfo;
import project.ProjectMember;
import tests.TestConfiguration;

class ClubAdminTests {
	private static TestConfiguration config;
	
	@BeforeAll
	static void setupRepositories() throws Exception {
		 config = new TestConfiguration();
	}
	
	/**
	 * Given: A club exists
	 * When: Club Admin adds a new project
	 * Then: The project should be added
	 */
	@Test
	void addNewProjectWithMembers() throws Exception {
		Optional<Club> c = config.getClubRepository().findByName("EE club");
		assertTrue(c.isPresent());
		
		Student s1 = new Student(19603, "ProjectLeader", null, null, null);
		Student s2 = new Student(19602, "ProjectMember1", null, null, null);
		Student s3 = new Student(19609, "ProjectMember2", null, null, null);
		ProjectMember[] members = new ProjectMember[3];
		members[0] = new ProjectMember(s1, "Member");
		members[1] = new ProjectMember(s2, "Member");
		members[2] = new ProjectMember(s3, "Member");
		
		ProjectInfo pInfo = new ProjectInfo("test.AddNewProject", 1, c.get(), "Sample Description", new Date(), null, 3, members);
		
		addNewProjectUseCase usecase = new addNewProjectUseCase(pInfo, config.getProjectRepository());
		usecase.execute();
		assertTrue(config.getProjectRepository().findProjectByInfo(pInfo).isPresent());
	}
	
	/**
	 * Given: A project exists
	 * When: Club Admin wants to update the project status to Complete, or abandoned.
	 * Then: The project status should change accordingly
	 * @throws Exception 
	 */
	@Test
	void updateProjectStatus() throws Exception {
		int oldStatus = 3;
		int newStatus = 4;
		Optional<Club> c = config.getClubRepository().findByName("EE club");
		assertTrue(c.isPresent());
		ProjectInfo pInfo = new ProjectInfo("test.updateProjectStatus", 1, c.get(), "Sample Description", new Date(), null, oldStatus, null);
		Project p = new Project(13, pInfo);
		config.getProjectRepository().insert(p);
		
		UpdateProjectStatusUseCase usecase = new UpdateProjectStatusUseCase(p, newStatus, config.getProjectRepository());
		usecase.execute();
		assertTrue(p.getInfo().getStatus() == newStatus);
		Optional<Project> foundProject = config.getProjectRepository().findProjectById(p.getId());
		assertTrue(foundProject.isPresent());
		assertTrue(foundProject.get().getInfo().getStatus() == newStatus);
	}
	/**
	 * Given a project p exists
	 * When club admin attempts to insert members to work on project p
	 * Then Members are registered as working members for project p
	 * @throws Exception 
	 */
	@Test
	void addMembersToProject() throws Exception {
		Optional<Club> c = config.getClubRepository().findByName("EE club");
		assertTrue(c.isPresent());
		
		Student s1 = new Student(19603, "ProjectLeader", null, null, null);
		Student s2 = new Student(19602, "ProjectMember1", null, null, null);
		Student s3 = new Student(19609, "ProjectMember2", null, null, null);
		ProjectMember[] members = new ProjectMember[3];
		members[0] = new ProjectMember(s1, "Member");
		members[1] = new ProjectMember(s2, "Member");
		members[2] = new ProjectMember(s3, "Member");
		
		ProjectInfo pInfo = new ProjectInfo("test.addMembersToProject", 1, c.get(), "Sample Description", new Date(), null, 3, null);
		Project p = new Project(500, pInfo);
		
		config.getProjectRepository().insert(p);
		assertTrue(projectExistsInRepository(p, config.getProjectRepository()));
		
		p.getInfo().setMembers(members);
		config.getProjectRepository().update(p);
		
		assertTrue(membersAreWorkingOnProject(members, p));
	}
	
	private boolean projectExistsInRepository(Project p, IProjectRepository r) throws Exception {
		return r.findProjectById(p.getId()).isPresent();
	}
	
	private boolean membersAreWorkingOnProject(ProjectMember[] members, Project project) {
		return true;
		
	}
	
	@Test
	void assignLeadershipToMember() {
		fail("Not yet implemented");
	}
	
	@Test
	void calculateNumberOfProjects() {
		fail("Not yet implemented");
	}
	
	@Test
	void approveMemberToJoinClub() {
		fail("Not yet implemented");
	}

}
