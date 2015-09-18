package mls;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import mls.area.Area;
import mls.area.AreaFactory;
import mls.property.Buy;
import mls.property.PropertyFactory;
import mls.property.Rent;
import mls.property.TempBuy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import constants.BedsAndBaths;
import constants.DownPayTypes;
import db.DBConnection;
import excel.ConvertExcel;

public class ImportMLS {
	private static final Logger log = Logger.getLogger(ImportMLS.class.getName());
	private final double stdIntRate = .00375;
	private final int stdNumPayments = 360;
	private final double stdTaxRate = .00529;
	private final String areaField = "Area";
	private final String cityField = "City";
	private final String countyField = "County";
	private final String buyPriceField = "Sold Price";
	private final String rentPriceField = "RP/RNP";
	private final String rentPriceField2 = "Rent Search Price";
	private final String buyZipField = "Zip Code";
	private final String rentZipField = "Zip";
	private final String fullBathField = "# Full Baths";
	private final String halfBathField = "# Half Baths";
	private final String bothBathField = "Total Full/Half Baths";
	private final String bedsField1 = "Bedrooms - All Levels";
	private final String bedsField2 = "# Bedrooms";
	private final String bedsField3 = "All Beds"; 
	
	public void doImport(JSONObject table, DBConnection conn) throws JSONException, IllegalArgumentException, ClassNotFoundException, SQLException{
		HashMap<Integer,ArrayList<JSONObject>> areas = getAreas(table);
		addAreaAvgs(areas, conn);
	}
	
	private double getMortgagePayment(double intRate, int numPayments, double price, double downPayment, double taxRate){
		return ((intRate*(price * (1-downPayment))*Math.pow((1+intRate),numPayments))/(Math.pow((1+intRate),numPayments)-1))+(price*taxRate);
	}
	
	private double getMortgagePayment(double price, double downPayment){
		return getMortgagePayment(stdIntRate, stdNumPayments, price, downPayment, stdTaxRate);
	}
	
	private HashMap<Integer,ArrayList<JSONObject>> getAreas(JSONObject table) throws JSONException{
		JSONArray rows = table.getJSONArray("results");
		HashMap<Integer,ArrayList<JSONObject>> areas = new HashMap<Integer,ArrayList<JSONObject>>();
		for(int i = 0; i < rows.length(); i++){
			JSONObject row = rows.getJSONObject(i);
			int area = row.getInt("Area");
			if(areas.containsKey(area)){
				areas.get(area).add(row);
			}
			else{
				ArrayList<JSONObject> temp = new ArrayList<JSONObject>();
				areas.put(area, temp);
			}
		}
		return areas;
	}
	
	private void addAreaAvgs(HashMap<Integer,ArrayList<JSONObject>> areas, DBConnection conn) throws JSONException, IllegalArgumentException, ClassNotFoundException, SQLException{
		int beds = 0;
		//ArrayList<TempBuy> tbs = new ArrayList<TempBuy>();
		boolean isRent = false;
		String price = "";
		String zip = "";
		
		ArrayList<JSONObject> vals = new ArrayList<JSONObject>();
		for(ArrayList<JSONObject> a : areas.values()){
			if(a.isEmpty()){
				areas.remove(a);
				continue;
			}
			else{
				vals = a;
				break;
			}
		}
		JSONObject first = vals.get(0);
		if(first.has(buyPriceField)){
			price = buyPriceField;
			zip = buyZipField;
		}
		else {
			if(first.has(rentPriceField)){
				price = rentPriceField;
			}
			else{
				price = rentPriceField2;
			}
			zip = rentZipField;
			isRent = true;
		}
		boolean done = false;
		int count = 0;
		while(!done){
			try{
				if(first.has(bedsField1)){
					beds = first.getInt(bedsField1);
					done = true;
				}
				else if(first.has(bedsField2)){
					beds = first.getInt(bedsField2);
					done = true;
				}
				else{
					beds = first.getInt(bedsField3);
					done = true;
				}
			}
			catch(JSONException e){
				count++;
				if(count < vals.size())
					first = vals.get(count);
				else{
					done = true;
				}
			}
		}
		for(ArrayList<JSONObject> a : areas.values()){
			if(a.isEmpty()){
				areas.remove(a);
				continue;
			}
		}
		if(beds > BedsAndBaths.MAX_BEDS.getInt())
			beds = BedsAndBaths.MAX_BEDS.getInt();
		if(beds < BedsAndBaths.MIN_BEDS.getInt())
			beds = BedsAndBaths.MIN_BEDS.getInt();
		for(ArrayList<JSONObject> a : areas.values()){
			if(a.isEmpty()){
				areas.remove(a);
				continue;
			}
			
			boolean isAtt = false;
			int areaNum = a.get(0).getInt(areaField);
			String city = null;
			String county = null;
			if(a.get(0).has(cityField))
				city = a.get(0).getString(cityField);
			if(a.get(0).has(countyField))
				county = a.get(0).getString(countyField);
			HashSet<Integer> zips = new HashSet<Integer>();
			double[] totalPrices = new double[BedsAndBaths.MAX_BATHS.getInt()];
			int[] counts = new int[BedsAndBaths.MAX_BATHS.getInt()];
			for(int i = 0; i < BedsAndBaths.MAX_BATHS.getInt(); i++){
				totalPrices[i] = 0;
				counts[i] = 0;
			}
			for(JSONObject j : a){
				int baths = 0;
				if(j.has(bothBathField)){
					double temp = j.getDouble(bothBathField);
					baths = (int) Math.floor(temp);
					baths += (int) (temp % 1) * 10;
				}
				else{
					baths = j.getInt(fullBathField);
					baths += j.getInt(halfBathField);
				}
				if(baths > BedsAndBaths.MAX_BATHS.getInt())
					baths = BedsAndBaths.MAX_BATHS.getInt();
				if(baths < BedsAndBaths.MIN_BATHS.getInt())
					baths = BedsAndBaths.MIN_BATHS.getInt();
				baths--;
				counts[baths]++;
				totalPrices[baths] += j.getDouble(price);
				int z = j.getInt(zip);
				zips.add(z);			
			}
			for(int i = 0; i < counts.length; i++){
				if(counts[i] != 0){
					totalPrices[i] = totalPrices[i] / counts[i];
				}
			}
			if(city != null){
				AreaFactory.makeArea(areaNum, city, zips, conn);
				if(county != null)
					conn.createCounty(city, county);
			}
			else{
				AreaFactory.makeArea(areaNum, zips, conn);
			}
			if(isRent){
				addRentArea(areaNum, beds, isAtt, totalPrices, counts, conn);
			}
			else{
				addBuyArea(areaNum, beds, isAtt, totalPrices, counts, conn);
				//tbs.add(new TempBuy(areaNum, beds, isAtt, totalPrices));
				//System.out.println(tbs.get(tbs.size()-1).toString());
			}
		}
		/*if(!isRent){
			for(TempBuy temp : tbs){
				addBuyArea(temp.getArea(), beds, temp.isAttached(), temp.getTotalPrices(), conn);
			}
		}*/
	}
	
	private void addRentArea(int areaNum, int beds, boolean isAtt, double[] prices, int[] counts, DBConnection conn) throws JSONException, IllegalArgumentException, ClassNotFoundException, SQLException{
		for(int i = 0; i < BedsAndBaths.MAX_BATHS.getInt(); i++) {
			PropertyFactory.makeRent(areaNum, beds, i+1, isAtt, prices[i], counts[i], conn);
		}
	}
	
	private void addBuyArea(int areaNum, int beds, boolean isAtt, double[] prices, int[] counts, DBConnection conn) throws JSONException, IllegalArgumentException, ClassNotFoundException, SQLException{
		for(int i = 0; i < BedsAndBaths.MAX_BATHS.getInt(); i++) {
			for(DownPayTypes dpt : DownPayTypes.values()){
				double dp = dpt.getDouble();
				PropertyFactory.makeBuy(areaNum, beds, i+1, isAtt, dp, getMortgagePayment(prices[i], dp), counts[i], conn);
			}
		}	
	}
	
	/*public static void main(String[] args) throws JSONException, IllegalArgumentException, ClassNotFoundException, SQLException{
		ImportMLS icsv = new ImportMLS();
		ConvertExcel cx = new ConvertExcel();
		JSONObject a = cx.CSVtoJSON("rentDet3b.xlsx");
		//JSONObject b = cx.CSVtoJSON("buyAtt2b.xlsx");
		//JSONObject c = cx.CSVtoJSON("rentAtt1b.xlsx");
		//JSONObject d = cx.CSVtoJSON("buyDet4b.xlsx");
		icsv.doImport(a);
		//icsv.doImport(b);
		//icsv.doImport(c);
		//icsv.doImport(d);
		ArrayList<HashMap<Area,Rent>> rs = PropertyFactory.getAllRents(false);
		HashMap<Double, ArrayList<HashMap<Area,Buy>>> bs = PropertyFactory.getAllBuys(false);
		for(HashMap<Area,Rent> h : rs){
			for(Rent r : h.values()){
				System.out.println(r.toString());
			}
		}
		for(ArrayList<HashMap<Area,Buy>> l : bs.values()){
			for(HashMap<Area,Buy> h : l){
				for(Buy u : h.values()){
					System.out.println(u.toString());
				}
			}
		}
	}*/
}
