package constants;

import java.util.ArrayList;
import java.util.Date;

public enum UserObjectFields {
	TIMESTAMP("Timestamp", Date.class),
	FNAME("Fname", String.class),
	MOVE_REASON("MoveReason", String.class),
	CURRENT_LOC("CurrentLoc", String.class),
	MOVE_DATE("MoveDate", Date.class),
	HOUSING_TYPE("HousingType", String.class),
	BEDS("Beds", int.class),
	BATHS("Baths", int.class),
	WORK_ZIP("WorkZip", int.class),
	WORK_ZIP_2("WorkZip2", int.class),
	COMMUTE_TIME("CommuteTime", int.class),
	TRANS_MODE("TransMode", String.class),
	DESIRED_LOCS("DesiredLocs", String.class),
	DESIRED_REGION("DesiredRegion", String.class),
	RENT_OR_BUY("RentOrBuy", String.class),
	EMAIL("Email", String.class),
	PROMO_CODE("PromoCode", String.class),
	PRICE_RANGE_BUY("PriceRangeBuy", double.class),
	MONTHLY_MORTGAGE("MonthlyMortgage", double.class),
	DOWN_PAYMENT("DownPayment", double.class),
	NEEDS_SCHOOL("NeedsSchool", boolean.class),
	MONTHLY_RENT("MonthlyRent", double.class),
	NEEDS_PARKING("NeedsParking", boolean.class),
	SCHOOL_LEVEL("SchoolLevel", String.class),
	SCHOOL_TYPE("SchoolType", String.class),
	MORE_QUESTIONS("MoreQuestions", String.class),
	CURRENT_LOC_LIKES_DISLIKES("CurrentLocLikesDislikes", String.class),
	IMPORTANT_ACTIVITIES("ImportantActivities", String.class),
	IMPORTANT_AREAS("ImportantAreas", String.class),
	WALKABILITY("Walkability", int.class),
	GREENSPACE("Greenspace", int.class),
	RESTAURANTS("Restaurants", int.class),
	BARS("Bars", int.class),
	AMENITIES("Amenities", int.class),
	SAFETY("Safety", int.class),
	ADDTIONAL_INFO("AdditionalInfo", String.class),
	PREVIOUS_SEARCH("PreviousSearch", String.class),
	APT_STYLE("AptStyle", String.class),
	SCHOOL_PERFORMANCE("SchoolPerformance", String.class),
	PHONE("Phone", String.class),
	SUMMARY("Summary", String.class),
	ASSIGNED("Assigned", String.class),
	COMMENT("Comment", String.class),
	STATUS("Status", String.class),
	HAND_OFF_DATE("HandOffDate", String.class),
	MATCHED_AREAS("MatchedAreas", ArrayList.class);
	
	private String info;
	private Class cl;
	
	private UserObjectFields(String s, Class c) {
		info = s;
		cl = c;
	}
	
	public String toString() {
		return info;
	}
	
	public Class toClass(){
		return cl;
	}
}
