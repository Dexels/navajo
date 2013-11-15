package de.xeinfach.kafenio.util;

/**
 * Static utility class with miscellaneous functions.
 * @author Maxym Mykhalchuk, Karsten Pawlik
 */
public final class Utils {
    
    /** 
     * Private constructor to forbid class creation 
     */
    private Utils() {}
    
    /**
     * Ensures that the given string does not have a given character as the first one.
     * If it is the case, all such characters are deleted from the beginning of the string.
     * @param character a character to check for
     * @param aString a string to check
     * @return returns the given string without a leading character(s)
     */
    public static String ensureHasNoLeading(char character, String aString) {
        if (aString != null)
            while (aString.length() > 0 && aString.charAt(0) == character)
                aString = aString.substring(1);
        return aString;
    }
    
    /**
     * Ensures that the given string's last character is the given character.
     * If the last character is the given character or an empty/null string is passed, 
     * the string is returned unchanged.
     * @param character a character to check for
     * @param aString a string to check
     * @return returns the given string with the given character appended
     */
    public static String ensureHasTrailing(char character, String aString) {
        if (aString != null && aString.length() > 0 && aString.charAt(aString.length()-1) != character)
            return aString + character;
            else return aString;
    }

	/**
	 * @param inputString input string to check
	 * @param returnValue a return value
	 * @return returns the inputString if the inputString IS NOT null or empty
	 * and the returnValue if the inputString IS empty or null
	 */
	public static String checkNullOrEmpty(String inputString, String returnValue) {
		if (inputString != null && !"".equals(inputString)) {
			return inputString;
		} else {
			return returnValue;
		}
	}

	/**
	 * @param inputString input string to check
	 * @return returns the inputString or null if the inputString is empty or null
	 */
	public static String checkNullOrEmpty(String inputString) {
		return Utils.checkNullOrEmpty(inputString, null);
	}
    
}
