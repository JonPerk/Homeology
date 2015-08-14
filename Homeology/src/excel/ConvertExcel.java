package excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

public class ConvertExcel {
	//can be deprecated after we pull objects from google api
	public JSONObject CSVtoJSON(String filename){
		JSONObject results = new JSONObject();
		ArrayList<String> colNames = new ArrayList<String>();
		try{
            FileInputStream file = new FileInputStream(new File(filename));
 
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            boolean first = true;
            int len = 0;
            ArrayList<JSONObject> rows = new ArrayList<JSONObject>();
            while (rowIterator.hasNext()){
            	int count = 0;
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                JSONObject temp = new JSONObject();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()){
                        case Cell.CELL_TYPE_NUMERIC:
                            if(first){
                            	colNames.add(Double.toString(cell.getNumericCellValue()));
                            }
                            else{
                            	temp.put(colNames.get(cell.getColumnIndex()), cell.getNumericCellValue());
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            if(first){
                            	colNames.add(cell.getStringCellValue());
                            }
                            else{
                            	temp.put(colNames.get(cell.getColumnIndex()), cell.getStringCellValue());
                            }
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                        	if(first){
                        		colNames.add(cell.getCellFormula());
                        	}
                        	else{
                        		temp.put(colNames.get(cell.getColumnIndex()), cell.getCellFormula().replaceAll("\"", ""));
                            }
                        	break;
                    }
                }
                if(first){
                	len = colNames.size();
                	first = false;
                }
                else if(!first && count == len){
                    count++;
                	break;
                }
                else{
                	rows.add(temp);
                }
            }
            results.put("results", rows);
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return results;
    }
}
