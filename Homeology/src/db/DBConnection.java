package db;

import java.sql.*;

public class DBConnection {
	// JDBC driver name and database URL
	private static DBConnection instance;
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private final String DB_URL = "jdbc:mysql://localhost/EMP";
	
	//  Database credentials
	private final String USER = "username";
	private final String PASS = "password";
	private Connection conn;
   
	private DBConnection() throws ClassNotFoundException, SQLException {
		//STEP 2: Register JDBC driver
		Class.forName(JDBC_DRIVER);
	}
   
	public DBConnection getInstance() throws ClassNotFoundException, SQLException{
		if(instance == null){
			instance = new DBConnection();
		}
		instance.conn = DriverManager.getConnection(DB_URL,USER,PASS);
		return instance;	   
	}

	public ResultSet doStatement(Statement stmt) throws SQLException{
		System.out.println("Creating statement...");
		stmt = conn.createStatement();
		String sql;
		sql = "SELECT id, first, last, age FROM Employees";
		ResultSet rs = stmt.executeQuery(sql);
		rs.close();
		stmt.close();
		conn.close();
		return rs;
	}
}
