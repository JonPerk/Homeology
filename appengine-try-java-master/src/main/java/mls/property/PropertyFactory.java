package mls.property;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import constants.BedsAndBaths;
import constants.DownPayTypes;
import db.DBConnection;
import mls.area.Area;
import mls.area.AreaFactory;

public class PropertyFactory {
	//private static DBConnection conn;
	private static HashMap<Long, Rent> rents = new HashMap<Long, Rent>();
	private static HashMap<Long, Buy> buys = new HashMap<Long, Buy>();
	private static final double minPrice = 0;
	private static final double maxPrice = 30000;
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
	
	public static Rent makeRent(int area, int beds, int baths, boolean attached, double price, DBConnection conn) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		if(price < minPrice || price > maxPrice)
			throw new IllegalArgumentException("Invalid price: " + price + ". Price must be in range: " + minPrice + "-" + maxPrice);
		Rent r = makeRent(area, beds, baths, attached, conn);
		r.setPrice(price, conn);
		return r;
	}
	
	public static Rent makeRent(int area, int beds, int baths, boolean attached, DBConnection conn) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		if(beds < BedsAndBaths.MIN_BEDS.getInt() || beds > BedsAndBaths.MAX_BEDS.getInt())
			throw new IllegalArgumentException("Invalid number of beds " + beds);
		if(baths < BedsAndBaths.MIN_BATHS.getInt() || baths > BedsAndBaths.MAX_BATHS.getInt())
			throw new IllegalArgumentException("Invalid number of baths " + baths);
		long id = getRentID(area, beds, baths, attached);
		if(rents.containsKey(id)){
			return rents.get(id);
		}
		Rent r = new Rent(area, beds, baths, attached);
		conn.createRent(r);
		r.setPrice(conn.getPrice(area, beds, baths, attached, r.getDownPayment()), conn);
		rents.put(id, r);
		return r;
	}
	
	public static Buy makeBuy(int area, int beds, int baths, boolean attached, double downPayment, double price, DBConnection conn) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		if(price < minPrice || price > maxPrice)
			throw new IllegalArgumentException("Invalid price: " + price + ". Price must be in range: " + minPrice + "-" + maxPrice);
		Buy b = makeBuy(area, beds, baths, attached, downPayment, conn);
		b.setPrice(price, conn);
		return b;
	}
	
	public static Buy makeBuy(int area, int beds, int baths, boolean attached, double downPayment, DBConnection conn) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		if(beds < BedsAndBaths.MIN_BEDS.getInt() || beds > BedsAndBaths.MAX_BEDS.getInt())
			throw new IllegalArgumentException("Invalid number of beds " + beds);
		if(baths < BedsAndBaths.MIN_BATHS.getInt() || baths > BedsAndBaths.MAX_BATHS.getInt())
			throw new IllegalArgumentException("Invalid number of baths " + baths);
		if(!DownPayTypes.isValid(downPayment))
			throw new IllegalArgumentException("Invalid down payment value " + downPayment);
		long id = getBuyID(area, beds, baths, attached, downPayment);
		if(buys.containsKey(id)){
			return buys.get(id);
		}
		Buy b = new Buy(area, beds, baths, attached, downPayment);
		conn.createBuy(b);
		b.setPrice(conn.getPrice(area, beds, baths, attached, downPayment), conn);
		buys.put(id, b);
		return b;
	}
	
	private static long getRentID(int area, int beds, int baths, boolean attached){
		return getID(area, beds, baths, attached, 0);
	}
	
	private static long getBuyID(int area, int beds, int baths, boolean attached, double downPayment){
		downPayment *= 100;
		long dp = (long) downPayment;
		return getID(area, beds, baths, attached, dp);
	}
	
	private static long getID(int area, int beds, int baths, boolean attached, long dp){
		long id = dp;
		if(attached){
			id += 100;
		}
		id += baths * 1000;
		id += beds * 10000;
		id += area * 100000;
		return id;
	}
}
