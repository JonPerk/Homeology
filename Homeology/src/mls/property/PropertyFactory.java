package mls.property;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import constants.BedsAndBaths;
import constants.DownPayTypes;
import db.DBStatement;
import mls.area.Area;
import mls.area.AreaFactory;

public class PropertyFactory { 
	private static final ArrayList<HashMap<Area,Rent>> rentsAtt = new ArrayList<HashMap<Area,Rent>>(BedsAndBaths.MAX_BEDS.getInt());
	private static final ArrayList<HashMap<Area,Rent>> rentsDet = new ArrayList<HashMap<Area,Rent>>(BedsAndBaths.MAX_BEDS.getInt());
	private static final ArrayList<HashMap<Area,Buy>> buys5dpAtt = new ArrayList<HashMap<Area,Buy>>(BedsAndBaths.MAX_BEDS.getInt());
	private static final ArrayList<HashMap<Area,Buy>> buys10dpAtt = new ArrayList<HashMap<Area,Buy>>(BedsAndBaths.MAX_BEDS.getInt());
	private static final ArrayList<HashMap<Area,Buy>> buys20dpAtt = new ArrayList<HashMap<Area,Buy>>(BedsAndBaths.MAX_BEDS.getInt());
	private static final ArrayList<HashMap<Area,Buy>> buys5dpDet = new ArrayList<HashMap<Area,Buy>>(BedsAndBaths.MAX_BEDS.getInt());
	private static final ArrayList<HashMap<Area,Buy>> buys10dpDet = new ArrayList<HashMap<Area,Buy>>(BedsAndBaths.MAX_BEDS.getInt());
	private static final ArrayList<HashMap<Area,Buy>> buys20dpDet = new ArrayList<HashMap<Area,Buy>>(BedsAndBaths.MAX_BEDS.getInt());
	private static final HashMap<Double, ArrayList<HashMap<Area,Buy>>> downPaymentsAtt;
	private static final HashMap<Double, ArrayList<HashMap<Area,Buy>>> downPaymentsDet;
	private static DBStatement stmt = null;
	static{
		try {
			stmt = new DBStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		downPaymentsAtt = new HashMap<Double, ArrayList<HashMap<Area,Buy>>>();
		downPaymentsAtt.put(DownPayTypes.DP5Percent.getDouble(), buys5dpAtt);
		downPaymentsAtt.put(DownPayTypes.DP10Percent.getDouble(), buys10dpAtt);
		downPaymentsAtt.put(DownPayTypes.DP20Percent.getDouble(), buys20dpAtt);
		downPaymentsDet = new HashMap<Double, ArrayList<HashMap<Area,Buy>>>();
		downPaymentsDet.put(DownPayTypes.DP5Percent.getDouble(), buys5dpDet);
		downPaymentsDet.put(DownPayTypes.DP10Percent.getDouble(), buys10dpDet);
		downPaymentsDet.put(DownPayTypes.DP20Percent.getDouble(), buys20dpDet);
		for(int i = 0; i < BedsAndBaths.MAX_BEDS.getInt(); i++){
			rentsAtt.add(new HashMap<Area,Rent>());
			rentsDet.add(new HashMap<Area,Rent>());
			buys5dpAtt.add(new HashMap<Area,Buy>());
			buys10dpAtt.add(new HashMap<Area,Buy>());
			buys20dpAtt.add(new HashMap<Area,Buy>());
			buys5dpDet.add(new HashMap<Area,Buy>());
			buys10dpDet.add(new HashMap<Area,Buy>());
			buys20dpDet.add(new HashMap<Area,Buy>());
		}
	}
	
	public static Rent makeRent(int area, int beds, boolean attached) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		if(attached)
			return makeRentAtt(area, beds);
		else
			return makeRentDet(area, beds);
	}
	
	private static Rent makeRentAtt(int area, int beds) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		if(beds < BedsAndBaths.MIN_BEDS.getInt() || beds > BedsAndBaths.MAX_BEDS.getInt())
			throw new IllegalArgumentException("Invalid number of beds " + beds);
		Area a = AreaFactory.makeArea(area);
		if(rentsAtt.get(beds-1).containsKey(a))
			return rentsAtt.get(beds-1).get(a);
		Rent r = new Rent(a, beds, true);
		rentsAtt.get(beds-1).put(a, r);
		stmt.createRent(r);
		return r;
	}
	
	private static Rent makeRentDet(int area, int beds) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		if(beds < BedsAndBaths.MIN_BEDS.getInt() || beds > BedsAndBaths.MAX_BEDS.getInt())
			throw new IllegalArgumentException("Invalid number of beds " + beds);
		Area a = AreaFactory.makeArea(area);
		if(rentsDet.get(beds-1).containsKey(a))
			return rentsDet.get(beds-1).get(a);
		Rent r = new Rent(a, beds, false);
		rentsDet.get(beds-1).put(a, r);
		stmt.createRent(r);
		return r;
	}
	
	public static Buy makeBuy(int area, int beds, boolean attached, double downPayment) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		if(attached)
			return makeBuyAtt(area, beds, downPayment);
		else
			return makeBuyDet(area, beds, downPayment);
	}
	
	private static Buy makeBuyAtt(int area, int beds, double downPayment) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		if(beds < BedsAndBaths.MIN_BEDS.getInt() || beds > BedsAndBaths.MAX_BEDS.getInt())
			throw new IllegalArgumentException("Invalid number of beds " + beds);
		if(!downPaymentsAtt.containsKey(downPayment))
			throw new IllegalArgumentException("Invalid down payment percentage " + downPayment);
		Area a = AreaFactory.makeArea(area);
		HashMap<Area,Buy> list = downPaymentsAtt.get(downPayment).get(beds-1);
		if(list.containsKey(a))
			return list.get(a);
		Buy b = new Buy(a, beds, true, downPayment);
		list.put(a, b);
		stmt.createBuy(b);
		return b;
	}
	
	private static Buy makeBuyDet(int area, int beds, double downPayment) throws IllegalArgumentException, ClassNotFoundException, SQLException{
		if(beds < BedsAndBaths.MIN_BEDS.getInt() || beds > BedsAndBaths.MAX_BEDS.getInt())
			throw new IllegalArgumentException("Invalid number of beds " + beds);
		if(!downPaymentsDet.containsKey(downPayment))
			throw new IllegalArgumentException("Invalid down payment percentage " + downPayment);
		Area a = AreaFactory.makeArea(area);
		HashMap<Area,Buy> list = downPaymentsDet.get(downPayment).get(beds-1);
		if(list.containsKey(a))
			return list.get(a);
		Buy b = new Buy(a, beds, false, downPayment);
		list.put(a, b);
		stmt.createBuy(b);
		return b;
	}
	
	public static ArrayList<HashMap<Area,Rent>> getAllRents(boolean attached) throws IllegalArgumentException{
		if(attached){
			return new ArrayList<HashMap<Area,Rent>>(rentsAtt);
		}
		else{
			return new ArrayList<HashMap<Area,Rent>>(rentsDet);
		}
	}
	
	public static HashMap<Double, ArrayList<HashMap<Area,Buy>>> getAllBuys(boolean attached) throws IllegalArgumentException{
		if(attached){
			return new HashMap<Double, ArrayList<HashMap<Area,Buy>>>(downPaymentsAtt);
		}
		else{
			return new HashMap<Double, ArrayList<HashMap<Area,Buy>>>(downPaymentsDet);
		}
	}
	
	public static ArrayList<HashMap<Area,Buy>> getAllBuysAtDP(double downPayment, boolean attached) throws IllegalArgumentException{
		if(attached){
			if(!downPaymentsAtt.containsKey(downPayment))
				throw new IllegalArgumentException("Invalid down payment percentage " + downPayment);
			else
				return new ArrayList<HashMap<Area,Buy>>(downPaymentsAtt.get(downPayment));
		}
		else{
			if(!downPaymentsDet.containsKey(downPayment))
				throw new IllegalArgumentException("Invalid down payment percentage " + downPayment);
			else
				return new ArrayList<HashMap<Area,Buy>>(downPaymentsDet.get(downPayment));
		}
	}
}
