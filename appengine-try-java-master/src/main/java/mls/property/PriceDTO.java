package mls.property;

import java.util.Date;

public class PriceDTO {
	public double price;
	public double price1MonthOld;
	public double price2MonthOld;
	public int lastCount;
	public Date lastMonthUpdate;
	
	public PriceDTO(double p0, double p1, double p2, int c){
		price = p0;
		price1MonthOld = p1;
		price2MonthOld = p2;
		lastCount = c;
		lastMonthUpdate = new Date();
	}
	
	public PriceDTO(double p0, double p1, double p2, int c, Date d){
		price = p0;
		price1MonthOld = p1;
		price2MonthOld = p2;
		lastCount = c;
		lastMonthUpdate = d;
	}
}
