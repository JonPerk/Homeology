package constants;

public enum KeeperFields {
	AREA_FIELD("Area"),
	CITY_FIELD("City"),
	COUNTY_FIELD("County"),
	BUY_PRICE_FIELD("Sold Price"),
	RENT_PRICE_FIELD("RP/RNP"),
	RENT_PRICE_FIELD_2("Rent Search Price"),
	BUY_ZIP_FIELD("Zip Code"),
	RENT_ZIP_FIELD("Zip"),
	FULL_BATH_FIELD("# Full Baths"),
	HALF_BATH_FIELD("# Half Baths"),
	BOTH_BATH_FIELD("Total Full/Half Baths"),
	BEDS_FIELD_1("Bedrooms - All Levels"),
	BEDS_FIELD_2("# Bedrooms"),
	BEDS_FIELD_3("All Beds");
	
	private String info;
	
	private KeeperFields(String s) {
		info = s;
	}
	
	public String toString() {
		return info;
	}
}
