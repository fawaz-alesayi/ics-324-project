package system_admin;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.OptionalInt;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import club.Club;
import club.ClubInfo;
import club.IClubMemberRepository;
import club.IClubRepository;
import club.MySQLClubMemberRepository;
import club.MySQLClubRepository;
import department.Department;
import department.IDepartmentRepository;
import department.MySQLDepartmentRepository;
import guest.AcademicStanding;
import guest.IStudentRepository;
import guest.MySQLStudentRepository;
import guest.Student;
import tests.TestDatabase;

class AdminTests {
	private static TestDatabase testDB;
	static IClubRepository clubTestRepository;
	private static IStudentRepository studentRepository;
	private static IClubMemberRepository clubMemberRepository;
	private static IDepartmentRepository departmentRepository;
	
	@BeforeAll
	static void setupRepositories() throws Exception {
		 testDB = TestDatabase.getInstance();
		 clubTestRepository = new MySQLClubRepository(testDB.getConnection());
		 studentRepository = new MySQLStudentRepository(testDB.getConnection());
		 clubMemberRepository = new MySQLClubMemberRepository(testDB.getConnection());
		 departmentRepository = new MySQLDepartmentRepository(testDB.getConnection());
	}
	
	
	/**
	 * Given: A department of name "d" exists and a system admin is logged in <br>
	 * When: A club is inserted with a department of name "d" <br>
	 * Then: A club should be saved
	 * @throws Exception
	 */
	@Test
	void addNewClub() throws Exception {
		Optional<Department> d = departmentRepository.findDepartmentbyName("ICS Department");
		assertTrue(d.isPresent());
		ClubInfo c = new ClubInfo("ClubName", "ClubAddress", "+123452152", "A Test Club", d.get(), ClubInfo.INACTIVE);
		AddClubUseCase addClub = new AddClubUseCase(new Admin(), c, clubTestRepository);
		addClub.execute();
		Optional<Club> club = clubTestRepository.findByName("ClubName");
		assertTrue(club.isPresent());
	}
	
	/**
	 * Given: A club exists <br>
	 * When: the system admin adds a student to a club <br>
	 * Then: the student is added to the club
	 * @throws Exception 
	 */
	@Test
	void addNewMemberToClub() throws Exception {
		Optional<Department> d = departmentRepository.findDepartmentbyName("ICS Department");
		assertTrue(d.isPresent());
		Optional<Club> c = clubTestRepository.findByName("EE club");
		assertTrue(c.isPresent());
		
		Student student = new Student(12523, "Ahmed", "Al-Khaldi", "+21513135", new AcademicStanding(AcademicStanding.SENIOR));
		AddNewMemberToClubUseCase usecase = new AddNewMemberToClubUseCase(student, c.get().getId(), clubTestRepository, studentRepository, clubMemberRepository);
		usecase.execute();
		assertTrue(studentIsAClubMemberOf(c.get().getId(), student));
	}
	
	private boolean studentIsAClubMemberOf(int clubId, Student s) throws Exception {
		return clubMemberRepository.studentIsAClubMemberOf(clubId, s);
	}
	
	@Test
	void changeMemberPositionInAClub() {
		fail("Not yet implemented");
	}
	
	@Test
	void findNumberOfMembersInAClub() {
		fail("Not yet implemented");
	}
	
	@Test
	void findNumberOfProjectsInAClub() {
		fail("Not yet implemented");
	}

}
