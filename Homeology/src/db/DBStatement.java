package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import constants.DownPayTypes;
import mls.area.Area;
import mls.area.AreaFactory;
import mls.property.Buy;
import mls.property.PropertyFactory;
import mls.property.Rent;

public class DBStatement {
	private DBConnection conn;
	
	public DBStatement() throws ClassNotFoundException, SQLException{
		conn = DBConnection.getInstance();
	}
	
	public void createArea(Area area) throws ClassNotFoundException, SQLException{
		conn = DBConnection.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO CITIES (MLS_ID, CITY) VALUES (");
		sql.append(area.getArea());
		sql.append(", '");
		sql.append(area.getCity());
		sql.append("');");
		conn.doStatement(sql.toString());
		for(int i : area.getZips()){
			sql.setLength(0);
			sql.append("INSERT INTO ZIP_CODES (MLS_ID, ZIP) VALUES (");
			sql.append(area.getArea());
			sql.append(", ");
			sql.append(i);
			sql.append(");");
			conn.doStatement(sql.toString());
		}
	}
	
	public void updateAreaCity(Area area) throws ClassNotFoundException, SQLException{
		conn = DBConnection.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE CITIES SET CITY = ");
		sql.append(area.getCity());
		sql.append("\nWHERE MLS_ID = ");
		sql.append(area.getArea());
		sql.append(";");
		conn.doStatement(sql.toString());
	}
	
	public void updateAreaZips(Area area) throws ClassNotFoundException, SQLException{
		conn = DBConnection.getInstance();
		StringBuilder sql = new StringBuilder();
		for(int i : area.getZips()){
			sql.setLength(0);
			sql.append("BEGIN\nIF NOT EXISTS (SELECT * FROM ZIP_CODES\nWHERE MLS_ID = ");
			sql.append(area.getArea());
			sql.append(" AND ZIP = ");
			sql.append(i);
			sql.append(")\nBEGIN\nINSERT INTO ZIP_CODES (MLS_ID, ZIP) VALUES (");
			sql.append(area.getArea());
			sql.append(", ");
			sql.append(i);
			sql.append(");\nEND\nEND");
			conn.doStatement(sql.toString());
		}
	}
	
	public void createRent(Rent rent) throws ClassNotFoundException, SQLException{
		conn = DBConnection.getInstance();
		StringBuilder sql = new StringBuilder();
		String sqlBase;
		sql.append("INSERT INTO RENT (AREA, BEDS, BATHS) VALUES (");
		sql.append(rent.getArea().getArea());
		sql.append(", ");
		sql.append(rent.getBeds());
		sql.append(", ");
		sqlBase = sql.toString();
		sql.append("1);");
		conn.doStatement(sql.toString());
		sql.setLength(0);
		sql.append(sqlBase);
		sql.append("2);");
		conn.doStatement(sql.toString());
		sql.setLength(0);
		sql.append(sqlBase);
		sql.append("3);");
		conn.doStatement(sql.toString());
	}
	
	public void updatePrice1Bath(Rent rent) throws ClassNotFoundException, SQLException{
		conn = DBConnection.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE RENT SET PRICE = ");
		sql.append(rent.getPrice1Bath());
		sql.append("\nWHERE MLS_ID = ");
		sql.append(rent.getArea().getArea());
		sql.append(" AND BEDS = ");
		sql.append(rent.getBeds());
		sql.append(" AND BATHS = 1;");
		conn.doStatement(sql.toString());
	}
	
	public void updatePrice2Bath(Rent rent) throws ClassNotFoundException, SQLException{
		conn = DBConnection.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE RENT SET PRICE = ");
		sql.append(rent.getPrice1Bath());
		sql.append("\nWHERE MLS_ID = ");
		sql.append(rent.getArea().getArea());
		sql.append(" AND BEDS = ");
		sql.append(rent.getBeds());
		sql.append(" AND BATHS = 2;");
		conn.doStatement(sql.toString());
	}
	
	public void updatePrice3Bath(Rent rent) throws ClassNotFoundException, SQLException{
		conn = DBConnection.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE RENT SET PRICE = ");
		sql.append(rent.getPrice1Bath());
		sql.append("\nWHERE MLS_ID = ");
		sql.append(rent.getArea().getArea());
		sql.append(" AND BEDS = ");
		sql.append(rent.getBeds());
		sql.append(" AND BATHS = 3;");
		conn.doStatement(sql.toString());
	}
	
	public void updatePrice1Bath(Buy buy) throws ClassNotFoundException, SQLException{
		String tableName = getTableName(buy.getDownPayment());
		conn = DBConnection.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ");
		sql.append(tableName);
		sql.append(" SET PRICE = ");
		sql.append(buy.getPrice1Bath());
		sql.append("\nWHERE MLS_ID = ");
		sql.append(buy.getArea().getArea());
		sql.append(" AND BEDS = ");
		sql.append(buy.getBeds());
		sql.append(" AND BATHS = 1;");
		conn.doStatement(sql.toString());
	}
	
	public void updatePrice2Bath(Buy buy) throws ClassNotFoundException, SQLException{
		String tableName = getTableName(buy.getDownPayment());
		conn = DBConnection.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ");
		sql.append(tableName);
		sql.append(" SET PRICE = ");
		sql.append(buy.getPrice1Bath());
		sql.append("\nWHERE MLS_ID = ");
		sql.append(buy.getArea().getArea());
		sql.append(" AND BEDS = ");
		sql.append(buy.getBeds());
		sql.append(" AND BATHS = 2;");
		conn.doStatement(sql.toString());
	}
	
	public void updatePrice3Bath(Buy buy) throws ClassNotFoundException, SQLException{
		String tableName = getTableName(buy.getDownPayment());
		conn = DBConnection.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ");
		sql.append(tableName);
		sql.append(" SET PRICE = ");
		sql.append(buy.getPrice1Bath());
		sql.append("\nWHERE MLS_ID = ");
		sql.append(buy.getArea().getArea());
		sql.append(" AND BEDS = ");
		sql.append(buy.getBeds());
		sql.append(" AND BATHS = 3;");
		conn.doStatement(sql.toString());
	}
	
	public void createBuy(Buy buy) throws ClassNotFoundException, SQLException{
		String tableName = getTableName(buy.getDownPayment());
		conn = DBConnection.getInstance();
		StringBuilder sql = new StringBuilder();
		String sqlBase;
		sql.append("INSERT INTO ");
		sql.append(tableName);
		sql.append("AREA, BEDS, BATHS) VALUES (");
		sql.append(buy.getArea().getArea());
		sql.append(", ");
		sql.append(buy.getBeds());
		sql.append(", ");
		sqlBase = sql.toString();
		sql.append("1);");
		conn.doStatement(sql.toString());
		sql.setLength(0);
		sql.append(sqlBase);
		sql.append("2);");
		conn.doStatement(sql.toString());
		sql.setLength(0);
		sql.append(sqlBase);
		sql.append("3);");
		conn.doStatement(sql.toString());
	}
	
	
	public void getAllAreas() throws ClassNotFoundException, SQLException{
		conn = DBConnection.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CITIES.MLS_ID, CITIES.CITY, ZIP_CODES.ZIP  FROM CITIES, ZIP_CODES\n");
		sql.append("WHERE CITIES.MLS_ID = ZIP_CODES.MLS_ID;");
		ResultSet rs = conn.doStatement(sql.toString());
		while(rs.next()){
			AreaFactory.makeArea(rs.getInt(0), rs.getString(1), new HashSet<Integer>(rs.getInt(2)));
		}
	}
	
	public void getAllRents() throws ClassNotFoundException, SQLException{
		conn = DBConnection.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MLS_ID, PRICE, BEDS, BATHS FROM RENTS;");
		ResultSet rs = conn.doStatement(sql.toString());
		while(rs.next()){
			Rent r = PropertyFactory.makeRent(rs.getInt(0), rs.getInt(2), false);
			int baths = rs.getInt(3);
			switch(baths){
			case 1:
				r.setPrice1Bath(rs.getDouble(1));
				break;
			case 2:
				r.setPrice2Bath(rs.getDouble(1));
				break;
			case 3:
				r.setPrice3Bath(rs.getDouble(1));
				break;
			}
		}
	}
	
	public void getAllBuys() throws ClassNotFoundException, SQLException{
		getAllBuysAtDP(0.05);
		getAllBuysAtDP(0.1);
		getAllBuysAtDP(0.2);
	}
	
	public void getAllBuysAtDP(double dp) throws ClassNotFoundException, SQLException{
		conn = DBConnection.getInstance();
		StringBuilder sql = new StringBuilder();
		String tableName = getTableName(dp);
		sql.append("SELECT MLS_ID, PRICE, BEDS, BATHS FROM ");
		sql.append(tableName);
		sql.append(";");
		ResultSet rs = conn.doStatement(sql.toString());
		while(rs.next()){
			Rent r = PropertyFactory.makeRent(rs.getInt(0), rs.getInt(2), false);
			int baths = rs.getInt(3);
			switch(baths){
			case 1:
				r.setPrice1Bath(rs.getDouble(1));
				break;
			case 2:
				r.setPrice2Bath(rs.getDouble(1));
				break;
			case 3:
				r.setPrice3Bath(rs.getDouble(1));
				break;
			}
		}
	}
	
	private String getTableName(double dp){
		String tableName = "";
		if(dp == DownPayTypes.DP5Percent.getDouble()){
			tableName = "FIVE_PER";
		}
		else if(dp == DownPayTypes.DP10Percent.getDouble()){
			tableName = "TEN_PER";
		}
		else{
			tableName = "TWENTY_PER";
		}
		return tableName;
	}
}
