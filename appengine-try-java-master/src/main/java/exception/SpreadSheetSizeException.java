package exception;

public class SpreadSheetSizeException extends Exception {
	public SpreadSheetSizeException(){
		super();
	}
	
	public SpreadSheetSizeException(String message){
		super(message);
	}
}
