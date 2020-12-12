package club;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import guest.Student;

public class MySQLClubMemberRepository implements IClubMemberRepository {
	private Connection conn;
	private IClubRepository clubRepository;

	public MySQLClubMemberRepository(Connection conn) {
		super();
		this.conn = conn;
		clubRepository = new MySQLClubRepository(conn);
	}

	@Override
	public void insertClubMemberToClub(Student student, int clubId) throws Exception {
		Optional<Club> club = clubRepository.findById(clubId);
		if (club.isEmpty())
			throw new Exception("No club id: " + clubId + " found");
		
		PreparedStatement statement = conn.prepareStatement(
				"INSERT INTO clubmember(ClubID, StudentID, StatusID) VALUES(?, ?, ?);");
		statement.setInt(1, clubId);
		statement.setInt(2, student.id);
		statement.setInt(3, 5);
		statement.execute();
	}

	@Override
	public boolean studentIsAClubMemberOf(int clubId, Student student) throws Exception {
		PreparedStatement statement = conn
				.prepareStatement
				("SELECT ClubID FROM clubmember WHERE clubmember.StudentId = ? "
				+ "AND clubmember.ClubID = ?;");
		statement.setInt(1, student.id);
		statement.setInt(2, clubId);
		ResultSet result = statement.executeQuery();
		if (result.next())
			return true;
		else
			return false;
	}

}
