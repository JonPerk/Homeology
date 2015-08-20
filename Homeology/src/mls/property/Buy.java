package mls.property;

import mls.area.Area;

public class Buy extends Rent{
	private final double downPayment;
	
	Buy(Area area, int beds, boolean attached, double downPayment) {
		super(area, beds, attached);
		this.downPayment = downPayment;
	}
	
	public double getDownPayment(){
		return downPayment;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder(super.toString());
		s.append(" at ");
		s.append(downPayment * 100);
		s.append("% Down Payment");
		return s.toString();
	}
}
