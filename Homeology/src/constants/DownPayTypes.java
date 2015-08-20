package constants;

public enum DownPayTypes {
	DP5Percent(0.05),
	DP10Percent(0.1),
	DP20Percent(0.2);
	
	private double dp;
	
	private DownPayTypes(double d){
		dp = d;
	}
	
	public double getDouble(){
		return dp;
	}
}
