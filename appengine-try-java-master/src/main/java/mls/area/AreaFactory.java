package mls.area;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import db.DBConnection;

public class AreaFactory {
	private static ArrayList<Integer> areas = new ArrayList<Integer>();
	private static final int min = 1;
	private static final int max = 9999;
	/*static{
		try {
			conn = new DBConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public synchronized static Area makeArea(int area, DBConnection conn) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		if(area < min || area > max)
			throw new IllegalArgumentException("Area number out of range");
		if(areas.contains(area))
			return conn.getArea(areas.get(area));
		else{
			Area a = new Area(area);
			conn.createArea(a);
			return a;
		}
	}
	
	public synchronized static Area makeArea(int area, String city, DBConnection conn) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		Area a = makeArea(area, conn);
		if(!city.trim().matches("[A-Za-z .]+"))
			throw new IllegalArgumentException("City name invalid");
		else {
			a.addCity(city, conn);
		}
		return a;
	}
	
	public synchronized static Area makeArea(int area, String city, HashSet<Integer> zips, DBConnection conn) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		Area a = makeArea(area, city, conn);
		a.addZips(zips, conn);
		return a;
	}
	
	public synchronized static Area makeArea(int area, HashSet<Integer> zips, DBConnection conn) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		Area a = makeArea(area, conn);
		a.addZips(zips, conn);
		return a;
	}
	
	public synchronized static HashMap<Integer,Area> getAllAreas(DBConnection conn) throws ClassNotFoundException, SQLException{
		return conn.getAllAreas();
	}
}
