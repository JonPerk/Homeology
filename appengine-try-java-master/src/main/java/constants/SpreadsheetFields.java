package constants;

public enum SpreadsheetFields {
	FNAME("First+Name","Fname"),
	MOVE_REASON("Reason+for+Your+Move","MoveReason"),
	CURRENT_LOC("City+or+Neighborhood+You+Live+Now","CurrentLoc"),
	MOVE_DATE("Desired+Move+Date","MoveDate"),
	HOUSING_TYPE("Type+of+Housing+You+Want","HousingType"),
	BEDS("Bedrooms+You+Require","Beds"),
	BATHS("Bathrooms+You+Require","Baths"),
	WORK_ZIP("Provide+Work+Zip+Code","WorkZip"),
	WORK_ZIP_2("If+applicable,+provide+other+resident's+work+Zip+Code","WorkZip2"),
	COMMUTE_TIME("MAXIMUM+Time+-+You+and+Other+Household+Members+will+Allow+for+Commuting+to+Work","CommuteTime"),
	TRANS_MODE("Mode+of+Transportation+to+Work","TransMode"),
	DESIRED_LOCS("Neighborhoods+or+Cities+You+are+Currently+Interested","DesiredLocs"),
	DESIRED_REGION("Select+Your+Preferred+Regions","DesiredRegion"),
	RENT_OR_BUY("Are+You+Buying+or+Renting?","RentOrBuy"),
	EMAIL("email+Address","Email"),
	PROMO_CODE("Enter+Promo+Code+Here","PromoCode"),
	PRICE_RANGE_BUY_MIN("Price+Range+for+Purchasing+a+Home.+Minimum","PriceRangeBuyMin"),
	PRICE_RANGE_BUY_MAX("Maximum","PriceRangeBuyMax"),
	MONTHLY_MORTGAGE("Monthly+Payment+you+are+Comfortable+with+for+your+Total+Housing+Expense","MonthlyMortgage"),
	DOWN_PAYMENT("Mortgage+Down+Payment","DownPayment"),
	NEEDS_SCHOOL("Does+Performance+of+School+System+where+you+will+Live+Matter?","NeedsSchool"),
	MONTHLY_RENT("Monthly+Rent+you+are+Comfortable+with+for+your+Housing+Expense","MonthlyRent"),
	NEEDS_PARKING("Do+you+need+Parking?","NeedsParking"),
	SCHOOL_LEVEL("Grades+of+Most+Interest","SchoolLevel"),
	SCHOOL_TYPE("Type+of+School","SchoolType"),
	MORE_QUESTIONS("Do+you+have+a+little+more+time+to+to+continue+with+Lifestyle+Questions?","MoreQuestions"),
	CURRENT_LOC_LIKES_DISLIKES("What+do+you+like+and+dislike+about+the+neighborhood+you+live+in+now?","CurrentLocLikesDislikes"),
	IMPORTANT_ACTIVITIES("Give+us+your+important+family+or+personal+activities:","ImportantActivities"),
	IMPORTANT_AREAS("Is+proximity+to+any+of+these+areas+important?","ImportantAreas"),
	WALKABILITY("How+important+is+walkability+on+a+scale+of+1+to+5?","Walkability"),
	GREENSPACE("How+important+is+green+space+on+a+scale+of+1+to+5?","Greenspace"),
	RESTAURANTS("How+important+is+having+lots+of+local+restaurants+on+a+scale+of+1+to+5?","Restaurants"),
	BARS("How+important+is+having+lots+of+local+bars+on+a+scale+of+1+to+5?","Bars"),
	AMENITIES("How+important+is+having+other+local+amenities+for+daily+tasks,+such+as+groceries,+dry+cleaning,+etc+on+a+scale+of+1+to+5?","Amenities"),
	SAFETY("Safety+is+important+to+all+of+us,+but+we+each+have+different+tolerances.","Safety"),
	ADDTIONAL_INFO("Is+there+anything+else+you+want+to+share?","AdditionalInfo"),
	PREVIOUS_SEARCH("How+are+things+going+with+your+Community+Search+Process?","PreviousSearch"),
	APT_STYLE("If+you+plan+to+live+in+a+condo/apartment,+what+style+of+living+do+you+prefer?","AptStyle"),
	SCHOOL_PERFORMANCE("School+Performance+Desired","SchoolPerformance"),
	PHONE("Please+provide+your+phone+number","Phone");
	
	private String info;
	private String userField;
	
	private SpreadsheetFields(String s, String u) {
		info = s;
		userField = u;
	}
	
	public String toString() {
		return info;
	}
	
	public String toUserField() {
		return userField;
	}
}
