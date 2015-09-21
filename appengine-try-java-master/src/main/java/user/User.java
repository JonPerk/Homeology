package user;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;

import mls.ImportMLS;
import constants.FormDBFields;
import constants.SpreadsheetFields;
import constants.UserObjectFields;
import db.DBConnection;

public class User {
	private static final Logger log = Logger.getLogger(DBConnection.class.getName());
	private final byte[] id;
	private final Date created;
	private Date modified;
	private String fname;
	private String moveReason;
	private String currentLoc;
	private Date moveDate;
	private String housingType; //possible enum
	private int beds;
	private int baths;
	private int workZip;
	private int workZip2;
	private double commuteTime;
	private String transMode; //possible enum
	private String desiredLocs;
	private String desiredRegion;
	private String rentOrBuy; //possible enum
	private String email;
	private String promoCode;
	private double priceRangeBuyMin;
	private double priceRangeBuyMax;
	private double monthlyMortgage;
	private double downPayment;
	private boolean needsSchool;
	private double monthlyRent;
	private boolean needsParking;
	private String schoolLevel; //possible enum
	private String schoolType; //possible enum
	private String moreQuestions;
	private String currentLocLikesDislikes;
	private String importantActivities;
	private String importantAreas; //possible enum
	private int walkability;
	private int greenspace;
	private int restaurants;
	private int bars;
	private int amenities;
	private String safety;
	private String additionalInfo;
	private String previousSearch; //possible enum
	private String aptStyle; //possible enum
	private String schoolPerformance; //possible enum
	private String phone;
	private String summary;
	private String assigned;
	private String comments;
	private String currentStatus;
	private Date handOffDate;
	private ArrayList<Integer> matchedAreas;
	
	public User(){
		UUID uuid = UUID.randomUUID();
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
	    bb.putLong(uuid.getMostSignificantBits());
	    bb.putLong(uuid.getLeastSignificantBits());
	    id = bb.array();
		created = new Date();
		modified = created;
	}
	
	public User(HashMap<String, Object> args, DBConnection conn) throws SQLException{
		UUID uuid = UUID.randomUUID();
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
	    bb.putLong(uuid.getMostSignificantBits());
	    bb.putLong(uuid.getLeastSignificantBits());
	    id = bb.array();
		created = new Date();
		modified = created;
		for(UserObjectFields uof : UserObjectFields.values()){
			String s = uof.toObjectField();
			if(args.containsKey(s)){
					Method m;
					try {
						Class[] cl = new Class[1];
						cl[0] = uof.toJavaClass();
						m = User.class.getMethod("set" + s, cl);
						m.invoke(this, args.get(s));
					} catch (NoSuchMethodException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					} catch (SecurityException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					} catch (IllegalAccessException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					} catch (IllegalArgumentException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					} catch (InvocationTargetException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
				
			}
		}
		conn.createUser(this);
	}
	
	public User(HashMap<String, Object> args, byte[] cust_id, Date date_created, Date date_modified) throws SQLException{
	    id = cust_id;
		created = date_created;
		modified = date_modified;
		for(UserObjectFields uof : UserObjectFields.values()){
			String s = uof.toObjectField();
			if(args.containsKey(s)){
					Method m;
					try {
						Class[] cl = new Class[1];
						cl[0] = uof.toJavaClass();
						m = User.class.getMethod("set" + s, cl);
						m.invoke(this, args.get(s));
					} catch (NoSuchMethodException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage() + " " + s + " " + uof.toJavaClass().getName(), e);
					} catch (SecurityException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage() + " " + s + " " + uof.toJavaClass().getName(), e);
					} catch (IllegalAccessException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage() + " " + s + " " + uof.toJavaClass().getName(), e);
					} catch (IllegalArgumentException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage() + " " + s + " " + uof.toJavaClass().getName(), e);
					} catch (InvocationTargetException e) {
						log.log(Level.SEVERE, e.getLocalizedMessage() + " " + s + " " + uof.toJavaClass().getName(), e);
					}
				
			}
		}
	}
	
	public byte[] getId() {
		return id;
	}
	
	public UUID getUUID() {
		ByteBuffer bb = ByteBuffer.wrap(id);
		long firstLong = bb.getLong(); 
		long secondLong = bb.getLong(); 
		return new UUID(firstLong, secondLong);
	}
	
	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	//TODO
	public void setModified(Date modified, DBConnection conn) {
		this.modified = modified;
	}

	public Date getCreated() {
		return created;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}
	//TODO
	public void setFname(String fname, DBConnection conn) {
		this.fname = fname;
	}

	public String getMoveReason() {
		return moveReason;
	}

	public void setMoveReason(String moveReason) {
		this.moveReason = moveReason;
	}
	//TODO
	public void setMoveReason(String moveReason, DBConnection conn) {
		this.moveReason = moveReason;
	}

	public String getCurrentLoc() {
		return currentLoc;
	}

	public void setCurrentLoc(String currentLoc) {
		this.currentLoc = currentLoc;
	}
	//TODO
	public void setCurrentLoc(String currentLoc, DBConnection conn) {
		this.currentLoc = currentLoc;
	}

	public Date getMoveDate() {
		return moveDate;
	}

	public void setMoveDate(Date moveDate) {
		this.moveDate = moveDate;
	}
	//TODO
	public void setMoveDate(Date moveDate, DBConnection conn) {
		this.moveDate = moveDate;
	}

	public String getHousingType() {
		return housingType;
	}

	public void setHousingType(String housingType) {
		this.housingType = housingType;
	}
	//TODO
	public void setHousingType(String housingType, DBConnection conn) {
		this.housingType = housingType;
	}

	public int getBeds() {
		return beds;
	}

	public void setBeds(int beds) {
		this.beds = beds;
	}
	//TODO
	public void setBeds(int beds, DBConnection conn) {
		this.beds = beds;
	}

	public int getBaths() {
		return baths;
	}

	public void setBaths(int baths) {
		this.baths = baths;
	}
	//TODO
	public void setBaths(int baths, DBConnection conn) {
		this.baths = baths;
	}

	public int getWorkZip() {
		return workZip;
	}

	public void setWorkZip(int workZip) {
		this.workZip = workZip;
	}
	//TODO
	public void setWorkZip(int workZip, DBConnection conn) {
		this.workZip = workZip;
	}

	public int getWorkZip2() {
		return workZip2;
	}

	public void setWorkZip2(int workZip2) {
		this.workZip2 = workZip2;
	}
	//TODO
	public void setWorkZip2(int workZip2, DBConnection conn) {
		this.workZip2 = workZip2;
	}

	public double getCommuteTime() {
		return commuteTime;
	}

	public void setCommuteTime(double commuteTime) {
		this.commuteTime = commuteTime;
	}
	//TODO
	public void setCommuteTime(double commuteTime, DBConnection conn) {
		this.commuteTime = commuteTime;
	}

	public String getTransMode() {
		return transMode;
	}

	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}
	//TODO
	public void setTransMode(String transMode, DBConnection conn) {
		this.transMode = transMode;
	}

	public String getDesiredLocs() {
		return desiredLocs;
	}

	public void setDesiredLocs(String desiredLocs) {
		this.desiredLocs = desiredLocs;
	}
	//TODO
	public void setDesiredLocs(String desiredLocs, DBConnection conn) {
		this.desiredLocs = desiredLocs;
	}

	public String getDesiredRegion() {
		return desiredRegion;
	}

	public void setDesiredRegion(String desiredRegion) {
		this.desiredRegion = desiredRegion;
	}
	//TODO
	public void setDesiredRegion(String desiredRegion, DBConnection conn) {
		this.desiredRegion = desiredRegion;
	}

	public String getRentOrBuy() {
		return rentOrBuy;
	}

	public void setRentOrBuy(String rentOrBuy) {
		this.rentOrBuy = rentOrBuy;
	}
	//TODO
	public void setRentOrBuy(String rentOrBuy, DBConnection conn) {
		this.rentOrBuy = rentOrBuy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	//TODO
	public void setEmail(String email, DBConnection conn) {
		this.email = email;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	//TODO
	public void setPromoCode(String promoCode, DBConnection conn) {
		this.promoCode = promoCode;
	}

	public double getPriceRangeBuyMin() {
		return priceRangeBuyMin;
	}

	public void setPriceRangeBuyMin(double priceRangeBuy) {
		this.priceRangeBuyMin = priceRangeBuy;
	}
	//TODO
	public void setPriceRangeBuyMin(double priceRangeBuy, DBConnection conn) {
		this.priceRangeBuyMin = priceRangeBuy;
	}
	
	public double getPriceRangeBuyMax() {
		return priceRangeBuyMax;
	}

	public void setPriceRangeBuyMax(double priceRangeBuy) {
		this.priceRangeBuyMax = priceRangeBuy;
	}
	//TODO
	public void setPriceRangeBuyMax(double priceRangeBuy, DBConnection conn) {
		this.priceRangeBuyMax = priceRangeBuy;
	}

	public double getMonthlyMortgage() {
		return monthlyMortgage;
	}

	public void setMonthlyMortgage(double monthlyMortgage) {
		this.monthlyMortgage = monthlyMortgage;
	}
	//TODO
	public void setMonthlyMortgage(double monthlyMortgage, DBConnection conn) {
		this.monthlyMortgage = monthlyMortgage;
	}

	public double getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(double downPayment) {
		this.downPayment = downPayment;
	}
	//TODO
	public void setDownPayment(double downPayment, DBConnection conn) {
		this.downPayment = downPayment;
	}

	public boolean getNeedsSchool() {
		return needsSchool;
	}

	public void setNeedsSchool(boolean needsSchool) {
		this.needsSchool = needsSchool;
	}
	//TODO
	public void setNeedsSchool(boolean needsSchool, DBConnection conn) {
		this.needsSchool = needsSchool;
	}

	public double getMonthlyRent() {
		return monthlyRent;
	}

	public void setMonthlyRent(double monthlyRent) {
		this.monthlyRent = monthlyRent;
	}
	//TODO
	public void setMonthlyRent(double monthlyRent, DBConnection conn) {
		this.monthlyRent = monthlyRent;
	}

	public boolean getNeedsParking() {
		return needsParking;
	}

	public void setNeedsParking(boolean needsParking) {
		this.needsParking = needsParking;
	}
	//TODO
	public void setNeedsParking(boolean needsParking, DBConnection conn) {
		this.needsParking = needsParking;
	}

	public String getSchoolLevel() {
		return schoolLevel;
	}

	public void setSchoolLevel(String schoolLevel) {
		this.schoolLevel = schoolLevel;
	}
	//TODO
	public void setSchoolLevel(String schoolLevel, DBConnection conn) {
		this.schoolLevel = schoolLevel;
	}

	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}
	//TODO
	public void setSchoolType(String schoolType, DBConnection conn) {
		this.schoolType = schoolType;
	}

	public String getMoreQuestions() {
		return moreQuestions;
	}

	public void setMoreQuestions(String moreQuestions) {
		this.moreQuestions = moreQuestions;
	}
	//TODO
	public void setMoreQuestions(String moreQuestions, DBConnection conn) {
		this.moreQuestions = moreQuestions;
	}

	public String getCurrentLocLikesDislikes() {
		return currentLocLikesDislikes;
	}

	public void setCurrentLocLikesDislikes(String currentLocLikesDislikes) {
		this.currentLocLikesDislikes = currentLocLikesDislikes;
	}
	//TODO
	public void setCurrentLocLikesDislikes(String currentLocLikesDislikes, DBConnection conn) {
		this.currentLocLikesDislikes = currentLocLikesDislikes;
	}

	public String getImportantActivities() {
		return importantActivities;
	}

	public void setImportantActivities(String importantActivities) {
		this.importantActivities = importantActivities;
	}
	//TODO
	public void setImportantActivities(String importantActivities, DBConnection conn) {
		this.importantActivities = importantActivities;
	}

	public String getImportantAreas() {
		return importantAreas;
	}

	public void setImportantAreas(String importantAreas) {
		this.importantAreas = importantAreas;
	}
	//TODO
	public void setImportantAreas(String importantAreas, DBConnection conn) {
		this.importantAreas = importantAreas;
	}

	public int getWalkability() {
		return walkability;
	}

	public void setWalkability(int walkability) {
		this.walkability = walkability;
	}
	//TODO
	public void setWalkability(int walkability, DBConnection conn) {
		this.walkability = walkability;
	}

	public int getGreenspace() {
		return greenspace;
	}

	public void setGreenspace(int greenspace) {
		this.greenspace = greenspace;
	}
	//TODO
	public void setGreenspace(int greenspace, DBConnection conn) {
		this.greenspace = greenspace;
	}

	public int getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(int restaurants) {
		this.restaurants = restaurants;
	}
	//TODO
	public void setRestaurants(int restaurants, DBConnection conn) {
		this.restaurants = restaurants;
	}

	public int getBars() {
		return bars;
	}

	public void setBars(int bars) {
		this.bars = bars;
	}
	//TODO
	public void setBars(int bars, DBConnection conn) {
		this.bars = bars;
	}

	public int getAmenities() {
		return amenities;
	}

	public void setAmenities(int amenities) {
		this.amenities = amenities;
	}
	//TODO
	public void setAmenities(int amenities, DBConnection conn) {
		this.amenities = amenities;
	}

	public String getSafety() {
		return safety;
	}

	public void setSafety(String safety) {
		this.safety = safety;
	}
	//TODO
	public void setSafety(String safety, DBConnection conn) {
		this.safety = safety;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	//TODO
	public void setAdditionalInfo(String additionalInfo, DBConnection conn) {
		this.additionalInfo = additionalInfo;
	}

	public String getPreviousSearch() {
		return previousSearch;
	}

	public void setPreviousSearch(String previousSearch) {
		this.previousSearch = previousSearch;
	}
	//TODO
	public void setPreviousSearch(String previousSearch, DBConnection conn) {
		this.previousSearch = previousSearch;
	}

	public String getAptStyle() {
		return aptStyle;
	}

	public void setAptStyle(String aptStyle) {
		this.aptStyle = aptStyle;
	}
	//TODO
	public void setAptStyle(String aptStyle, DBConnection conn) {
		this.aptStyle = aptStyle;
	}

	public String getSchoolPerformance() {
		return schoolPerformance;
	}

	public void setSchoolPerformance(String schoolPerformance) {
		this.schoolPerformance = schoolPerformance;
	}
	//TODO
	public void setSchoolPerformance(String schoolPerformance, DBConnection conn) {
		this.schoolPerformance = schoolPerformance;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	//TODO
	public void setPhone(String phone, DBConnection conn) {
		this.phone = phone;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	//TODO
	public void setSummary(String summary, DBConnection conn) {
		this.summary = summary;
	}

	public String getAssigned() {
		return assigned;
	}

	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}
	//TODO
	public void setAssigned(String assigned, DBConnection conn) {
		this.assigned = assigned;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comment) {
		this.comments = comment;
	}
	//TODO
	public void setComments(String comment, DBConnection conn) {
		this.comments = comment;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String status) {
		this.currentStatus = status;
	}
	//TODO
	public void setCurrentStatus(String status, DBConnection conn) {
		this.currentStatus = status;
	}

	public Date getHandOffDate() {
		return handOffDate;
	}

	public void setHandOffDate(Date handOffDate) {
		this.handOffDate = handOffDate;
	}
	//TODO
	public void setHandOffDate(Date handOffDate, DBConnection conn) {
		this.handOffDate = handOffDate;
	}

	public ArrayList<Integer> getMatchedAreas() {
		return matchedAreas;
	}

	public void setMatchedAreas(ArrayList<Integer> matchedAreas, DBConnection conn) throws SQLException {
		this.matchedAreas = matchedAreas;
		conn.updateMatchedAreas(id, matchedAreas);
	}
	
	public void setMatchedAreas(ArrayList<Integer> matchedAreas) {
		this.matchedAreas = matchedAreas;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		for(UserObjectFields uof : UserObjectFields.values()){
			Method m;
			try {
				m = User.class.getMethod("get" + uof.toObjectField());
				Object o = m.invoke(this);
				if(uof.toJavaClass() != ArrayList.class){
					s.append(uof.toPrintString());
					s.append(" ");
					if(o != null)
						s.append(o.toString());
					else
						s.append("null");
					s.append("\n");
				}
				else{
					s.append(uof.toPrintString());
					s.append(" ");
					if(o != null){
						for(int i : new ArrayList<Integer>((ArrayList<Integer>) o)){
							s.append(i);
							s.append(" ");
						}
					}
					else
						s.append("null");
					s.append("\n");
				}
			} catch (NoSuchMethodException e) {
				log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} catch (SecurityException e) {
				log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} catch (IllegalAccessException e) {
				log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} catch (IllegalArgumentException e) {
				log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} catch (InvocationTargetException e) {
				log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}
		return s.toString();
	}
	
	public static void main(String[] args){
		ImportUsers iu = new ImportUsers();
		DBConnection db = null;
		try {
			db = new DBConnection();
			String results = "email+Address=efe@gg.ggg"
					+ "&Select+Your+Preferred+Regions=%5BLjava.lang.Object;@571fb1d"
					+ "&Mortgage+Down+Payment=20%25"
					+ "&How+are+things+going+with+your+Community+Search+Process?=I+haven't+started+any+research"
					+ "&Type+of+Housing+You+Want=I'm+open+to+options!"
					+ "&Bathrooms+You+Require=2%2B"
					+ "&First+Name=jon"
					+ "&Enter+Promo+Code+Here"
					+ "&Neighborhoods+or+Cities+You+are+Currently+Interested"
					+ "&Desired+Move+Date=01-01"
					+ "&Do+you+have+a+little+more+time+to+to+continue+with+Lifestyle+Questions?=Complete+later+by+email+or+phone"
					+ "&Reason+for+Your+Move=Relocating+from+outside+the+area"
					+ "&Provide+Work+Zip+Code=60615"
					+ "&If+applicable,+provide+other+resident's+work+Zip+Code&Maximum=5678901"
					+ "&Price+Range+for+Purchasing+a+Home.+Minimum=4567890"
					+ "&City+or+Neighborhood+You+Live+Now"
					+ "&Bedrooms+You+Require=2"
					+ "&Monthly+Payment+you+are+Comfortable+with+for+your+Total+Housing+Expense=1500"
					+ "&Please+provide+your+phone+number"
					+ "&Are+You+Buying+or+Renting?=Buying"
					+ "&MAXIMUM+Time+-+You+and+Other+Household+Members+will+Allow+for+Commuting+to+Work=2.0+hrs.";
			iu.addNewUser(results, db);
			ArrayList<User> users = db.getAllUsers();
			System.out.println(users.get(users.size()-1));
		} catch (ClassNotFoundException e) {
			if(db != null)
				db.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (SQLException e) {
			if(db != null)
				db.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (IllegalArgumentException e) {
			if(db != null)
				db.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (IOException e) {
			if(db != null)
				db.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (JSONException e) {
			if(db != null)
				db.close();
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}
}
