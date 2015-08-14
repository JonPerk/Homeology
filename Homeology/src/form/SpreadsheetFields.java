package form;

public enum SpreadsheetFields {
	TIMESTAMP("Timestamp"),
	FNAME("First Name"),
	MOVE_REASON("Reason for Your Move"),
	CURRENT_LOC("City or Neighborhood You Live Now"),
	MOVE_DATE("Desired Move Date"),
	HOUSING_TYPE("Type of Housing You Want"),
	BEDS("Bedrooms You Require"),
	BATHS("Bathrooms You Require"),
	WORK_ZIP("Provide Work Zip Code"),
	WORK_ZIP_2("If applicable, provide other resident's work Zip Code"),
	COMMUTE_TIME("MAXIMUM Time - You and Other Household Members will Allow for Commuting to Work"),
	TRANS_MODE("Mode of Transportation to Work"),
	DESIRED_LOCS("Neighborhoods or Cities You are Currently Interested"),
	DESIRED_REGION("Select Your Preferred Regions"),
	RENT_OR_BUY("Are You Buying or Renting?"),
	EMAIL("email Address"),
	PROMO_CODE("Enter Promo Code Here"),
	PRICE_RANGE_BUY("Price Range for Purchasing a Home"),
	MONTHLY_MORTGAGE("Monthly Payment you are Comfortable with for your Total Housing Expense"),
	DOWN_PAYMENT("Mortgage Down Payment"),
	NEEDS_SCHOOL("Does Performance of School System where you will Live Matter?"),
	MONTHLY_RENT("Monthly Rent you are Comfortable with for your Housing Expense"),
	NEEDS_PARKING("Do you need Parking?"),
	SCHOOL_LEVEL("Grades of Most Interest"),
	SCHOOL_TYPE("Type of School"),
	MORE_QUESTIONS("Do you have a little more time to to continue with Lifestyle Questions?"),
	CURRENT_LOC_LIKES_DISLIKES("What do you like and dislike about the neighborhood you live in now?"),
	IMPORTANT_ACTIVITIES("Give us your important family or personal activities:"),
	IMPORTANT_AREAS("Is proximity to any of these areas important?"),
	WALKABILITY("How important is walkability on a scale of 1 to 5?"),
	GREENSPACE("How important is green space on a scale of 1 to 5?"),
	RESTAURANTS("How important is having lots of local restaurants on a scale of 1 to 5?"),
	BARS("How important is having lots of local bars on a scale of 1 to 5?"),
	AMENITIES("How important is having other local amenities for daily tasks, such as groceries, dry cleaning, etc on a scale of 1 to 5?"),
	SAFETY("Safety is important to all of us, but we each have different tolerances."),
	ADDTIONAL_INFO("Is there anything else you want to share?"),
	PREVIOUS_SEARCH("How are things going with your Community Search Process?"),
	APT_STYLE("If you plan to live in a condo/apartment, what style of living do you prefer?"),
	SCHOOL_PERFORMANCE("School Performance Desired"),
	PHONE("Please provide your phone number"),
	SUMMARY("Summary"),
	ASSIGNED("Assigned"),
	COMMENT("Comment"),
	STATUS("Status"),
	HAND_OFF_DATE("Date of Hand Off");
	
	private String info;
	
	private SpreadsheetFields(String s) {
		info = s;
	}
	
	public String toString() {
		return info;
	}
}
