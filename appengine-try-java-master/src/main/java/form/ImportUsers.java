package form;

import java.util.ArrayList;
import java.util.HashMap;

import mls.ImportMLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import constants.FormDBFields;
import constants.SpreadsheetFields;
import excel.ConvertExcel;

public class ImportUsers {
	//this is a temp until we get the form connected
	public ArrayList<HashMap<String, Object>> findNewUsers(JSONObject table) throws JSONException{
		JSONArray rows = table.getJSONArray("results");
		ArrayList<HashMap<String, Object>> newUsers = new ArrayList<HashMap<String, Object>>();
		for(int i = 0; i < rows.length(); i++){
			HashMap<String, Object> temp = new HashMap<String, Object>();
			JSONObject row = rows.getJSONObject(i);
			for(SpreadsheetFields s : SpreadsheetFields.values()){
				if(row.has(s.toString())){
					temp.put(FormDBFields.valueOf(s.name()).toString(), row.get(s.toString()));
				}
			}
			newUsers.add(temp);
		}
		return newUsers;
	}
	
	public ArrayList<HashMap<String, Object>> findUsersAreas(ArrayList<HashMap<String, Object>> users, ArrayList<HashMap<String,Object>> areasRents, ArrayList<HashMap<String,Object>> areasBuys){
		for(HashMap<String, Object> user : users){
			user = findUserAreas(user, areasRents, areasBuys);
		}
		return users;
	}
	
	public HashMap<String, Object> findUserAreas(HashMap<String,Object> user, ArrayList<HashMap<String,Object>> areasRents, ArrayList<HashMap<String,Object>> areasBuys){
		ArrayList<Integer> results = new ArrayList<Integer>();
		String type = (String) user.get("RENT_OR_BUY");
		if(type == null){
			return user;
		}
		if(type.equalsIgnoreCase("Renting")){
			if(user.get("BATHS") != null && user.get("MONTHLY_RENT") != null)
				try{
					results = findRentsByPrice(Double.valueOf(user.get("MONTHLY_RENT").toString().replaceAll("$,", "")), Integer.valueOf(user.get("BATHS").toString().replace("+", "")), areasRents);
		
				}
				catch(Exception e){
					e.printStackTrace();
				}
		}
		else if(type.equalsIgnoreCase("Buying")){
			if(user.get("BATHS") != null && user.get("MONTHLY_MORTGAGE") != null && user.get("DOWN_PAYMENT") != null)
				try{
					results = findMortgagesByPrice(Double.valueOf(user.get("MONTHLY_MORTGAGE").toString().replaceAll("$,", "")), Double.valueOf(user.get("DOWN_PAYMENT").toString()), Integer.valueOf(user.get("BATHS").toString().replace("+", "")), areasBuys);
				}
				catch(Exception e){
					e.printStackTrace();
				}
		}
		else{
			if(user.get("BATHS") != null && user.get("MONTHLY_RENT") != null)
				try{
					results = findRentsByPrice(Double.valueOf(user.get("MONTHLY_RENT").toString().replaceAll("$,", "")), Integer.valueOf(user.get("BATHS").toString().replace("+", "")), areasRents);
		
				}
				catch(Exception e){
					e.printStackTrace();
				}
			if(user.get("BATHS") != null && user.get("MONTHLY_MORTGAGE") != null && user.get("DOWN_PAYMENT") != null)
				try{
					results.addAll(findMortgagesByPrice(Double.valueOf(user.get("MONTHLY_MORTGAGE").toString().replaceAll("$,", "")), Double.valueOf(user.get("DOWN_PAYMENT").toString()), Integer.valueOf(user.get("BATHS").toString().replace("+", "")), areasBuys));
				}
				catch(Exception e){
					e.printStackTrace();
				}
		}
		user.put("MATCHED_AREAS", results);
		return user;
	}
	
	public ArrayList<Integer> findRentsByPrice(double price, int baths, ArrayList<HashMap<String,Object>> areas){
		ArrayList<Integer> results = new ArrayList<Integer>();
		int margin = 100;
		while(results.size() < 10 && margin <= 500){
			for(int tempBaths = baths; tempBaths <= 3; tempBaths++){
				for(HashMap<String,Object> area : areas){
					if(area.get("Price"+tempBaths+"Bath") != null){
						double p = Double.valueOf(area.get("Price"+tempBaths+"Bath").toString());
						if(Math.abs(p - price) <= margin){
							results.add(Integer.valueOf(area.get("Area").toString()));
						}
					}
				}
			}
			margin += 100;
		}
		return results;
	}
	
	public ArrayList<Integer> findMortgagesByPrice(double price, double dp, int baths, ArrayList<HashMap<String,Object>> areas){
		ArrayList<Integer> results = new ArrayList<Integer>();
		int margin = 100;
		int downpayment = (int) dp * 100;
		while(results.size() < 10 && margin <= 500){
			for(int tempBaths = baths; tempBaths <= 3; tempBaths++){
				for(HashMap<String,Object> area : areas){				
					if(area.get("Price"+tempBaths+"Bath"+downpayment+"Percent") != null){
						double p = Double.valueOf(area.get("Price"+tempBaths+"Bath"+downpayment+"Percent").toString());
						if(Math.abs(p - price) <= margin){
							results.add(Integer.valueOf(area.get("Area").toString()));
						}
					}
				}
			}
			margin += 100;
		}
		return results;
	}
	
	public static void main(String[] args) {
		ImportUsers iu = new ImportUsers();
		ImportMLS icsv = new ImportMLS();
		ConvertExcel cx = new ConvertExcel();
	}

}
