package constants;

public enum ZoneSpeeds {
	ZERO(0, new int[] {20,20,25,25,30,30,30,30,30,30,30}),
	ONE(1, new int[] {20,25,25,25,30,30,35,35,35,30,30}),
	TWO(2, new int[] {25,25,25,25,30,30,35,35,35,35,35}),
	THREE(3, new int[] {25,25,25,30,30,30,30,35,35,30,30}),
	FOUR(4, new int[] {30,25,25,30,30,30,30,35,35,30,30}),
	FIVE(5, new int[] {30,30,30,30,30,35,35,35,35,35,35}),
	SIX(6, new int[] {30,35,35,30,30,35,35,35,35,30,35}),
	SEVEN(7, new int[] {30,35,35,35,35,35,35,40,40,30,35}),
	EIGHT(8, new int[] {30,30,35,30,30,35,35,40,40,35,35}),
	NINE(9, new int[] {30,30,35,30,30,35,30,30,35,40,40}),
	TEN(10, new int[] {30,30,35,30,30,35,35,35,35,40,40});
	
	private int zone;
	private int[] speeds;
	
	private ZoneSpeeds(int z, int[] s){
		zone = z;
		speeds = s;
	}
	
	public int getZone(){
		return zone;
	}
	
	public int[] getSpeeds(){
		return speeds;
	}
	
	public static ZoneSpeeds getZoneSpeed(int z){
		for(ZoneSpeeds zs : ZoneSpeeds.values()){
			if(z == zs.zone){
				return zs;
			}
		}
		return null;
	}
}
