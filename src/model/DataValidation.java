package model;

public class DataValidation {
	public static boolean checkLessThanFifty(String str){
		return (str.length() < 51);
	}
	
	private static String escape(String str){
		str = str.replace("\\", "\\\\");
		str = str.replace("_", "\\_");
		str = str.replace("\n", "\\n");
		return str;
	}
	
	private static String unescape(String str){
		str = str.replace("\\\\", "\\");
		str = str.replace("\\_", "_");
		str = str.replace("\\n", "\n");
		return str;
	}
}
