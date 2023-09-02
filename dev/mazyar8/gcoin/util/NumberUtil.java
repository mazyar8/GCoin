package dev.mazyar8.gcoin.util;

public class NumberUtil {

	/** if String contains only numbers, will return true */
	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
