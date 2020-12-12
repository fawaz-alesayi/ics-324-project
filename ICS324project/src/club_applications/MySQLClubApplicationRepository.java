package club_applications;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import javax.xml.xpath.XPathEvaluationResult.XPathResultType;

import com.sun.glass.ui.Application;

import Controllers.DatabaseDefinitions;
import club.Club;
import club.ClubInfo;
import club.IClubRepository;
import department.Department;
import department.IDepartmentRepository;
import department.MySQLDepartmentRepository;
import guest.IStudentRepository;
import guest.MySQLStudentRepository;
import guest.Student;
import utility.DateAdapters;

public class MySQLClubApplicationRepository implements IClubApplicationRepository {
	private Connection conn;
	IStudentRepository studentRepo;
	IClubRepository clubRepo;

	public MySQLClubApplicationRepository(Connection conn, IStudentRepository studentRepo, IClubRepository clubRepo) {
		super();
		this.conn = conn;
		this.studentRepo = studentRepo;
		this.clubRepo = clubRepo;
	}

	@Override
	public Optional<ClubApplication> findById(int applicationId) throws Exception {
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM club_applicant WHERE club_applicant.id = ?");
		statement.setInt(1, applicationId);
		statement.execute();
		ResultSet result = statement.executeQuery();
		Optional<ClubApplication> application = Optional.empty();
		if (result.next()) {
			int applicantStudentId = result.getInt("student_id");
			Optional<Student> s = studentRepo.findStudentById(applicantStudentId);
			if (s.isEmpty())
				throw new Exception("Student id: " + applicantStudentId + "is not found for application" + applicationId);
			
			int applicantClubId = result.getInt("club_id");
			Optional<Club> c = clubRepo.findById(applicantClubId);
			if (c.isEmpty())
				throw new Exception("Club is not found for application: " + applicationId);


			Date creationDate = DateAdapters.getDateFromTimestamp(result.getTimestamp("creation_date"));
			ClubApplicationInfo info = new ClubApplicationInfo(s.get(), c.get(), creationDate);
			ClubApplication foundApplication = new ClubApplication(applicationId, info);
			application = Optional.of(foundApplication);
		}
		return application;
		
	}

	@Override
	public void update(ClubApplication c) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(ClubApplication c) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
