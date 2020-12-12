package guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.OptionalInt;

import javax.xml.xpath.XPathEvaluationResult.XPathResultType;

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
		statement.setString(2, s.firstName);
		statement.setString(3, s.lastName);
		statement.setString(4, s.phoneNum);
		statement.setInt(5, s.standing.standing);
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
			Student newStudent = new Student(id, studentFirstName, studentLastName, studentPhone, studentAcademicStanding);
			return student.of(newStudent);
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

}
