package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import mls.ImportMLS;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;

import constants.KeeperFields;
import db.DBConnection;
import exception.SpreadSheetSizeException;

public class ConvertExcel {
	private final ArrayList<String> keeperFields = new ArrayList<String>();
	
	public ConvertExcel(){
		for(KeeperFields k : KeeperFields.values()){
			keeperFields.add(k.toString());
		}
	}
	
	public JSONObject CSVtoJSON(/*String filename*/ InputStream is, DBConnection conn) throws SpreadSheetSizeException{
		JSONObject results = new JSONObject();
		HashMap<Integer,String> colNames = new HashMap<Integer,String>();
		try{
            //FileInputStream file = new FileInputStream(new File(filename));
 
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            //Iterate through each rows one by one
            if(sheet.getLastRowNum() > 1001){
            	throw new SpreadSheetSizeException("SpreadSheet has too many rows");
            }
            Iterator<Row> rowIterator = sheet.iterator();
            boolean first = true;
            ArrayList<JSONObject> rows = new ArrayList<JSONObject>();
            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                if(first){
	                Iterator<Cell> cellIterator = row.cellIterator();
	                int count = 0;
	                while (cellIterator.hasNext()){
	                    Cell cell = cellIterator.next();
	                    int type = cell.getCellType();
	                    if(type == Cell.CELL_TYPE_STRING){
	                    	String s = cell.getStringCellValue();
	                    	if(keeperFields.contains(s)){
	                    		colNames.put(cell.getColumnIndex(), s);
	                    	}
	                    }
	                    else if(type == Cell.CELL_TYPE_BLANK){
	                    	first = false;
	                    	break;
	                    }
	                    count++;
	                    if(count > 10){
	                    	throw new SpreadSheetSizeException("SpreadSheet has too many columns");
	                    }
	                }
	                first = false;
                }
                else{
                	if(row.getFirstCellNum() != -1){
	                	JSONObject temp = new JSONObject();
	                	for(int i : colNames.keySet()){
	                		Cell cell = row.getCell(i);
		                    //Check the cell type and format accordingly
		                    switch (cell.getCellType()){
		                        case Cell.CELL_TYPE_NUMERIC:
		                            temp.put(colNames.get(i), cell.getNumericCellValue());
		                            break;
		                        case Cell.CELL_TYPE_STRING:
		                            temp.put(colNames.get(i), cell.getStringCellValue());
		                            break;
		                        case Cell.CELL_TYPE_FORMULA:
		                        	temp.put(colNames.get(i), cell.getCellFormula().replaceAll("\"", ""));
		                        	break;
		                    }
	                	}
	                	rows.add(temp);
                	}
                }
            }
            results.put("results", rows);
            is.close();
            workbook.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return results;
    }
	
	public JSONObject testMethod(String filename, DBConnection conn) throws FileNotFoundException, ClassNotFoundException, SQLException, SpreadSheetSizeException{
		FileInputStream file = new FileInputStream(new File(filename));
		return CSVtoJSON(file, conn);
	}
	public static void main(String[] args) throws SpreadSheetSizeException{
		ConvertExcel ce = new ConvertExcel();
		String path = "C:\\Users\\L096026\\Documents\\Homeology\\Homeology\\";
		String file = "buyAtt2b3";
		String ext = ".xlsx";
		JSONObject json = new JSONObject();
		DBConnection conn = null;
		try {
			conn = new DBConnection();
			json = ce.testMethod(path + file + ext, conn);
			ImportMLS im = new ImportMLS();
			im.doImport(json, conn);
			conn.close();
		} catch (FileNotFoundException e) {
			if(conn != null)
				conn.close();
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			if(conn != null)
				conn.close();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			if(conn != null)
				conn.close();
			e.printStackTrace();
		} catch (JSONException e) {
			if(conn != null)
				conn.close();
			e.printStackTrace();
		} catch (SQLException e) {
			if(conn != null)
				conn.close();
			e.printStackTrace();
		}
		
	}
}
