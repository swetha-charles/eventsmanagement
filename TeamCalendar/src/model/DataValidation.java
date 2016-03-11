package model;

public class DataValidation {
	public static boolean checkLessThanFifty(String... str){
		for(String s : str){
			if(s.length()>51){
				return false;
			}
		}
		
		return true;
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

	public static boolean checkLessThanTwenty(String username) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean checkLessThanThirty(String name) {
		// TODO Auto-generated method stub
		return true;
	}
}
