package application;

import java.sql.*;

public class mySqlConnector {

	public static void main(String args[]) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://"
					+ "kcs.cnqw4wj71pc6.eu-central-1.rds.amazonaws.com/kcs",
					"kcs-api", "437eL8Xam5muluLowiFEq85e6Ek825");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from statustype;");

			while (rs.next()) {
				System.out.println(rs.getInt(1) + "  " + rs.getString(2));
				// rs.getInt(1) is the first column and rs.getString(2) is the
				// second column..
				// You have to take care of the mapping on your own here.
			}
			con.close();
		}

		catch (Exception e) {
			System.out.println(e);
		}

	}
}