package department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import club.IClubRepository;

public class MySQLDepartmentRepository implements IDepartmentRepository {
	private Connection conn;

	public MySQLDepartmentRepository(Connection conn) {
		super();
		this.conn = conn;
	}

	public OptionalInt findDepartmentIdByName(String departmentName) throws Exception {
		Optional<Department> d = findDepartmentbyName(departmentName);
		OptionalInt departmentId = OptionalInt.empty();
		if (d.isPresent())
			departmentId = OptionalInt.of(d.get().getId());
		return departmentId;
	}

	@Override
	public Optional<Department> findDepartmentbyName(String departmentName) throws Exception {
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM department WHERE department.name = ? LIMIT 1;");
		statement.setString(1, departmentName);
		ResultSet databaseResult = statement.executeQuery();
		
		Optional<Department> department = Optional.empty();
		if (databaseResult.next()) {
			DepartmentInfo departmentInfoFound = new DepartmentInfo(databaseResult.getString(2),
					databaseResult.getString(3),
					databaseResult.getString(4));
			Department departmentFound = new Department(databaseResult.getInt(1), departmentInfoFound);
			department = Optional.of(departmentFound);
		}
		return department;
	}

	@Override
	public Optional<Department> findDepartmentById(int id) throws Exception {
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM department WHERE department.ID = ? LIMIT 1;");
		statement.setInt(1, id);
		ResultSet databaseResult = statement.executeQuery();
		
		Optional<Department> department = Optional.empty();
		if (databaseResult.next()) {
			DepartmentInfo departmentInfoFound = new DepartmentInfo(databaseResult.getString(2),
					databaseResult.getString(3),
					databaseResult.getString(4));
			Department departmentFound = new Department(databaseResult.getInt(1), departmentInfoFound);
			department = Optional.of(departmentFound);
		}
		return department;
	}




}
