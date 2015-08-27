package mls.area;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import db.DBStatement;

public class AreaFactory {
	private static HashMap<Integer,Area> areas = new HashMap<Integer,Area>();
	private static final int min = 1;
	private static final int max = 9999;
	private static DBStatement stmt = null;
	static{
		try {
			stmt = new DBStatement();
			stmt.getAllAreas();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Area makeArea(int area) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		if(areas.containsKey(area))
			return areas.get(area);
		if(area < min || area > max)
			throw new IllegalArgumentException("Area number out of range");
		Area a = new Area(area);
		areas.put(area, a);
		return a;
	}
	
	public static Area makeArea(int area, String city) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		Area a = makeArea(area);
		if(!city.trim().matches("[A-Za-z .]+"))
			throw new IllegalArgumentException("City name invalid");
		else if(a.getCity() == null)
			a.setCity(city);
		else if(!a.getCity().equals(city)){
			city = a.getCity() + ", " + city;
			a.setCity(city);
		}
		areas.put(area, a);
		return a;
	}
	
	public static Area makeArea(int area, String city, HashSet<Integer> zips) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		Area a = makeArea(area, city);
		a.addZips(zips);
		areas.put(area, a);
		return a;
	}
	
	public static Area makeArea(int area, HashSet<Integer> zips) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		Area a = makeArea(area);
		a.addZips(zips);
		areas.put(area, a);
		return a;
	}
	
	public static HashMap<Integer,Area> getAllAreas(){
		return new HashMap<Integer,Area>(areas);
	}
}
