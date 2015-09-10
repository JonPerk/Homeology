package mls.area;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import db.DBConnection;

public class Area {
	//TODO date created, date modified
	private final int area;
	private HashSet<String> cities = new HashSet<String>();
	private HashSet<Integer> zips = new HashSet<Integer>();
	
	Area(int area) throws ClassNotFoundException, SQLException{
		this.area = area;
	}
	
	void addZips(HashSet<Integer> z, DBConnection conn) throws ClassNotFoundException, SQLException{
		for(int zip : z){
			zips.add(zip);
		}
		conn.updateAreaZips(this);
	}
	
	public HashSet<Integer> getZips(){
		return new HashSet<Integer>(zips);
	}
	
	void addCity(String c, DBConnection conn) throws ClassNotFoundException, SQLException{
		cities.add(c);
		conn.updateAreaCity(this);
	}
	
	public HashSet<String> getCities(){
		return new HashSet<String>(cities);
	}
	
	public int getArea(){
		return area;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("Area No.: ");
		s.append(area);
		s.append(";  City: ");
		for(String city : cities){
			s.append(city);
			s.append(" ");
		}
		s.append("; Zips: ");
		s.append(zips.toString());
		return s.toString();
	}
}
