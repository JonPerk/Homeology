package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import mls.ImportMLS;
import mls.area.Area;
import mls.area.AreaFactory;
import mls.property.Buy;
import mls.property.Rent;
import myapp.SubmitFormServlet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import constants.BedsAndBaths;
import constants.FormDBFields;
import constants.SpreadsheetFields;
import constants.UserObjectFields;
import constants.ZoneSpeeds;
import db.DBConnection;
import excel.ConvertExcel;

public class ImportUsers {
	private static final Logger log = Logger.getLogger(ImportUsers.class.getName());
	private final String apiKey = "ms1tPiHZV1fRwm0srvrJgftzEYLrqesNHthwMYwI0LAw7AYz6qinFMXkKJLbXeZF";
	private final String urlName = "http://www.zipcodeapi.com/rest/" + apiKey + "/radius.json/zip_code/dist/mile";
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
	
	public User addNewUser(String results, DBConnection db) throws ClassNotFoundException, SQLException, IllegalArgumentException, IOException, JSONException{
		String[] responses = results.split("&");
		HashMap<String, Object> newUser = new HashMap<String, Object>();
		for(int i = 0; i < responses.length; i++){
			String[] temp = responses[i].split("=");
			String plus = Pattern.quote("+");
			if(temp.length > 1){
				UserObjectFields uof = UserObjectFields.getUserObjectField(temp[0]);
				temp[1] = temp[1].replace("+", " ");
				if(uof != null){
					if(uof.toJavaClass().equals(String.class))
						newUser.put(uof.toObjectField(), temp[1]);
					else if(uof.toJavaClass().equals(int.class))
						newUser.put(uof.toObjectField(), Integer.valueOf(temp[1].replaceAll("[$,]?(%2B)?(hr[.])?(hrs[.])?", "").trim()));
					else if(uof.toJavaClass().equals(double.class)){
						double d;
						if(temp[1].equals("5%25"))
							d = 0.05;
						else if(temp[1].equals("10%25"))
							d= 0.1;
						else if(temp[1].equals("20%25"))
							d= 0.2;
						else if(temp[1].equals("Not+applicable%2Fdon%27t+care"))
							d = 10;
						else{
							d = Double.valueOf(temp[1].replaceAll("[$,]?(%2B)?(hr[.])?(hrs[.])?", "").trim());
						}
						newUser.put(uof.toObjectField(), d);
					}else if(uof.toJavaClass().equals(boolean.class))
						newUser.put(uof.toObjectField(), Boolean.valueOf(temp[1]));
					else if(uof.toJavaClass().equals(byte[].class))
						newUser.put(uof.toObjectField(), temp[1].getBytes());
					else if(uof.toJavaClass().equals(Date.class)){
						String[] dates = temp[1].split("-");
						TimeZone tz = TimeZone.getTimeZone("America/Chicago");
						Calendar cal = Calendar.getInstance(tz);
						cal.set(2015, Integer.valueOf(dates[0]), Integer.valueOf(dates[1]));
						Date d = cal.getTime();
						newUser.put(uof.toObjectField(), d);
					}
				}
			}
		}
		String type = (String) newUser.get(UserObjectFields.RENT_OR_BUY.toObjectField());
		if(type == null){
			throw new IllegalArgumentException("Invalid user form");
		}
		ArrayList<Integer> matches = new ArrayList<Integer>();
		if(type.equalsIgnoreCase("Renting"))
			matches = findRentsByPrice(Double.valueOf(newUser.get("MonthlyRent").toString()), Integer.valueOf(newUser.get("Beds").toString()), Integer.valueOf(newUser.get("Baths").toString()), db);
		else
			matches = findMortgagesByPrice(Double.valueOf(newUser.get("MonthlyMortgage").toString()), Integer.valueOf(newUser.get("Beds").toString()), Integer.valueOf(newUser.get("Baths").toString()), Double.valueOf(newUser.get("DownPayment").toString()), db);
		newUser.put("MatchedAreas", matches);
		User user = new User(newUser, db);
		if(user.getWorkZip() != 0){
			if(user.getWorkZip2() != 0){
				matches = findMatchedCommuteAreas(user.getMatchedAreas(), user.getCommuteTime(), user.getWorkZip(), user.getWorkZip2(), db);
			}
			else{
				matches = findMatchedCommuteAreas(user.getMatchedAreas(), user.getCommuteTime(), user.getWorkZip(), db);
			}
			user.setMatchedAreas(matches, db);
		}
		return user;
	}
	
	public ArrayList<Integer> findMatchedCommuteAreas(ArrayList<Integer> matches, double commuteTime, int workZip, DBConnection conn) throws IOException, JSONException, SQLException, IllegalArgumentException, ClassNotFoundException{
		//log.log(Level.INFO, workZip + "");
		ArrayList<Integer> newMatches = new ArrayList<Integer>();
		if(workZip != 0){
			ArrayList<Integer> zips = getZips(workZip, commuteTime, conn);
			for(int m : matches){
				Area a = AreaFactory.makeArea(m, conn);
				HashSet<Integer> areaZips = a.getZips();
				//log.log(Level.INFO, a.toString());
				for(int z :zips){
					if(areaZips.contains(z)){
						newMatches.add(a.getArea());
						break;
					}
				}
			}
		}
		else{
			newMatches = matches;
		}
		return newMatches;
	}
	
	public ArrayList<Integer> findMatchedCommuteAreas(ArrayList<Integer> matches, double commuteTime, int workZip, int workZip2, DBConnection conn) throws IOException, JSONException, SQLException, IllegalArgumentException, ClassNotFoundException{
		ArrayList<Integer> newMatches = new ArrayList<Integer>();
		newMatches = findMatchedCommuteAreas(matches, commuteTime, workZip, conn);
		newMatches = findMatchedCommuteAreas(newMatches, commuteTime, workZip2, conn);
		return newMatches;
	}
	
	public ArrayList<Integer> getZips(int workZip, double commuteTime, DBConnection conn) throws IOException, JSONException, SQLException{
		ArrayList<Integer> results = new ArrayList<Integer>();
		String urlString = urlName.replaceAll("zip_code", Integer.toString(workZip));
		String dist = Double.toString(40*commuteTime);
		urlString = urlString.replaceAll("dist", dist);
		URL url = new URL(urlString);
		HttpURLConnection api = (HttpURLConnection) url.openConnection();
		api.setDoOutput(true);
		api.setDoInput(true);
		api.setUseCaches(false);
		BufferedReader rd = new BufferedReader(new InputStreamReader(api.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		api.disconnect();
		JSONObject json = new JSONObject(sb.toString());
		JSONArray arr = json.getJSONArray("zip_codes");
		//log.log(Level.INFO, arr.toString(2));
		int workZone = conn.getZipZone(workZip);
		//log.log(Level.INFO, workZone + " " + workZip);
		for(int i = 0; i < arr.length(); i++){
			JSONObject o = arr.getJSONObject(i);
			//log.log(Level.INFO, o.toString(2));
			int zip = Integer.valueOf(o.getString("zip_code"));
			int zone = conn.getZipZone(zip);
			//log.log(Level.INFO, zone + " ");
			if(zone != -1 && workZone != -1){
				if(ZoneSpeeds.getZoneSpeed(zone).getSpeeds()[workZone]*commuteTime >= o.getDouble("distance")){
					//log.log(Level.INFO, (ZoneSpeeds.getZoneSpeed(zone).getSpeeds()[workZone]*commuteTime >= o.getDouble("distance"))+"");
					results.add(zip);
				}
			}
		}
		return results;
	}
	
	public ArrayList<Integer> findRentsByPrice(double price, int beds, int baths, DBConnection conn) throws ClassNotFoundException, SQLException{
		ArrayList<Integer> results = new ArrayList<Integer>();
		int margin = 100;
		while(results.size() < 10 && margin <= 200){
			for(int tempBaths = baths; tempBaths <= BedsAndBaths.MAX_BATHS.getInt(); tempBaths++){
				ArrayList<Rent> rents = conn.getAllRents(beds, tempBaths);
				for(Rent rent : rents){
					double p = rent.getPrice();
					if(Math.abs(p - price) <= margin){
						results.add(rent.getArea());
					}
				}
			}
			margin += 50;
		}
		return results;
	}
	
	public ArrayList<Integer> findMortgagesByPrice(double price, int beds, int baths, double dp, DBConnection conn) throws ClassNotFoundException, SQLException{
		ArrayList<Integer> results = new ArrayList<Integer>();
		int margin = 100;
		int downpayment = (int) dp * 100;
		while(results.size() < 10 && margin <= 200){
			for(int tempBaths = baths; tempBaths <= 3; tempBaths++){
				ArrayList<Buy> buys = conn.getAllBuysAtDP(beds, tempBaths, dp);
				for(Buy buy : buys){				
					double p = buy.getPrice();
					if(Math.abs(p - price) <= margin){
						results.add(buy.getArea());
					}
				}
			}
			margin += 50;
		}
		return results;
	}

}
