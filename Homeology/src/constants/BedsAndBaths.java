package constants;

public enum BedsAndBaths {
	MAX_BEDS(5),
	MIN_BEDS(1),
	MAX_BATHS(3),
	MIN_BATHS(1);
	
	private int num;
	
	private BedsAndBaths(int i){
		num = i;
	}
	
	public int getInt(){
		return num;
	}
}
