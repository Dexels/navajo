/*
 *-------------------------------------------------------------------------
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * The GNU Lesser General Public License may be found at the following URL:
 * http://www.opensource.org/licenses/lgpl-license.php
 *-------------------------------------------------------------------------
 */
package de.xeinfach.kafenio.urlfetch;

import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: Represents an HTTP header.
 *
 * @author Todd Wilson, modified by Karsten Pawlik
 */
public class HTTPHeader {
	
	private static LeanLogger log = new LeanLogger("HTTPHeader.class");
	
	/** The key portion of the header. */
	private String key = null;

	/** The value portion of the header. */
	private String value = null;


	/**
	 * Generates a new header from the given key and value.
	 *
	 * @param newKey The key portion of the header.
	 * @param newValue	The value portion of the header.
	 */
	public HTTPHeader( String newKey, String newValue ) {
		setKey( newKey );
		setValue( newValue );
	}


	/**
	 * Gets the key portion of the parameter.
	 *
	 * @return The key.
	 */
	public String getKey() {
		return this.key;
	}


	/**
	 * Sets the key portion of the parameter.
	 *
	 * @param newKey The key.
	 */
	public void setKey(String newKey) {
		this.key = newKey;
	}


	/**
	 * Gets the value portion of the parameter.
	 *
	 * @return	The value.
	 */
	public String getValue() {
		return this.value;
	}


	/**
	 * Sets the value portion of the parameter.
	 *
	 * @param newValue The value.
	 */
	public void setValue(String newValue) {
		this.value = newValue;
	}


	/**
	 * A string representation of the header.
	 *
	 * @return	The header.
	 */
	public String toString() {
		return key + ": " + value;
	}
}
