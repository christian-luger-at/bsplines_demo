package at.runaway.splinedemo.utils;

import java.util.List;

public class Helper {

	/**
	 * Checks if the given index is valid for a concrete list
	 * 
	 * @param list list to check
	 * @param index index to check
	 * @return true if index is valid
	 */
	@SuppressWarnings("unchecked")
	public static boolean isIndexInRange(List list, int index) {
		if (list.isEmpty()) {
			return false;
		}
		return index >= 0 && index < list.size(); 
	}

	public static boolean isIndexInRange(double[] list, int index) {
		if (list == null || list.length == 0) {
			return false;
		}
		return index >= 0 && index < list.length; 
	}
}
