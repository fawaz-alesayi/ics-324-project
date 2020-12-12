package system_admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import club.IClubRepository;

public class MySQLAdminRepository implements IAdminRepository {
	private Connection conn;

	public MySQLAdminRepository(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public String findAdminById(int adminId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}




}
