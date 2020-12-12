package utility;

import java.sql.*;

public class CreateDbConnection {
	 Connection conn = null;
	    public static Connection createConnection(){
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://"
						+ "kcs.cnqw4wj71pc6.eu-central-1.rds.amazonaws.com/kcs",
						"kcs-api", "437eL8Xam5muluLowiFEq85e6Ek825");
	            return con;
	        } catch (ClassNotFoundException | SQLException ex) {
	            System.err.println("ConnectionUtil : "+ex.getMessage());
	           return null;
	        }
	    }
}
