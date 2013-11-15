package de.xeinfach.kafenio.component;

import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: Class that holds name / value-pairs for List-items.
 * @author Karsten Pawlik
 */
public class NameValuePair {

	private static LeanLogger log = new LeanLogger("NameValuePair.class");
	
	private String name;
	private String value;
		
	/**
	 * creates a new, empty NameValuePair Object.
	 *
	 */
	public NameValuePair() {
		this(null,null);
	}

	/**
	 * creates a new name / value pair for a list.<BR>
	 * this constructor sets both name and value to the given string.<BR>
	 * @param newName name of the pair
	 */
	public NameValuePair(String newName) {
		this(newName, null);
	}
		
	/**
	 * creates a new name / value pair for a list.<BR>
	 * this constructor sets both name and value to the given name and value.<BR>
	 * @param newName name of the pair
	 * @param newValue value of the pair
	 */
	public NameValuePair(String newName, String newValue) {
		this.name = newName;
		this.value = newValue;
		log.debug("new NameValuePair created.");
	}
		
	/**
	 * @return returns the current name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return returns the current value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * sets the name
	 * @param string name
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * sets the value
	 * @param string value
	 */
	public void setValue(String string) {
		value = string;
	}
	
	/**
	 * @return returns a string representation of this object.
	 */
	public String toString() {
		return name + "   (" + value + ")";
	}
}
