package guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import javax.xml.xpath.XPathEvaluationResult.XPathResultType;

import Controllers.DatabaseDefinitions;

public class MySQLStudentRepository implements IStudentRepository {
	private Connection conn;

	public MySQLStudentRepository(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void insertStudent(Student s) throws Exception {
		PreparedStatement statement = conn.prepareStatement(
				"INSERT IGNORE INTO student(id, Fname, Lname, phone, StatusID) VALUES (?, ?, ?, ?, ?);");
		statement.setInt(1, s.id);
		statement.setString(2, s.info.firstName);
		statement.setString(3, s.info.lastName);
		statement.setString(4, s.info.phoneNum);
		statement.setInt(5, s.info.standing.standing);
		statement.execute();
	}

	@Override
	public Optional<Student> findStudentById(int id) throws Exception {
		PreparedStatement statement = conn.prepareStatement("SELECT `ID`, Fname, Lname, phone, StatusID FROM student WHERE student.id = ? LIMIT 1;");
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		Optional<Student> student = Optional.empty();
		if (result.next()) {
			String studentFirstName = result.getString("Fname");
			String studentLastName = result.getString("Lname");
			String studentPhone = result.getString("phone");
			int studentStatusID = result.getInt("StatusID");
			AcademicStanding studentAcademicStanding = new AcademicStanding(studentStatusID);
			StudentInfo sInfo = new StudentInfo(studentFirstName, studentLastName, studentPhone, studentAcademicStanding);
			Student newStudent = new Student(id, sInfo);
			student = Optional.of(newStudent);
		}
		return student;
	}

	@Override
	public OptionalInt findStudentIdByPhone(String phoneNum) throws Exception {
		PreparedStatement statement = conn
				.prepareStatement("SELECT `ID` FROM student WHERE student.Phone = ? LIMIT 1;");
		statement.setString(1, phoneNum);
		ResultSet result = statement.executeQuery();
		OptionalInt studentId = OptionalInt.empty();
		if (result.next())
			return studentId = OptionalInt.of(result.getInt(1));
		return studentId;
	}

	@Override
	public List<Student> findStudentsWhoAreApplyingToClubId(int clubId) throws Exception {
		PreparedStatement ps = conn.prepareStatement(
				"SELECT club_applicant.student_id " + "FROM club_applicant " + "WHERE club_id = ? "
						+ "AND StatusID = ?;");
		ps.setInt(1, clubId);
		ps.setInt(2, DatabaseDefinitions.PENDING_APPLICATION);
		ps.execute();
		ResultSet results = ps.executeQuery();
		List<Integer> applicantsStudentIds = new ArrayList<Integer>();
		List<Student> studentList = new ArrayList<Student>();

		while (results.next())
			applicantsStudentIds.add(results.getInt(1));
		
		for (int id: applicantsStudentIds) {
			Optional<Student> s = findStudentById(id);
			if (s.isPresent())
				studentList.add(s.get());
		}
		
		return studentList;
	}

}
