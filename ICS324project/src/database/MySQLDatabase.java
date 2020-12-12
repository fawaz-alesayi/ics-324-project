package database;

import java.sql.*;

public final class MySQLDatabase {
    private static MySQLDatabase instance;
    private Connection connection;

    private MySQLDatabase() throws Exception {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://"
					+ "kcs.cnqw4wj71pc6.eu-central-1.rds.amazonaws.com/kcs",
					"kcs-api", "437eL8Xam5muluLowiFEq85e6Ek825");

    }

    public static MySQLDatabase getInstance() throws Exception {
        if (instance == null) {
            instance = new MySQLDatabase();
        }
        return instance;
    }
    
    public Connection getConnection() {
    	return connection;
    }
}