package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import mls.area.Area;
import mls.area.AreaFactory;
import mls.property.Buy;
import mls.property.PropertyFactory;
import mls.property.Rent;
import myapp.Worker;

import com.google.appengine.api.utils.SystemProperty;

import constants.BedsAndBaths;
import constants.DownPayTypes;

public class DBConnection {
	private String url;
	private static final Logger log = Logger.getLogger(DBConnection.class.getName());
	private final Connection conn;
	
	public DBConnection() throws ClassNotFoundException, SQLException {
		if (SystemProperty.environment.value() ==
		    SystemProperty.Environment.Value.Production) {
			//Connecting from App Engine.
			//Load the class that provides the "jdbc:google:mysql://"
		  // prefix.
		  Class.forName("com.mysql.jdbc.GoogleDriver");
		 url =
		    "jdbc:google:mysql://homeology-database:homeology-db/Homeology?user=root";
		} else {
		 // Connecting from an external network.
		  Class.forName("com.mysql.jdbc.Driver");
		  url = "jdbc:mysql://127.0.0.1:3306/test?user=root";
		}
		conn = DriverManager.getConnection(url);
	}
	
	public void createArea(Area area) throws ClassNotFoundException, SQLException{
		PreparedStatement stmtCheck = null;
		PreparedStatement stmtUpdate = null;
		ResultSet rs = null;
		try{
			String s = "SELECT * FROM Cities\nWHERE MLS_ID = "
					+ area.getArea() + " AND City = ?;";
			stmtCheck = conn.prepareStatement(s);
			s = "INSERT INTO Cities (MLS_ID, City) VALUES ("
					+ area.getArea() + ", ?);";
			stmtUpdate = conn.prepareStatement(s);
			for(String city : area.getCities()){
				stmtCheck.setString(1, city);
				rs = stmtCheck.executeQuery();
				if(!rs.first()){
					stmtUpdate.setString(1, city);
					stmtUpdate.executeUpdate();
				}
				rs.close();
			}
			stmtCheck.close();
			stmtUpdate.close();
			s = "SELECT * FROM Zip_Code"
					+ "\nWHERE MLS_ID = " + area.getArea() + " AND zipcode = ?;";
			stmtCheck = conn.prepareStatement(s);
			s = "INSERT INTO Zip_Code (MLS_ID, zipcode) VALUES ("
					+ area.getArea() + ", ?);";
			stmtUpdate = conn.prepareStatement(s);
			for(int i : area.getZips()){
				stmtCheck.setInt(1, i);
				rs = stmtCheck.executeQuery();
				if(!rs.first()){
					stmtUpdate.setInt(1, i);
					stmtUpdate.executeUpdate();
				}
				rs.close();
			}
			stmtCheck.close();
			stmtUpdate.close();
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmtCheck != null)
					stmtCheck.close();
				if(stmtUpdate != null)
					stmtUpdate.close();	
			} catch(SQLException e){}
		}
	}
	
	public synchronized void updateAreaCity(Area area) throws ClassNotFoundException, SQLException{
		PreparedStatement stmtCheck = null;
		PreparedStatement stmtUpdate = null;
		ResultSet rs = null;
		try{
			String s = "SELECT * FROM Cities\nWHERE MLS_ID = "
					+ area.getArea() + " AND City = ?;";
			stmtCheck = conn.prepareStatement(s);
			s = "INSERT INTO Cities (MLS_ID, City) VALUES ("
					+ area.getArea() + ", ?);";
			stmtUpdate = conn.prepareStatement(s);
			for(String city : area.getCities()){
				stmtCheck.setString(1, city);
				rs = stmtCheck.executeQuery();
				if(!rs.first()){
					stmtUpdate.setString(1, city);
					stmtUpdate.executeUpdate();
				}
				rs.close();
			}
			stmtCheck.close();
			stmtUpdate.close();
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmtCheck != null)
					stmtCheck.close();
				if(stmtUpdate != null)
					stmtUpdate.close();
			} catch(SQLException e){}
		}
	}
	
	public synchronized void updateAreaZips(Area area) throws ClassNotFoundException, SQLException{
		PreparedStatement stmtCheck = null;
		PreparedStatement stmtUpdate = null;
		ResultSet rs = null;
		try{
			String s = "SELECT * FROM Zip_Code"
					+ "\nWHERE MLS_ID = " + area.getArea() + " AND zipcode = ?;";
			stmtCheck = conn.prepareStatement(s);
			s = "INSERT INTO Zip_Code (MLS_ID, zipcode) VALUES ("
					+ area.getArea() + ", ?);";
			stmtUpdate = conn.prepareStatement(s);
			for(int i : area.getZips()){
				stmtCheck.setInt(1, i);
				rs = stmtCheck.executeQuery();
				if(!rs.first()){
					stmtUpdate.setInt(1, i);
					stmtUpdate.executeUpdate();
				}
				rs.close();
			}
			stmtCheck.close();
			stmtUpdate.close();
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmtCheck != null)
					stmtCheck.close();
				if(stmtUpdate != null)
					stmtUpdate.close();
			} catch(SQLException e){}
		}
	}
	
	public void createRent(Rent rent) throws ClassNotFoundException, SQLException{
		PreparedStatement stmtCheck = null;
		PreparedStatement stmtUpdate = null;
		ResultSet rs = null;
		try{
			String s = "SELECT * FROM Rent"
					+ "\nWHERE MLS_ID = ? AND Beds = ? AND Baths = ? AND Attached = ?;";
			stmtCheck = conn.prepareStatement(s);
			stmtCheck.setInt(1, rent.getArea());
			stmtCheck.setInt(2, rent.getBeds());
			stmtCheck.setInt(3, rent.getBaths());
			stmtCheck.setBoolean(4, rent.isAttached());		
			rs = stmtCheck.executeQuery();
			if(!rs.first()){
				s = "INSERT INTO Rent (MLS_ID, Beds, Baths, Attached, Price)"
						+ " VALUES (?,?,?,?,?);";
				stmtUpdate = conn.prepareStatement(s);
				stmtUpdate.setInt(1, rent.getArea());
				stmtUpdate.setInt(2, rent.getBeds());
				stmtUpdate.setInt(3, rent.getBaths());
				stmtUpdate.setBoolean(4, rent.isAttached());
				stmtUpdate.setDouble(5, rent.getPrice());
				stmtUpdate.executeUpdate();
				stmtUpdate.close();
			}
			rs.close();
			stmtCheck.close();
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmtCheck != null)
					stmtCheck.close();
				if(stmtUpdate != null)
					stmtUpdate.close();
			} catch(SQLException e){}
		}
	}
	
	public void updatePrice(Rent rent) throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		try{
			String tableName = getTableName(rent.getDownPayment());
			String s = "UPDATE " + tableName + " SET Price = ?"
					+ "\nWHERE MLS_ID = ?  AND Beds = ? AND Baths = ? AND Attached = ?;";
			stmt = conn.prepareStatement(s);
			stmt.setDouble(1, rent.getPrice());
			stmt.setInt(2, rent.getArea());
			stmt.setInt(3, rent.getBeds());
			stmt.setInt(4, rent.getBaths());
			stmt.setBoolean(5, rent.isAttached());
			stmt.executeUpdate();
			stmt.close();
		} finally {
			try{
				if(stmt != null)
					stmt.close();
			} catch(SQLException e) {}
		}
	}
	
	public void createBuy(Buy buy) throws ClassNotFoundException, SQLException{
		PreparedStatement stmtCheck = null;
		PreparedStatement stmtUpdate = null;
		ResultSet rs = null;
		try{
			String tableName = getTableName(buy.getDownPayment());
			String s = "SELECT * FROM " + tableName
					+ "\nWHERE MLS_ID = ? AND Beds = ? AND Baths = ? AND Attached = ?;";
			stmtCheck = conn.prepareStatement(s);
			stmtCheck.setInt(1, buy.getArea());
			stmtCheck.setInt(2, buy.getBeds());
			stmtCheck.setInt(3, buy.getBaths());
			stmtCheck.setBoolean(4, buy.isAttached());		
			rs = stmtCheck.executeQuery();
			if(!rs.first()){
				s =  "INSERT INTO " + tableName
						+ " (MLS_ID, Beds, Baths, Attached, Price)"
						+ " VALUES (?,?,?,?,?);";
				stmtUpdate = conn.prepareStatement(s);
				stmtUpdate.setInt(1, buy.getArea());
				stmtUpdate.setInt(2, buy.getBeds());
				stmtUpdate.setInt(3, buy.getBaths());
				stmtUpdate.setBoolean(4, buy.isAttached());
				stmtUpdate.setDouble(5, buy.getPrice());
				stmtUpdate.executeUpdate();
				stmtUpdate.close();
			}
			rs.close();
			stmtCheck.close();
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmtCheck != null)
					stmtCheck.close();
				if(stmtUpdate != null)
					stmtUpdate.close();
			} catch(SQLException e){}
		}
	}
	
	public HashMap<Integer,Area> getAllAreas() throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String s = "SELECT Cities.MLS_ID, Cities.City, Zip_Code.zipcode  FROM Cities, Zip_Code\n"
					+ "WHERE Cities.MLS_ID = Zip_Code.MLS_ID;";
			stmt = conn.prepareStatement(s);
			HashMap<Integer,Area> areas = new HashMap<Integer,Area>();
			rs = stmt.executeQuery();
			if(rs.isClosed()){
				throw new SQLException("what the fuck");
			}
			while(rs.next()){
				HashSet<Integer> zips = new HashSet<Integer>();
				zips.add(rs.getInt("Zip_Code.zipcode"));
				Area a = AreaFactory.makeArea(rs.getInt("Cities.MLS_ID"), rs.getString("Cities.City"), zips, this);
				areas.put(a.getArea(), a);
			}
			rs.close();
			stmt.close();
			return areas;
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();
				
					
			} catch(SQLException e) {}
		}
	}
	
	public ArrayList<Rent> getAllRents() throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String s = "SELECT * FROM Rent;";
			stmt = conn.prepareStatement(s);
			rs = stmt.executeQuery();
			ArrayList<Rent> rents = new ArrayList<Rent>();
			while(rs.next()){
				Rent r = PropertyFactory.makeRent(rs.getInt("MLS_ID"), rs.getInt("Beds"), rs.getInt("Baths"), rs.getBoolean("Attached"), rs.getDouble("Price"), this);
				rents.add(r);
			}
			rs.close();
			stmt.close();
			
			return rents;
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();
			} catch(SQLException e) {}
		}
	}
	
	public ArrayList<Buy> getAllBuys() throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Buy> buys = new ArrayList<Buy>();
		try{
			for(DownPayTypes dpt : DownPayTypes.values()){
				double dp = dpt.getDouble();
				String tableName = getTableName(dp);
				
				String s = "SELECT * FROM "
						+ tableName + ";";
				stmt = conn.prepareStatement(s);
				rs = stmt.executeQuery();
				while(rs.next()){
					Buy b = PropertyFactory.makeBuy(rs.getInt("MLS_ID"), rs.getInt("Beds"), rs.getInt("Baths"), rs.getBoolean("Attached"), dp, rs.getDouble("Price"), this);
					buys.add(b);
				}
				rs.close();
				stmt.close();
			}
			return buys;
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();
			} catch(SQLException e) {}
		}
	}
	
	public ArrayList<Buy> getAllBuysAtDP(double dp) throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String tableName = getTableName(dp);
			String s = "SELECT * FROM " + tableName + ";";
			stmt = conn.prepareStatement(s);
			rs = stmt.executeQuery();
			ArrayList<Buy> buys = new ArrayList<Buy>();
			while(rs.next()){
				Buy b = PropertyFactory.makeBuy(rs.getInt("MLS_ID"), rs.getInt("Beds"), rs.getInt("Baths"), rs.getBoolean("Attached"), dp, rs.getDouble("Price"), this);
				buys.add(b);
			}
			rs.close();
			stmt.close();
			
			return buys;
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();
			} catch(SQLException e) {}
		}
	}
	
	public boolean isArea(int area) throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String s = "SELECT Cities.MLS_ID, Cities.City, Zip_Code.zipcode  FROM Cities, Zip_Code\n"
					+ "WHERE Cities.MLS_ID = ?  AND Zip_Code.MLS_ID = ?;";
			stmt = conn.prepareStatement(s);
			rs = stmt.executeQuery();
			if(rs.first()){
				rs.close();
				stmt.close();
				
				return true;
			}
			else{
				rs.close();
				stmt.close();
				return false;
			}
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();
			} catch(SQLException e) {}
		}
	}
	
	public Area getArea(int area) throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String s = "SELECT Cities.MLS_ID, Cities.City, Zip_Code.zipcode  FROM Cities, Zip_Code\n"
					+ "WHERE Cities.MLS_ID = ?  AND Zip_Code.MLS_ID = ?;";
			stmt = conn.prepareStatement(s);
			stmt.setInt(1, area);
			stmt.setInt(2, area);
			HashMap<Integer,Area> areas = new HashMap<Integer,Area>();
			rs = stmt.executeQuery();
			if(rs.isClosed()){
				throw new SQLException("what the fuck");
			}
			while(rs.next()){
				HashSet<Integer> zips = new HashSet<Integer>();
				zips.add(rs.getInt("Zip_Code.zipcode"));
				Area a = AreaFactory.makeArea(rs.getInt("Cities.MLS_ID"), rs.getString("Cities.City"), zips, this);
				areas.put(a.getArea(), a);
			}
			rs.close();
			Area a = areas.get(area);
			stmt.close();
			return a;
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();
			} catch(SQLException e) {}
		}
	}
	
	public Rent getRent(int area, int beds, int baths, boolean isAtt) throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String s = "SELECT * FROM Rent\nWHERE MLS_ID = ? AND Beds = ?  AND Baths = ? AND Attached = ?;";
			stmt = conn.prepareStatement(s);
			stmt.setInt(1, area);
			stmt.setInt(2, beds);
			stmt.setInt(3, baths);
			stmt.setBoolean(4, isAtt);
			rs = stmt.executeQuery();
			Rent r = null;
			if(rs.first()){
				r = PropertyFactory.makeRent(rs.getInt("MLS_ID"), rs.getInt("Beds"), rs.getInt("Baths"), rs.getBoolean("Attached"), rs.getDouble("Price"), this);
			}
			rs.close();
			stmt.close();
			return r;		
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();
			} catch(SQLException e) {}
		}
	}
	
	public ArrayList<Rent> getAreaRents(int area) throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String s = "SELECT * FROM Rent\nWHERE MLS_ID = ?;";
			stmt = conn.prepareStatement(s);
			stmt.setInt(1, area);
			rs = stmt.executeQuery();
			ArrayList<Rent> rents = new ArrayList<Rent>();
			if(rs.first()){
				Rent r = PropertyFactory.makeRent(rs.getInt("MLS_ID"), rs.getInt("Beds"), rs.getInt("Baths"), rs.getBoolean("Attached"), rs.getDouble("Price"), this);
				rents.add(r);
			}
			rs.close();
			stmt.close();
			return rents;		
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();
			} catch(SQLException e) {}
		}
	}
	
	public Buy getBuy(int area, int beds, int baths, boolean isAtt, double dp) throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String tableName = getTableName(dp);
		try{
			String s = "SELECT * FROM " + tableName 
					+ "\nWHERE MLS_ID = ? AND Beds = ? AND Baths = ? AND Attached = ?;";
			stmt = conn.prepareStatement(s);
			stmt.setInt(1, area);
			stmt.setInt(2, beds);
			stmt.setInt(3, baths);
			stmt.setBoolean(4, isAtt);
			rs = stmt.executeQuery();
			Buy b = null;
			if(rs.first()){
				b = PropertyFactory.makeBuy(rs.getInt("MLS_ID"), rs.getInt("Beds"), rs.getInt("Baths"), rs.getBoolean("Attached"), dp, rs.getDouble("Price"), this);
			}
			rs.close();
			stmt.close();
			return b;		
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();	
			} catch(SQLException e) {}
		}
	}
	
	public ArrayList<Buy> getAreaBuys(int area) throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Buy> buys = new ArrayList<Buy>();
		try{
			for(DownPayTypes dpt : DownPayTypes.values()){
				double dp = dpt.getDouble();
				String tableName = getTableName(dp);
				
				String s = "SELECT * FROM " + tableName + "\nWHERE MLS_ID = ?;";
				stmt = conn.prepareStatement(s);
				stmt.setInt(1, area);
				rs = stmt.executeQuery();
				if(rs.first()){
					Buy b = PropertyFactory.makeBuy(rs.getInt("MLS_ID"), rs.getInt("Beds"), rs.getInt("Baths"), rs.getBoolean("Attached"), dp, rs.getDouble("Price"), this);
					buys.add(b);
				}
				rs.close();
				stmt.close();
			}
			return buys;		
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();	
			} catch(SQLException e) {}
		}
	}
	
	public double getPrice(int area, int beds, int baths, boolean isAtt, double dp) throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String tableName = getTableName(dp);
		try{
			String s = "SELECT Price FROM " + tableName
					+ "\nWHERE MLS_ID = ? AND Beds = ?  AND Beds = ? AND Attached = ?;";
			stmt = conn.prepareStatement(s);
			stmt.setInt(1, area);
			stmt.setInt(2, beds);
			stmt.setInt(3, baths);
			stmt.setBoolean(4, isAtt);
			rs = stmt.executeQuery();
			if(!rs.first()){
				rs.close();
				stmt.close();
				return 0;
			}
			else{
				double p = rs.getDouble("Price");
				rs.close();
				stmt.close();
				return p;
			}		
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();	
			} catch(SQLException e) {}
		}
	}
	
	public double[] getAllPrices(int area, int beds, boolean isAtt, double dp) throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String tableName = getTableName(dp);
		try{
			String s = "SELECT Price FROM " + tableName
					+ "\nWHERE MLS_ID = ? AND Beds = ?  AND Beds = ? AND Attached = ?;";
			stmt = conn.prepareStatement(s);
			stmt.setInt(1, area);
			stmt.setInt(2, beds);
			stmt.setBoolean(3, isAtt);
			rs = stmt.executeQuery();
			double[] prices = new double[3];
			for(int i = 0; i < BedsAndBaths.MAX_BATHS.getInt(); i++){
				s = "SELECT Price FROM" 
						+ tableName + "\nWHERE MLS_ID = ? AND Beds = ?  AND Beds = ? AND Attached = ?;";
				PreparedStatement stmt2 = conn.prepareStatement(s);
				stmt2.setInt(1, area);
				stmt2.setInt(2, beds);
				stmt2.setInt(3, i);
				stmt2.setBoolean(4, isAtt);
				ResultSet rs2 = stmt.executeQuery();
				double p = 0;
				if(rs2.first()){
					p = rs2.getDouble("Price");
				}
				rs2.close();
				stmt2.close();
				prices[i] = p;
			}
			rs.close();
			stmt.close();
			return prices;
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();	
			} catch(SQLException e) {}
		}
	}
	
	private String getTableName(double dp){
		String tableName = "";
		if(dp == 0){
			tableName = "Rent";
		}
		else if(dp == DownPayTypes.DP5Percent.getDouble()){
			tableName = "Five_Per";
		}
		else if(dp == DownPayTypes.DP10Percent.getDouble()){
			tableName = "Ten_Per";
		}
		else{
			tableName = "Twenty_Per";
		}
		return tableName;
	}
	
	public void close(){
		try {
			conn.close();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}
}