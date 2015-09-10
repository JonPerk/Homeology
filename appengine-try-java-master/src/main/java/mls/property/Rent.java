package mls.property;

import java.sql.SQLException;

import constants.BedsAndBaths;
import db.DBConnection;
import mls.area.Area;

public class Rent {
	//TODO date created, date modified
	private final int area;
	private final int beds;
	private final int baths;
	private final boolean attached;
	private final double minPrice = 0;
	private final double maxPrice = 30000;
	private double price;
	
	Rent(int area, int beds, int baths, boolean attached) throws ClassNotFoundException, SQLException{
		this.area = area;
		this.beds = beds;
		this.baths = baths;
		this.attached = attached;
		this.price = 0;
	}
	
	public void setPrice(double price, DBConnection conn) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		if(price < minPrice || price > maxPrice)
			throw new IllegalArgumentException("Invalid price: " + price + ". Price must be in range: " + minPrice + "-" + maxPrice);
		this.price = price;
		conn.updatePrice(this);
	}
	
	
	
	public int getArea() {
		return area;
	}

	public int getBeds() {
		return beds;
	}
	
	public int getBaths(){
		return baths;
	}
	
	public boolean isAttached() {
		return attached;
	}

	public double getMinPrice() {
		return minPrice;
	}
	
	public double getMaxPrice() {
		return maxPrice;
	}
	
	public double getPrice() {
		return price;
	}
	
	public double getDownPayment(){
		return 0;
	}
	
	public boolean isRent(){
		return true;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder(area);
		if(attached)
			s.append(", Att/Det: Attached, Beds: ");
		else
			s.append(", Att/Det: Detached, Beds: ");
		s.append(beds);
		s.append(", Baths: ");
		s.append(baths);
		s.append(", Price: ");
		s.append(price);
		return s.toString();
	}
}
