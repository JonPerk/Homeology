package mls.property;

import java.sql.SQLException;

import db.DBConnection;
import mls.area.Area;

public class Buy extends Rent{
	private final double downPayment;
	
	Buy(int area, int beds, int baths, boolean attached, double downPayment) throws ClassNotFoundException, SQLException {
		super(area, beds, baths, attached);
		this.downPayment = downPayment;
	}
	
	@Override
	public double getDownPayment(){
		return downPayment;
	}
	
	@Override
	public boolean isRent(){
		return false;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder(super.toString());
		s.append(" at ");
		s.append(downPayment * 100);
		s.append("% Down Payment");
		return s.toString();
	}
}
