package constants;

public enum Zones {
	Cook(0, 20),
	DuPage(1, 25),
	Lake(3, 30),
	McHenry(3, 30),
	Kane(4, 30),
	Kendall(4, 30),
	Grundy(5, 35),
	DeKalb(6, 35),
	LaSalle(6, 35),
	Boone(7, 40),
	Winnebago(7, 40),
	Ogle(8, 40),
	Lee(8, 40),
	Kankakee(9, 40),
	Iroquois(9, 40),
	Ford(9, 40),
	Livingston(10, 40);
	
	private int zone;
	private int speed;
	
	private Zones(int z, int s){
		zone = z;
		speed = s;
	}
	
	public int getZone(){
		return zone;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public static void main(String[] args){
		System.out.println(Zones.valueOf("Lake").getZone());
	}
}
