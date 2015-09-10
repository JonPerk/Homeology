package mls.property;

import java.sql.SQLException;

import constants.BedsAndBaths;
import db.DBConnection;
import mls.area.Area;

public class TempBuy{
	private final int area;
	private final int beds;
	private final boolean attached;
	private final double minPrice = 0;
	private final double maxPrice = 30000;
	private double[] totalPrices;
	
	
	public TempBuy(int area, int beds, boolean attached, double[] totalPrices) throws ClassNotFoundException, SQLException{
		this.area = area;
		this.beds = beds;
		this.attached = attached;
		this.totalPrices = totalPrices;
	}
	
	
	
	public int getArea() {
		return area;
	}

	public int getBeds() {
		return beds;
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
	
	public String toString(){
		StringBuilder s = new StringBuilder(area);
		if(attached)
			s.append("; Att/Det: Attached ");
		else
			s.append("; Att/Det: Detached ");
		s.append("; Prices 1 Bath: ");
		s.append(totalPrices[0]);
		s.append("; Prices 2 Bath: ");
		s.append(totalPrices[1]);
		s.append("; Prices 3 Bath: ");
		s.append(totalPrices[2]);
		return s.toString();
	}



	public double[] getTotalPrices() {
		return totalPrices;
	}



	public void setTotalPrices(double[] totalPrices) {
		this.totalPrices = totalPrices;
	}

}
