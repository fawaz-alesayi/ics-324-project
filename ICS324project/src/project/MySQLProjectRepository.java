package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.OptionalInt;

import club.Club;
import club.ClubInfo;
import club.IClubRepository;
import department.IDepartmentRepository;
import department.MySQLDepartmentRepository;

public class MySQLProjectRepository implements IProjectRepository {
	private Connection conn;
	private IClubRepository clubRepository;

	public MySQLProjectRepository(Connection conn, IClubRepository clubRepository) {
		super();
		this.conn = conn;
		this.clubRepository = clubRepository;
	}

	@Override
	public void insert(Project p) throws Exception {
		Optional<Club> club = clubRepository.findByName(p.getInfo().getClub().getInfo().getName());
		if (club.isEmpty())
			throw new Exception("No club name: " + p.getInfo().getClub().getInfo().getName() + " found");

		int clubId = club.get().getId();
		
		PreparedStatement statement = conn.prepareStatement(
				"INSERT INTO project(ID, Name, ProjectTypeID, clubID, des, StatusID) VALUES(?, ?, ?, ?, ?, ?);");
		statement.setInt(1, p.getId());
		statement.setString(2, p.getInfo().getName());
		statement.setInt(3, p.getInfo().getType());
		statement.setInt(4, clubId);
		statement.setString(5, p.getInfo().getDescription());
		// statement.setTimestamp(5, p.getStartDate());
		// statement.setInt(6, p.getEndDate());
		statement.setInt(6, p.getInfo().getStatus());
		statement.execute();
	}

	@Override
	public void insert(ProjectInfo p) throws Exception {
		Optional<Club> club = clubRepository.findByName(p.getClub().getInfo().getName());
		if (club.isEmpty())
			throw new Exception("No club name: " + p.getClub().getInfo().getName() + " found");

		int clubId = club.get().getId();
		PreparedStatement statement = conn.prepareStatement(
				"INSERT INTO project(Name, ProjectTypeID, clubID, des, StatusID) VALUES(?, ?, ?, ?, ?);");
		statement.setString(1, p.getName());
		statement.setInt(2, p.getType());
		statement.setInt(3, clubId);
		statement.setString(4, p.getDescription());
		// statement.setTimestamp(5, p.getStartDate());
		// statement.setInt(6, p.getEndDate());
		statement.setInt(5, p.getStatus());
		statement.execute();
	}

	private void updateProjectTime(ProjectInfo p) throws Exception {

	}

	@Override
	public Optional<Project> findProjectByInfo(ProjectInfo p) throws Exception {
		Optional<Club> club = clubRepository.findByName(p.getClub().getInfo().getName());
		if (club.isEmpty())
			throw new Exception("No club name: " + p.getClub().getInfo().getName() + " found");
		PreparedStatement statement = conn.prepareStatement(
				"SELECT " + "project.ID, " + "project.Name, " + "project.ProjectTypeID, " + "project.des, "
						+ "project.StartDate, " + "project.EndDate, " + "project.StatusID " + "FROM project, club "
						+ "WHERE project.Name = ? " + "AND club.Name = ? " + "AND project.clubID = club.ID;");
		statement.setString(1, p.getName());
		statement.setString(2, p.getClub().getInfo().getName());
		ResultSet result = statement.executeQuery();
		Optional<Project> project = Optional.empty();
		if (result.next()) {
			ProjectInfo projectInfoFound = new ProjectInfo().name(result.getString("Name"))
					.type(result.getInt("ProjectTypeID")).description(result.getString("des"))
					.status(result.getInt("StatusID")).club(club.get());

			if (result.getTimestamp("StartDate") == null)
				projectInfoFound.startDate(null);
			else
				projectInfoFound.startDate(new Date(result.getTimestamp("StartDate").getTime()));

			if (result.getTimestamp("EndDate") == null)
				projectInfoFound.endDate(null);
			else
				projectInfoFound.endDate(new Date(result.getTimestamp(6).getTime()));

			Project projectFound = new Project(result.getInt(1), projectInfoFound);
			project = Optional.of(projectFound);
		}
		return project;
	}

	@Override
	public Optional<Project> findProjectById(int id) throws Exception {

		
		PreparedStatement statement = conn.prepareStatement("SELECT " + "project.clubID, " + "project.ID, " + "project.Name, "
				+ "project.ProjectTypeID, " + "project.des, " + "project.StartDate, " + "project.EndDate, "
				+ "project.StatusID " + "FROM project " + "WHERE project.ID = ?;");
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		Optional<Project> project = Optional.empty();
		if (result.next()) {
			Optional<Club> club = clubRepository.findById(result.getInt("clubID"));
			if (club.isEmpty())
				throw new Exception("project found with invalid club id: " + id);
			
			ProjectInfo projectInfoFound = new ProjectInfo().name(result.getString("Name"))
					.type(result.getInt("ProjectTypeID")).description(result.getString("des"))
					.status(result.getInt("StatusID")).club(club.get());

			if (result.getTimestamp("StartDate") == null)
				projectInfoFound.startDate(null);
			else
				projectInfoFound.startDate(new Date(result.getTimestamp("StartDate").getTime()));

			if (result.getTimestamp("EndDate") == null)
				projectInfoFound.endDate(null);
			else
				projectInfoFound.endDate(new Date(result.getTimestamp(6).getTime()));

			Project projectFound = new Project(result.getInt(1), projectInfoFound);
			project = Optional.of(projectFound);
		}
		return project;
	}

	@Override
	public void update(Project p) throws Exception {
		PreparedStatement stmnt = conn.prepareStatement("UPDATE project "
				+ "SET "
				+ "ID = ?, "
				+ "Name = ?, "
				+ "ProjectTypeID = ?, "
				+ "clubID = ?,"
				+ "des = ?,"
				+ "StartDate = ?, "
				+ "EndDate = ?, "
				+ "StatusID = ? "
				+ "WHERE project.ID = ?;");
		stmnt.setInt(1, p.getId());
		stmnt.setString(2, p.getInfo().getName());
		stmnt.setInt(3, p.getInfo().getType());
		stmnt.setInt(4, p.getInfo().getClub().getId());
		stmnt.setString(5, p.getInfo().getDescription());
		stmnt.setTimestamp(6, p.getInfo().getStartDate().isPresent() ? new Timestamp(p.getInfo().getStartDate().get().getTime())
				: null);
		stmnt.setTimestamp(7, p.getInfo().getEndDate().isPresent() ? new Timestamp(p.getInfo().getEndDate().get().getTime())
				: null);
		stmnt.setInt(8, p.getInfo().getStatus());
		stmnt.setInt(9, p.getId());
		stmnt.execute();
	}

}
