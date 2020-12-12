package club;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import department.Department;
import department.IDepartmentRepository;
import department.MySQLDepartmentRepository;

public class MySQLClubRepository implements IClubRepository {
	private Connection conn;

	public MySQLClubRepository(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void insertClub(Club c) throws Exception {
		IDepartmentRepository departmentRepository = new MySQLDepartmentRepository(conn);
		Optional<Department> department = departmentRepository.findDepartmentById(c.getInfo().getDepartment().getId());

		if (department.isPresent()) {
			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO club(Name, Address, Phone, Des, DepartmentID, StatusID) VALUES(?, ?, ?, ?, ?, ?);");
			statement.setString(1, c.getInfo().getName());
			statement.setString(2, c.getInfo().getAddress());
			statement.setString(3, c.getInfo().getPhoneNumber());
			statement.setString(4, c.getInfo().getDescription());
			statement.setInt(5, department.get().getId());
			statement.setInt(6, c.getInfo().getStatus());
			statement.execute();
		} else
			throw new Exception(
					"No department with name: " + c.getInfo().getDepartment().getInfo().getName() + " found");
	}

	public Optional<String> findClubNameById(int clubId) throws Exception {
		PreparedStatement statement = conn.prepareStatement("SELECT Name FROM club WHERE club.id = ?");
		statement.setInt(1, clubId);
		statement.execute();
		ResultSet result = statement.executeQuery();
		Optional<String> clubName = Optional.empty();
		if (result.next())
			clubName = Optional.of(result.getString(1));
		return clubName;
	}

	public OptionalInt findClubIdByName(String clubName) throws Exception {
		PreparedStatement statement = conn.prepareStatement("SELECT id FROM club WHERE club.Name = ?");
		statement.setString(1, clubName);
		ResultSet result = statement.executeQuery();
		OptionalInt clubId = OptionalInt.empty();
		if (result.next())
			return clubId = OptionalInt.of(result.getInt(1));
		return clubId;
	}

	@Override
	public void insertClubApplicationForStudentWithId(int studentId, int clubId) throws Exception {
		PreparedStatement statement = conn
				.prepareStatement("INSERT INTO club_applicant(student_id, club_id) VALUES (?, ?);");
		statement.setInt(1, studentId);
		statement.setInt(2, clubId);
		statement.execute();
	}

	@Override
	public List<Integer> findClubApplicationsIdByStudentId(int studentId) throws Exception {
		PreparedStatement statement = conn.prepareStatement("SELECT id FROM club_applicant WHERE student_id = ?");
		statement.setInt(1, studentId);
		ResultSet result = statement.executeQuery();

		List<Integer> studentIds = new ArrayList<Integer>();
		addResultsToList(studentIds, result);

		return studentIds;
	}

	private void addResultsToList(List<Integer> l, ResultSet r) throws Exception {
		while (r.next())
			l.add(r.getInt(1));
	}

	@Override
	public void insertClub(ClubInfo c) throws Exception {
		IDepartmentRepository departmentRepository = new MySQLDepartmentRepository(conn);
		Optional<Department> department = departmentRepository.findDepartmentById(c.getDepartment().getId());

		if (department.isPresent()) {
			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO club(Name, Address, Phone, Des, DepartmentID, StatusID) VALUES(?, ?, ?, ?, ?, ?);");
			statement.setString(1, c.getName());
			statement.setString(2, c.getAddress());
			statement.setString(3, c.getPhoneNumber());
			statement.setString(4, c.getDescription());
			statement.setInt(5, department.get().getId());
			statement.setInt(6, c.getStatus());
			statement.execute();
		} else
			throw new Exception("No department with name: " + c.getDepartment() + " found");
	}

	@Override
	public Optional<Club> findByName(String clubName) throws Exception {
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM club WHERE club.name = ?");
		statement.setString(1, clubName);
		statement.execute();
		ResultSet result = statement.executeQuery();
		Optional<Club> club = Optional.empty();
		if (result.next()) {
			int clubId = result.getInt(1);
			String clubNameFound = result.getString(2);
			String clubAddress = result.getString(3);
			String clubPhoneNumber = result.getString(4);
			String clubDescription = result.getString(5);
			int clubDepartmentId = result.getInt(6);
			int clubStatus = result.getInt(7);

			// find department
			IDepartmentRepository departmentRepository = new MySQLDepartmentRepository(conn);
			Optional<Department> d = departmentRepository.findDepartmentById(clubDepartmentId);
			if (d.isEmpty())
				throw new Exception("Club with name: " + clubNameFound + " has no department");

			ClubInfo foundClubInfo = new ClubInfo(clubNameFound, clubAddress, clubPhoneNumber, clubDescription, d.get(),
					clubStatus);
			Club foundClub = new Club(clubId, foundClubInfo);
			club = Optional.of(foundClub);
		}
		return club;
	}

	@Override
	public Optional<Club> findById(int id) throws Exception {
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM club WHERE club.id = ?");
		statement.setInt(1, id);
		statement.execute();
		ResultSet result = statement.executeQuery();
		Optional<Club> club = Optional.empty();
		if (result.next()) {
			String clubName = result.getString(2);
			String clubAddress = result.getString(3);
			String clubPhoneNumber = result.getString(4);
			String clubDescription = result.getString(5);
			int clubDepartmentId = result.getInt(6);
			int clubStatus = result.getInt(7);

			// find department
			IDepartmentRepository departmentRepository = new MySQLDepartmentRepository(conn);
			Optional<Department> d = departmentRepository.findDepartmentById(clubDepartmentId);
			if (d.isEmpty())
				throw new Exception("Club with id: " + id + " has no department");

			ClubInfo foundClubInfo = new ClubInfo(clubName, clubAddress, clubPhoneNumber, clubDescription, d.get(),
					clubStatus);
			Club foundClub = new Club(id, foundClubInfo);
			club = Optional.of(foundClub);
		}
		return club;
	}

}
