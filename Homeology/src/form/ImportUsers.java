package form;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import excel.ConvertExcel;

public class ImportUsers {
	//TODO compare timestamp from spreadsheet 
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
	
	public HashMap<String, Object> findUserAreasByPrice(HashMap<String,Object> user){
		//TODO
		return new HashMap<String, Object>();
	}
	
	public static void main(String[] args) {
		ImportUsers iu = new ImportUsers();
		ConvertExcel cx = new ConvertExcel();
		try {
			JSONObject t = cx.CSVtoJSON("form.xlsx");
			ArrayList<HashMap<String, Object>> l = iu.findNewUsers(t);
			for(HashMap<String,Object> a : l){
				System.out.println("NEW USER: ");
				for(FormDBFields f : FormDBFields.values()){
					System.out.println("  " + f.toString() + ": " + a.get(f.toString()));
				}
				System.out.println();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
