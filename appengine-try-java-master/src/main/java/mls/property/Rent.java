package mls.property;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import constants.BedsAndBaths;
import db.DBConnection;
import mls.ImportMLS;
import mls.area.Area;

public class Rent {
	private static final Logger log = Logger.getLogger(Rent.class.getName());
	private final int area;
	private final int beds;
	private final int baths;
	private final boolean attached;
	private final double minPrice = 0;
	private final double maxPrice = 30000;
	private double price;
	private int lastCount;
	private double price1MonthOld;
	private double price2MonthOld;
	private Date lastUpdateMonth;
	
	Rent(int area, int beds, int baths, boolean attached) throws ClassNotFoundException, SQLException{
		this.area = area;
		this.beds = beds;
		this.baths = baths;
		this.attached = attached;
		this.price = 0;
		lastCount = 0;
		price1MonthOld = 0;
		price2MonthOld = 0;
		lastUpdateMonth = new Date();
	}
	
	public synchronized void setPrice(double price, int count, Date newDate, DBConnection conn) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		if(price < minPrice || price > maxPrice || price == Double.NaN)
			throw new IllegalArgumentException("Invalid price: " + price + ". Price must be in range: " + minPrice + "-" + maxPrice);
		if(price == 0)
			return;
		Calendar cal = Calendar.getInstance();
		cal.setTime(newDate);
		int newYear = cal.get(Calendar.YEAR);
		int newMon = cal.get(Calendar.MONTH);
		cal.setTime(lastUpdateMonth);
		int oldYear = cal.get(Calendar.YEAR);
		int oldMon = cal.get(Calendar.MONTH);
		if(newYear > oldYear || newMon > oldMon){
			price2MonthOld = price1MonthOld;
			price1MonthOld = this.price;
			this.price = price;
			lastCount = count;
		}
		else{
			this.price = ((this.price * lastCount) + (price * count)) / (lastCount + count);
			lastCount = lastCount + count;
		}
		lastUpdateMonth = newDate;
		conn.updatePrice(this);
	}
	
	public void setDBPrices(PriceDTO prices){
		price = prices.price;
		price1MonthOld = prices.price1MonthOld;
		price2MonthOld = prices.price2MonthOld;
		lastCount = prices.lastCount;
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
	
	public int getLastCount() {
		return lastCount;
	}

	public double getPrice1MonthOld() {
		return price1MonthOld;
	}

	public double getPrice2MonthOld() {
		return price2MonthOld;
	}

	public Date getLastUpdateMonth() {
		return lastUpdateMonth;
	}

	public double getDownPayment(){
		return 0;
	}
	
	public boolean isRent(){
		return true;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder("Area: " + area);
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
