package mls.area;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import db.DBStatement;

public class Area {
	//TODO date created, date modified
	private final int area;
	private String city;
	private HashSet<Integer> zips = new HashSet<Integer>();
	private DBStatement stmt;
	
	Area(int area) throws ClassNotFoundException, SQLException{
		this.area = area;
		stmt = new DBStatement();
	}
	
	boolean addZips(HashSet<Integer> z) throws ClassNotFoundException, SQLException{
		boolean result = zips.addAll(z);
		stmt.updateAreaZips(this);
		return result;
	}
	
	public HashSet<Integer> getZips(){
		return new HashSet<Integer>(zips);
	}
	
	void setCity(String c) throws ClassNotFoundException, SQLException{
		city = c;
		if(city == null){
			stmt.createArea(this);
		}
		else{
			stmt.updateAreaCity(this);
		}
	}
	
	public String getCity(){
		return city;
	}
	
	public int getArea(){
		return area;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("Area No.: ");
		s.append(area);
		s.append(";  City: ");
		s.append(city);
		s.append("; Zips: ");
		s.append(zips.toString());
		return s.toString();
	}
}
