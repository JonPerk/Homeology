package db;

import java.sql.*;

public class DBConnection {
	// JDBC driver name and database URL
	private static DBConnection instance;
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private final static String DB_URL = "jdbc:mysql://localhost/EMP";
	
	//  Database credentials
	private final static String USER = "username";
	private final static String PASS = "password";
	private static Connection conn;
   
	private DBConnection() throws ClassNotFoundException, SQLException {
		//STEP 2: Register JDBC driver
		Class.forName(JDBC_DRIVER);
	}
   
	public static DBConnection getInstance() throws ClassNotFoundException, SQLException{
		if(instance == null){
			instance = new DBConnection();
		}
		String url = null;
		//if (SystemProperty.environment.value() ==
		    //SystemProperty.Environment.Value.Production) {
		  // Connecting from App Engine.
		  // Load the class that provides the "jdbc:google:mysql://"
		  // prefix.
		 // Class.forName("com.mysql.jdbc.GoogleDriver");
		 // url =
		   // "jdbc:google:mysql://homeology-database:homeology-db?user=root";
		//} else {
		 // Connecting from an external network.
		  Class.forName("com.mysql.jdbc.Driver");
		  url = "jdbc:mysql://173.194.248.24:3306?user=root";
		//}
		instance.conn = DriverManager.getConnection(url);
		return instance;	   
	}
	
	public Connection getConnection(){
		return conn;
	}

	public ResultSet doStatement(String sql) throws SQLException{
		System.out.println("Creating statement...");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		rs.close();
		stmt.close();
		conn.close();
		return rs;
	}
}
