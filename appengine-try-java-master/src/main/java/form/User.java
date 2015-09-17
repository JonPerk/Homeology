package form;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.sql.SQLException;
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
	private int safety;
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
	
	public User(HashMap<String, Object> args) throws SQLException{
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

	public Date getCreated() {
		return created;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getMoveReason() {
		return moveReason;
	}

	public void setMoveReason(String moveReason) {
		this.moveReason = moveReason;
	}

	public String getCurrentLoc() {
		return currentLoc;
	}

	public void setCurrentLoc(String currentLoc) {
		this.currentLoc = currentLoc;
	}

	public Date getMoveDate() {
		return moveDate;
	}

	public void setMoveDate(Date moveDate) {
		this.moveDate = moveDate;
	}

	public String getHousingType() {
		return housingType;
	}

	public void setHousingType(String housingType) {
		this.housingType = housingType;
	}

	public int getBeds() {
		return beds;
	}

	public void setBeds(int beds) {
		this.beds = beds;
	}

	public int getBaths() {
		return baths;
	}

	public void setBaths(int baths) {
		this.baths = baths;
	}

	public int getWorkZip() {
		return workZip;
	}

	public void setWorkZip(int workZip) {
		this.workZip = workZip;
	}

	public int getWorkZip2() {
		return workZip2;
	}

	public void setWorkZip2(int workZip2) {
		this.workZip2 = workZip2;
	}

	public double getCommuteTime() {
		return commuteTime;
	}

	public void setCommuteTime(double commuteTime) {
		this.commuteTime = commuteTime;
	}

	public String getTransMode() {
		return transMode;
	}

	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}

	public String getDesiredLocs() {
		return desiredLocs;
	}

	public void setDesiredLocs(String desiredLocs) {
		this.desiredLocs = desiredLocs;
	}

	public String getDesiredRegion() {
		return desiredRegion;
	}

	public void setDesiredRegion(String desiredRegion) {
		this.desiredRegion = desiredRegion;
	}

	public String getRentOrBuy() {
		return rentOrBuy;
	}

	public void setRentOrBuy(String rentOrBuy) {
		this.rentOrBuy = rentOrBuy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public double getPriceRangeBuyMin() {
		return priceRangeBuyMin;
	}

	public void setPriceRangeBuyMin(double priceRangeBuy) {
		this.priceRangeBuyMin = priceRangeBuy;
	}
	
	public double getPriceRangeBuyMax() {
		return priceRangeBuyMax;
	}

	public void setPriceRangeBuyMax(double priceRangeBuy) {
		this.priceRangeBuyMax = priceRangeBuy;
	}

	public double getMonthlyMortgage() {
		return monthlyMortgage;
	}

	public void setMonthlyMortgage(double monthlyMortgage) {
		this.monthlyMortgage = monthlyMortgage;
	}

	public double getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(double downPayment) {
		this.downPayment = downPayment;
	}

	public boolean getNeedsSchool() {
		return needsSchool;
	}

	public void setNeedsSchool(boolean needsSchool) {
		this.needsSchool = needsSchool;
	}

	public double getMonthlyRent() {
		return monthlyRent;
	}

	public void setMonthlyRent(double monthlyRent) {
		this.monthlyRent = monthlyRent;
	}

	public boolean getNeedsParking() {
		return needsParking;
	}

	public void setNeedsParking(boolean needsParking) {
		this.needsParking = needsParking;
	}

	public String getSchoolLevel() {
		return schoolLevel;
	}

	public void setSchoolLevel(String schoolLevel) {
		this.schoolLevel = schoolLevel;
	}

	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	public String getMoreQuestions() {
		return moreQuestions;
	}

	public void setMoreQuestions(String moreQuestions) {
		this.moreQuestions = moreQuestions;
	}

	public String getCurrentLocLikesDislikes() {
		return currentLocLikesDislikes;
	}

	public void setCurrentLocLikesDislikes(String currentLocLikesDislikes) {
		this.currentLocLikesDislikes = currentLocLikesDislikes;
	}

	public String getImportantActivities() {
		return importantActivities;
	}

	public void setImportantActivities(String importantActivities) {
		this.importantActivities = importantActivities;
	}

	public String getImportantAreas() {
		return importantAreas;
	}

	public void setImportantAreas(String importantAreas) {
		this.importantAreas = importantAreas;
	}

	public int getWalkability() {
		return walkability;
	}

	public void setWalkability(int walkability) {
		this.walkability = walkability;
	}

	public int getGreenspace() {
		return greenspace;
	}

	public void setGreenspace(int greenspace) {
		this.greenspace = greenspace;
	}

	public int getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(int restaurants) {
		this.restaurants = restaurants;
	}

	public int getBars() {
		return bars;
	}

	public void setBars(int bars) {
		this.bars = bars;
	}

	public int getAmenities() {
		return amenities;
	}

	public void setAmenities(int amenities) {
		this.amenities = amenities;
	}

	public int getSafety() {
		return safety;
	}

	public void setSafety(int safety) {
		this.safety = safety;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getPreviousSearch() {
		return previousSearch;
	}

	public void setPreviousSearch(String previousSearch) {
		this.previousSearch = previousSearch;
	}

	public String getAptStyle() {
		return aptStyle;
	}

	public void setAptStyle(String aptStyle) {
		this.aptStyle = aptStyle;
	}

	public String getSchoolPerformance() {
		return schoolPerformance;
	}

	public void setSchoolPerformance(String schoolPerformance) {
		this.schoolPerformance = schoolPerformance;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAssigned() {
		return assigned;
	}

	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comment) {
		this.comments = comment;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String status) {
		this.currentStatus = status;
	}

	public Date getHandOffDate() {
		return handOffDate;
	}

	public void setHandOffDate(Date handOffDate) {
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
	
	public static void main(String[] args){
		ImportUsers iu = new ImportUsers();
		DBConnection db = null;
		try {
			db = new DBConnection();
			ImportMLS im = new ImportMLS();
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
					+ "&Provide+Work+Zip+Code=60614"
					+ "&If+applicable,+provide+other+resident's+work+Zip+Code&Maximum=5678901"
					+ "&Price+Range+for+Purchasing+a+Home.+Minimum=4567890"
					+ "&City+or+Neighborhood+You+Live+Now"
					+ "&Bedrooms+You+Require=2"
					+ "&Monthly+Payment+you+are+Comfortable+with+for+your+Total+Housing+Expense=1500"
					+ "&Please+provide+your+phone+number"
					+ "&Are+You+Buying+or+Renting?=Buying"
					+ "&MAXIMUM+Time+-+You+and+Other+Household+Members+will+Allow+for+Commuting+to+Work=0.5 hr.";
			iu.addNewUser(results, db);
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
