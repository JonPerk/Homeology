package constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.sql.Types;

public enum UserObjectFields {
	ID("","Id", byte[].class, "Id", Types.BINARY, "ID"),
	CREATED("","Created", Date.class, "Created", Types.DATE, "Date Created"),
	MODIFIED("","Modified", Date.class, "Modified", Types.DATE,"Date Modified"),
	FNAME("First+Name","Fname", String.class, "Fname", Types.VARCHAR, "First Name"),
	MOVE_REASON("Reason+for+Your+Move","MoveReason", String.class, "Move_Reason", Types.VARCHAR, "Reason for Move"),
	CURRENT_LOC("City+or+Neighborhood+You+Live+Now","CurrentLoc", String.class, "Current_Loc", Types.VARCHAR, "Current City or Neighborhood"),
	MOVE_DATE("Desired+Move+Date","MoveDate", Date.class, "Move_Date", Types.DATE, "Desired Move Date"),
	HOUSING_TYPE("Type+of+Housing+You+Want", "HousingType", String.class, "Housing_Type", Types.VARCHAR, "Type of Housing Desired"),
	BEDS("Bedrooms+You+Require", "Beds", int.class, "Beds", Types.INTEGER ,"Bedrooms Required"),
	BATHS("Bathrooms+You+Require", "Baths", int.class, "Baths", Types.INTEGER, "Bathrooms Required"),
	WORK_ZIP("Provide+Work+Zip+Code", "WorkZip", int.class, "Work_Zip", Types.INTEGER, "Work Zip Code"),
	WORK_ZIP_2("If+applicable,+provide+other+resident's+work+Zip+Code", "WorkZip2", int.class, "Work_Zip_2", Types.INTEGER, "2nd Work Zip Code"),
	COMMUTE_TIME("MAXIMUM+Time+-+You+and+Other+Household+Members+will+Allow+for+Commuting+to+Work", "CommuteTime", double.class, "Commute_Time", Types.DOUBLE, "Max Commute Time"),
	TRANS_MODE("Mode+of+Transportation+to+Work", "TransMode", String.class, "Trans_Mode", Types.VARCHAR, "Transportation Mode"),
	DESIRED_LOCS("Neighborhoods+or+Cities+You+are+Currently+Interested", "DesiredLocs", String.class, "Desired_Locs", Types.VARCHAR, "Desired Locations"),
	DESIRED_REGION("Select+Your+Preferred+Regions", "DesiredRegion", String.class, "Desired_Region", Types.VARCHAR,"Desired Region"),
	RENT_OR_BUY("Are+You+Buying+or+Renting?", "RentOrBuy", String.class, "Rent_Or_Buy", Types.VARCHAR, "Renting or Buying"),
	EMAIL("email+Address", "Email", String.class, "Email", Types.VARCHAR, "Email"),
	PROMO_CODE("Enter+Promo+Code+Here", "PromoCode", String.class, "Promo_Code", Types.VARCHAR, "Promo Code"),
	PRICE_RANGE_BUY_MIN("Price+Range+for+Purchasing+a+Home.+Minimum", "PriceRangeBuyMin", double.class, "Price_Range_Buy_Min", Types.DOUBLE, "Min Purchase Price"),
	PRICE_RANGE_BUY_MAX("Maximum", "PriceRangeBuyMax", double.class, "Price_Range_Buy_Max", Types.DOUBLE, "Max Purchase Price"),
	MONTHLY_MORTGAGE("Monthly+Payment+you+are+Comfortable+with+for+your+Total+Housing+Expense", "MonthlyMortgage", double.class, "Monthly_Mortgage", Types.DOUBLE, "Max Monthly Mortgage"),
	DOWN_PAYMENT("Mortgage+Down+Payment", "DownPayment", double.class, "Down_Payment", Types.DOUBLE, "Mortgage Down Payment"),
	NEEDS_SCHOOL("Does+Performance+of+School+System+where+you+will+Live+Matter?", "NeedsSchool", boolean.class, "Needs_School", Types.BOOLEAN, "Needs School Information?"),
	MONTHLY_RENT("Monthly+Rent+you+are+Comfortable+with+for+your+Housing+Expense", "MonthlyRent", double.class, "Monthly_Rent", Types.DOUBLE, "Max Monthly Rent"),
	NEEDS_PARKING("Do+you+need+Parking?", "NeedsParking", boolean.class, "Needs_Parking", Types.BOOLEAN, "Parking Desired"),
	SCHOOL_LEVEL("Grades+of+Most+Interest", "SchoolLevel", String.class, "School_Level", Types.VARCHAR, "School Level"),
	SCHOOL_TYPE("Type+of+School", "SchoolType", String.class, "School_Type", Types.VARCHAR, "School Type"),
	MORE_QUESTIONS("Do+you+have+a+little+more+time+to+to+continue+with+Lifestyle+Questions?", "MoreQuestions", String.class, "More_Questions", Types.VARCHAR, "More Questions"),
	CURRENT_LOC_LIKES_DISLIKES("What+do+you+like+and+dislike+about+the+neighborhood+you+live+in+now?", "CurrentLocLikesDislikes", String.class, "Current_Loc_Likes_Dislikes", Types.VARCHAR, "Current Location Likes and Dislikes"),
	IMPORTANT_ACTIVITIES("Give+us+your+important+family+or+personal+activities:", "ImportantActivities", String.class, "Important_Activities", Types.VARCHAR, "Important Activities"),
	IMPORTANT_AREAS("Is+proximity+to+any+of+these+areas+important?", "ImportantAreas", String.class, "Important_Areas", Types.VARCHAR, "Important Areas"),
	WALKABILITY("How+important+is+walkability+on+a+scale+of+1+to+5?", "Walkability", int.class, "Walkability", Types.INTEGER, "Importance of Walkability"),
	GREENSPACE("How+important+is+green+space+on+a+scale+of+1+to+5?", "Greenspace", int.class, "Greenspace", Types.INTEGER, "Importance of Greenspace"),
	RESTAURANTS("How+important+is+having+lots+of+local+restaurants+on+a+scale+of+1+to+5?", "Restaurants", int.class, "Restaurants", Types.INTEGER, "Importance of Restaurants"),
	BARS("How+important+is+having+lots+of+local+bars+on+a+scale+of+1+to+5?", "Bars", int.class, "Bars", Types.INTEGER, "Importance of Bars"),
	AMENITIES("How+important+is+having+other+local+amenities+for+daily+tasks,+such+as+groceries,+dry+cleaning,+etc+on+a+scale+of+1+to+5?", "Amenities", int.class, "Amenities", Types.INTEGER, "Importance of Amenities"),
	SAFETY("Safety+is+important+to+all+of+us,+but+we+each+have+different+tolerances.", "Safety", String.class, "Safety", Types.VARCHAR, "Importance of Safety"),
	ADDTIONAL_INFO("Is+there+anything+else+you+want+to+share?", "AdditionalInfo", String.class, "Additional_Info", Types.VARCHAR, "Additional Info"),
	PREVIOUS_SEARCH("How+are+things+going+with+your+Community+Search+Process?", "PreviousSearch", String.class, "Previous_Search", Types.VARCHAR, "Previous Searching Done"),
	APT_STYLE("If+you+plan+to+live+in+a+condo/apartment,+what+style+of+living+do+you+prefer?", "AptStyle", String.class, "Apt_Style", Types.VARCHAR, "Condo/Apartment Style Preferred"),
	SCHOOL_PERFORMANCE("School+Performance+Desired", "SchoolPerformance", String.class, "School_Performance", Types.VARCHAR, "School Performance Desired"),
	PHONE("Please+provide+your+phone+number", "Phone", String.class,"Phone", Types.VARCHAR, "Phone"),
	SUMMARY("", "Summary", String.class, "Summary", Types.VARCHAR, "Summary"),
	ASSIGNED("", "Assigned", String.class, "Assigned", Types.VARCHAR, "Assigned to"),
	COMMENT("", "Comments", String.class, "Comments", Types.VARCHAR, "Comments"),
	CURRENT_STATUS("", "CurrentStatus", String.class, "Current_Status", Types.VARCHAR, "CurrentStatus"),
	HAND_OFF_DATE("", "HandOffDate", Date.class, "Hand_Off_Date", Types.DATE, "Hand-off Date"),
	MATCHED_AREAS("", "MatchedAreas", ArrayList.class, "Matched_Areas", Types.ARRAY, "Matched Areas");
	
	private String ff;
	private String of;
	private Class cl;
	private String db;
	private int dc;
	private String ps;
	
	private UserObjectFields(String f, String o, Class c, String d, int b, String p) {
		ff = f;
		of = o;
		cl = c;
		db = d;
		dc = b;
		ps = p;
	}
	
	public String toFormField(){
		return ff;
	}
	
	public String toObjectField(){
		return of;
	}
	
	public Class toJavaClass(){
		return cl;
	}
	
	public String toDBField(){
		return db;
	}
	
	public int toDBClass(){
		return dc;
	}
	
	public String toPrintString(){
		return ps;
	}
	
	public static UserObjectFields getUserObjectField(String s){
		for(UserObjectFields uof : UserObjectFields.values()){
			if(uof.toFormField().equals(s) || uof.toObjectField().equals(s) || uof.toDBField().equals(s) || uof.toPrintString().equals(s)){
				return uof;
			}
		}
		return null;
	}
}
