package mls.area;

import java.util.ArrayList;
import java.util.HashSet;

public class Area {
	private final int area;
	private String city;
	private HashSet<Integer> zips = new HashSet<Integer>();
	
	Area(int area){
		this.area = area;
	}
	
	boolean addZips(HashSet<Integer> z){
		return zips.addAll(z);
	}
	
	public HashSet<Integer> getZips(){
		return new HashSet<Integer>(zips);
	}
	
	boolean setCity(String c){
		if(city != null)
			return false;
		city = c;
		return true;
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
