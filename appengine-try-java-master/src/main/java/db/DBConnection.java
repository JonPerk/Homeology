package db;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
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
import constants.FormDBFields;
import constants.UserObjectFields;
import constants.Zones;
import form.User;

public class DBConnection {
	private String url;
	private static final Logger log = Logger.getLogger(DBConnection.class.getName());
	private volatile Connection conn;
	
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
	
	public synchronized void createUser(User user) throws SQLException{
		PreparedStatement stmtCheck = null;
		PreparedStatement stmtUpdate = null;
		PreparedStatement stmtCheck2 = null;
		PreparedStatement stmtUpdate2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try{
			String sc = "SELECT * FROM Cust_Data\nWHERE Id = ?;";
			stmtCheck = conn.prepareStatement(sc);
			byte[] id = user.getId();
			stmtCheck.setBytes(1, id);
			rs = stmtCheck.executeQuery();
			if(!rs.first()){
				StringBuilder insert = new StringBuilder("INSERT INTO Cust_Data (");
				StringBuilder vals = new StringBuilder(") VALUES (");
				HashMap<Object, Integer> values = new HashMap<Object, Integer>();
				ArrayList<Object> objs = new ArrayList<Object>();
				for(UserObjectFields uof : UserObjectFields.values()){
					Method m;
					try {
						m = User.class.getMethod("get" + uof.toObjectField());
						Object o = m.invoke(user);
						if(uof.toDBClass() != Types.ARRAY){
							insert.append(uof.toDBField());
							insert.append(",");
							vals.append("?,");
						}
						objs.add(o);
						values.put(o, uof.toDBClass());
					} catch (NoSuchMethodException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					} catch (SecurityException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					} catch (IllegalAccessException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					} catch (IllegalArgumentException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					} catch (InvocationTargetException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
				}
				insert.setLength(insert.length()-1);
				vals.setLength(vals.length()-1);
				vals.append(");");
				String s = insert.toString() + vals.toString();
				stmtUpdate = conn.prepareStatement(s);
				int count = 1;
				for(Object o : objs){
					if(values.get(o) != Types.ARRAY){
						stmtUpdate.setObject(count, o, values.get(o));
						count++;
					}
					else{
						ArrayList<Integer> arr = (ArrayList<Integer>) o;
						String sql = "SELECT * FROM Matched_Areas\nWHERE Cust_Id = ? AND MLS_ID = ?";
						stmtCheck2 = conn.prepareStatement(sql);
						stmtCheck2.setBytes(1, id);
						String sql2 = "INSERT INTO Matched_Areas (Cust_Id,MLS_ID) VALUES (?,?);";
						stmtUpdate2 = conn.prepareStatement(sql2);
						stmtUpdate2.setBytes(1, id);
						for(int i : arr){
							stmtCheck2.setInt(2, i);
							rs2 = stmtCheck2.executeQuery();
							if(!rs2.first()){
								stmtUpdate2.setInt(2,i);
								stmtUpdate2.executeUpdate();
							}
							rs2.close();
						}
					}
				}
				int i = stmtUpdate.executeUpdate();
				if(rs != null)
					rs.close();
				if(rs2 != null)
					rs2.close();
				if(stmtCheck != null)
					stmtCheck.close();
				if(stmtCheck2 != null)
					stmtCheck2.close();
				if(stmtUpdate != null)
					stmtUpdate.close();
				if(stmtUpdate2 != null)
					stmtUpdate2.close();
			}
			if(rs != null)
				rs.close();
			if(rs2 != null)
				rs2.close();
			if(stmtCheck != null)
				stmtCheck.close();
			if(stmtCheck2 != null)
				stmtCheck2.close();
			if(stmtUpdate != null)
				stmtUpdate.close();
			if(stmtUpdate2 != null)
				stmtUpdate2.close();
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(rs2 != null)
					rs2.close();
				if(stmtCheck != null)
					stmtCheck.close();
				if(stmtCheck2 != null)
					stmtCheck2.close();
				if(stmtUpdate != null)
					stmtUpdate.close();
				if(stmtUpdate2 != null)
					stmtUpdate2.close();
			} catch(SQLException e){}
		}
	}
	
	public User getUser(byte[] id) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String s = "SELECT * FROM Cust_Data, Matched_Areas\nWHERE Cust_Data.Id = ? AND Matched_Areas.Cust_Id = ?;";
			stmt = conn.prepareStatement(s);
			stmt.setBytes(1, id);
			stmt.setBytes(2, id);
			rs = stmt.executeQuery();
			User u = null;
			if(rs.first()){
				HashMap<String,Object> results = new HashMap<String,Object>();
				ArrayList<Integer> matchedAreas = new ArrayList<Integer>();
				while(rs.next()){
					if(rs.first()){
						for(UserObjectFields uof : UserObjectFields.values()){
							if(!uof.equals(UserObjectFields.MATCHED_AREAS))
								results.put(uof.toObjectField(), rs.getObject(uof.toDBField()));
						}
					}
					rs.getInt(rs.getInt("MLS_ID"));
				}
				results.put(UserObjectFields.MATCHED_AREAS.toFormField(), matchedAreas);
				u = new User(results);
			}
			rs.close();
			stmt.close();
			return u;		
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();
			} catch(SQLException e) {}
		}
	}
	
	public User getUser(String email) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String s = "SELECT * FROM Cust_Data, Matched_Areas\nWHERE Cust_Data.Email = ? AND Matched_Areas.Cust_Id = Cust_Data.Id;";
			stmt = conn.prepareStatement(s);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			User u = null;
			if(rs.first()){
				HashMap<String,Object> results = new HashMap<String,Object>();
				ArrayList<Integer> matchedAreas = new ArrayList<Integer>();
				while(rs.next()){
					if(rs.first()){
						for(UserObjectFields uof : UserObjectFields.values()){
							if(!uof.equals(UserObjectFields.MATCHED_AREAS))
								results.put(uof.toObjectField(), rs.getObject(uof.toDBField()));
						}
					}
					rs.getInt(rs.getInt("MLS_ID"));
				}
				results.put(UserObjectFields.MATCHED_AREAS.toFormField(), matchedAreas);
				u = new User(results);
			}
			rs.close();
			stmt.close();
			return u;		
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();
			} catch(SQLException e) {}
		}
	}
	
	public synchronized ArrayList<User> getUsers() throws SQLException{
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try{
			String s = "SELECT * FROM Cust_Data;";
			stmt = conn.prepareStatement(s);
			rs = stmt.executeQuery();
			String s2 = "SELECT * FROM Matched_Areas\nWHERE Cust_Id = ?;";
			stmt2 = conn.prepareStatement(s2);
			ArrayList<User> users = new ArrayList<User>();
			while(rs.next()){
				HashMap<String,Object> results = new HashMap<String,Object>();
				ArrayList<Integer> matchedAreas = new ArrayList<Integer>();
				for(UserObjectFields uof : UserObjectFields.values()){
					if(!uof.equals(UserObjectFields.MATCHED_AREAS))
						results.put(uof.toObjectField(), rs.getObject(uof.toDBField()));
				}
				stmt2.setBytes(1, rs.getBytes(UserObjectFields.ID.toDBField()));
				rs2 = stmt2.executeQuery();
				while(rs2.next()){
					matchedAreas.add(rs.getInt("MLS_ID"));
				}
				results.put(UserObjectFields.MATCHED_AREAS.toFormField(), matchedAreas);
				users.add(new User(results));
			}
			if(rs != null)
				rs.close();
			if(rs2 != null)
				rs2.close();
			if(stmt != null)
				stmt.close();
			if(stmt2 != null)
				stmt2.close();
			return users;		
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(rs2 != null)
					rs2.close();
				if(stmt != null)
					stmt.close();
				if(stmt2 != null)
					stmt2.close();
			} catch(SQLException e) {}
		}
	}
	
	public synchronized void updateMatchedAreas(byte[] id, ArrayList<Integer> areas) throws SQLException{
		PreparedStatement stmtCheck = null;
		PreparedStatement stmtUpdate = null;
		try{
			String s = "DELETE FROM Matched_Areas\nWHERE Cust_Id = ?;";
			stmtCheck = conn.prepareStatement(s);
			stmtCheck.setBytes(1, id);
			stmtCheck.executeUpdate();
			s = "INSERT INTO Matched_Areas (Cust_Id, MLS_ID) VALUES (?,?);";
			stmtUpdate = conn.prepareStatement(s);
			stmtUpdate.setBytes(1, id);
			for(int a : areas){
				stmtUpdate.setInt(2, a);
				stmtUpdate.executeUpdate();
			}
			stmtCheck.close();
			stmtUpdate.close();
		} finally {
			try{
				if(stmtCheck != null)
					stmtCheck.close();
				if(stmtUpdate != null)
					stmtUpdate.close();
			} catch(SQLException e){}
		}
	}
	
	public synchronized void createArea(Area area) throws ClassNotFoundException, SQLException{
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
	
	public synchronized void createRent(Rent rent) throws ClassNotFoundException, SQLException{
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
	
	public synchronized void createCounty(String city, String county) throws SQLException{
		PreparedStatement stmtCheck = null;
		PreparedStatement stmtUpdate = null;
		ResultSet rs = null;
		try{
			String s = "SELECT * FROM Counties"
					+ "\nWHERE City = ?;";
			stmtCheck = conn.prepareStatement(s);
			stmtCheck.setString(1, city);	
			rs = stmtCheck.executeQuery();
			if(!rs.first()){
				s = "INSERT INTO Counties (City, County)"
						+ " VALUES (?,?);";
				stmtUpdate = conn.prepareStatement(s);
				stmtUpdate.setString(1, city);
				stmtUpdate.setString(2, county);
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
	public synchronized void createBuy(Buy buy) throws ClassNotFoundException, SQLException{
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
	
	public ArrayList<Rent> getAllRents(int beds, int baths) throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String s = "SELECT * FROM Rent\nWHERE Beds = ? AND Baths = ?;";
			stmt = conn.prepareStatement(s);
			stmt.setInt(1, beds);
			stmt.setInt(2, baths);
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
	
	public ArrayList<Buy> getAllBuysAtDP(int beds, int baths, double dp) throws ClassNotFoundException, SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String tableName = getTableName(dp);
			String s = "SELECT * FROM " + tableName + "\nWHERE Beds = ? AND Baths = ?;";
			stmt = conn.prepareStatement(s);
			stmt.setInt(1, beds);
			stmt.setInt(2, baths);
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
	
	public int getZipZone(int zip) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String s = "SELECT County FROM Zip_Code, Cities, Counties\nWHERE Zip_Code.zipcode = ? AND Zip_Code.MLS_ID = Cities.MLS_ID AND Cities.City = Counties.City;";
			stmt = conn.prepareStatement(s);
			stmt.setInt(1, zip);
			rs = stmt.executeQuery();
			String county = null;
			int zone = -1;
			if(rs.first()){
				county = rs.getString("County");
			}
			if(county != null)
				zone = Zones.valueOf(county.replace(" ", "")).getZone();
			rs.close();
			stmt.close();
			return zone;
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