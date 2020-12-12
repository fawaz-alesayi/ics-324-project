package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import club.IClubRepository;
import club.MySQLClubRepository;
import guest.AcademicStanding;
import guest.ApplyForAClubUseCase;
import guest.IStudentRepository;
import guest.MySQLStudentRepository;
import guest.Student;
import guest.StudentInfo;

class Register {

	/**
	 * Given a club exists
	 * When Students provide their id, first name, last name, and academic standing (freshmen, sophomore, ...)
	 * and the club exists
	 * Then a registration form is saved for the club admin to view.
	 * @throws Exception 
	 */
	@Test
	void registerForClub() throws Exception {
		TestDatabase TestDB = TestDatabase.getInstance();
		IStudentRepository studentTestRepository = new MySQLStudentRepository(TestDB.getConnection());
		IClubRepository clubTestRepository = new MySQLClubRepository(TestDB.getConnection());
		AcademicStanding standing = new AcademicStanding(AcademicStanding.FRESHMAN);
		Student guest = new Student(2017, new StudentInfo("Ali", "Al-Ahmadi", "05599553486", standing));
		ApplyForAClubUseCase usecase = new ApplyForAClubUseCase(guest, "icsClub", clubTestRepository, studentTestRepository, TestDB.getConnection());
		usecase.execute();
		List<Integer> applicationIds = clubTestRepository.findClubApplicationsIdByStudentId(guest.id);
		assertTrue(applicationIds.size() != 0);
	}

}
