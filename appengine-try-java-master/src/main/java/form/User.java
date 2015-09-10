package form;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import constants.FormDBFields;
import constants.SpreadsheetFields;
import constants.UserObjectFields;

public class User {
	private Date timestamp;
	private String fname;
	private String moveReason;
	private String currentLoc;
	private Date moveDate;
	private String housingType; //possible enum
	private int beds;
	private int baths;
	private int workZip;
	private int workZip2;
	private int commuteTime;
	private String transMode; //possible enum
	private String desiredLocs;
	private String desiredRegion;
	private String rentOrBuy; //possible enum
	private String email;
	private String promoCode;
	private double priceRangeBuy;
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
	private String comment;
	private String status;
	private Date handOffDate;
	private ArrayList<Integer> matchedAreas;
	
	public User(){
		timestamp = new Date();
	}
	
	public User(HashMap<String, Object> args){
		timestamp = new Date();
		for(FormDBFields s : FormDBFields.values()){
			if(args.containsKey(s.toString())){
				try{
					Method m = User.class.getMethod("set" + UserObjectFields.valueOf(s.name()).toString(), UserObjectFields.valueOf(s.name()).toClass());
					m.invoke(this, args.get(s.toString()));
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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

	public int getCommuteTime() {
		return commuteTime;
	}

	public void setCommuteTime(int commuteTime) {
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

	public double getPriceRangeBuy() {
		return priceRangeBuy;
	}

	public void setPriceRangeBuy(double priceRangeBuy) {
		this.priceRangeBuy = priceRangeBuy;
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

	public boolean isNeedsSchool() {
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

	public boolean isNeedsParking() {
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public void setMatchedAreas(ArrayList<Integer> matchedAreas) {
		this.matchedAreas = matchedAreas;
	}
	
	
}
