package mls;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import excel.ConvertExcel;

public class ImportMLS {
	private final double stdIntRate = .00375;
	private final int stdNumPayments = 360;
	private final double stdTaxRate = .00529;
	
	public double getMortgagePayment(double intRate, int numPayments, double price, double downPayment, double taxRate){
		return ((intRate*(price * (1-downPayment))*Math.pow((1+intRate),numPayments))/(Math.pow((1+intRate),numPayments)-1))+(price*taxRate);
	}
	
	public double getMortgagePayment(double price, double downPayment){
		return getMortgagePayment(stdIntRate, stdNumPayments, price, downPayment, stdTaxRate);
	}
	
	public HashMap<Integer,ArrayList<JSONObject>> getAreas(JSONObject table) throws JSONException{
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
	
	public ArrayList<HashMap<String, Object>> getAreaAvg(HashMap<Integer,ArrayList<JSONObject>> areas) throws JSONException{
		ArrayList<HashMap<String,Object>> areasAvgs = new ArrayList<HashMap<String,Object>>();
		for(ArrayList<JSONObject> a : areas.values()){
			if(a.isEmpty()){
				areas.remove(a);
				continue;
			}
			HashMap<String,Object> temp = new HashMap<String,Object>();
			String price = "";
			String zip = "";
			int area = a.get(0).getInt("Area");
			temp.put("Area", area);
			temp.put("City", a.get(0).getString("City"));
			ArrayList<Integer> zips = new ArrayList<Integer>();
			if(a.get(0).has("Sold Price")){
				price = "Sold Price";
				zip = "Zip Code";
			}
			else{
				price = "RP/RNP";
				zip = "Zip";
			}
			int[] bathcount = {0,0,0,0};
			for(JSONObject j : a){
				int fullbath = j.getInt("# Full Baths");
				int halfbath = j.getInt("# Half Baths");
				int baths = fullbath + halfbath;
				if(baths > 3)
					baths = 3;
				String bathname = "Price" + baths + "Bath";
				bathcount[baths]++;
				if(!temp.containsKey(bathname)){
					temp.put(bathname, j.getDouble(price));
				}
				else{
					temp.put(bathname, j.getDouble(price) + (double) temp.get(bathname));
				}
				int z = j.getInt(zip);
				if(!zips.contains(z))
					zips.add(z);			
			}
			for(int i = 1; i < bathcount.length; i++){
				if(!temp.containsKey("Price"+i+"Bath")){
					temp.put("Price"+i+"Bath", 0.0);
				}
				else{
					temp.put("Price"+i+"Bath", (double) temp.get("Price"+i+"Bath")/bathcount[i]);
				}
			}
			temp.put("Zips", zips);
			areasAvgs.add(temp);
		}
		return areasAvgs;
	}
	
	public static void main(String[] args){
		ImportMLS icsv = new ImportMLS();
		ConvertExcel cx = new ConvertExcel();
		try {
			JSONObject t = cx.CSVtoJSON("rentDet3b.xlsx");
			//JSONObject t = icsv.CSVtoJSON("buyAtt2b.xlsx");
			HashMap<Integer,ArrayList<JSONObject>> as = icsv.getAreas(t);
			ArrayList<HashMap<String,Object>> aa = icsv.getAreaAvg(as);
			/*for(ArrayList<JSONObject> av : as.values()){
				for(JSONObject a : av){
					System.out.println("AREA: " + a.get("Area") + 
							"    CITY: " + a.get("City"));
				}
			}*/
			for(HashMap<String,Object> a : aa){
				String zips = "";
				ArrayList<Integer> zs = (ArrayList<Integer>) a.get("Zips");
				for(int z : zs){
					zips = zips + z + " ";
				}
				System.out.println("AREA: " + a.get("Area") + 
						"    CITY: " + a.get("City") + 
						"    1 BATH AVG PRICE: " + a.get("Price1Bath") +
						"    2 BATH AVG PRICE: " + a.get("Price2Bath") +
						"    3 BATH AVG PRICE: " + a.get("Price3Bath") +
						"    ZIPS: " + zips);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
