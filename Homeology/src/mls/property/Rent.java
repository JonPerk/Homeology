package mls.property;

import constants.BedsAndBaths;
import mls.area.Area;

public class Rent {
	private final Area area;
	private final int beds;
	private final boolean attached;
	private final double minPrice = 0;
	private final double maxPrice = 30000;
	private double price1Bath;
	private double price2Bath;
	private double price3Bath;
	
	
	Rent(Area area, int beds, boolean attached){
		this.area = area;
		this.beds = beds;
		this.attached = attached;
	}
	
	public void setPrice1Bath(double price) throws IllegalArgumentException{
		if(price < minPrice || price > maxPrice)
			throw new IllegalArgumentException("Invalid price: " + price + ". Price must be in range: " + minPrice + "-" + maxPrice);
		price1Bath = price;
	}
	
	public void setPrice2Bath(double price) throws IllegalArgumentException{
		if(price < minPrice || price > maxPrice)
			throw new IllegalArgumentException("Invalid price: " + price + ". Price must be in range: " + minPrice + "-" + maxPrice);
		price2Bath = price;
	}
	
	public void setPrice3Bath(double price) throws IllegalArgumentException{
		if(price < minPrice || price > maxPrice)
			throw new IllegalArgumentException("Invalid price: " + price + ". Price must be in range: " + minPrice + "-" + maxPrice);
		price3Bath = price;
	}
	
	public void setAllPriceBath(double[] prices) throws IllegalArgumentException {
		if(prices.length != BedsAndBaths.MAX_BATHS.getInt()){
			throw new IllegalArgumentException("Invalid array length: " + prices.length + " must be" + BedsAndBaths.MAX_BATHS.getInt());
		}
		setPrice1Bath(prices[0]);
		setPrice2Bath(prices[1]);
		setPrice3Bath(prices[2]);
	}
	
	public Area getArea() {
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
	
	public double getPrice1Bath() {
		return price1Bath;
	}

	public double getPrice2Bath() {
		return price2Bath;
	}

	public double getPrice3Bath() {
		return price3Bath;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder(area.toString());
		if(attached)
			s.append("; Att/Det: Attached ");
		else
			s.append("; Att/Det: Detached ");
		s.append("; Price 1 Bath: ");
		s.append(price1Bath);
		s.append("; Price 2 Bath: ");
		s.append(price2Bath);
		s.append("; Price 3 Bath: ");
		s.append(price3Bath);
		return s.toString();
	}
}
