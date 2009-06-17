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

import java.util.StringTokenizer;

import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: Encapsulates information about an HTTP cookie.
 *
 * @author Todd Wilson, modified by Karsten Pawlik
 */
public class Cookie {
	
	private static LeanLogger log = new LeanLogger("Cookie.class");
	private String keyValue = "";
	private String expires = null;
	private String domain = null;
	private String path = null;

	/**
	 * The constructor.Takes a string representing a cookie,
	 * parses, the string and populates itself with the
	 * corresponding data.
	 *
	 * @param rawCookie The string representing the cookie.
	 */
	public Cookie( String rawCookie ) {
		StringTokenizer st = new StringTokenizer( rawCookie + ";", ";" );
		String tmpToken = null;
	
		while( st.hasMoreElements() ) {
			tmpToken = ( String )st.nextElement();
	
			tmpToken = shaveFirstSpace( tmpToken );
	
			if( tmpToken.toUpperCase().startsWith( "EXPIRES" ) ) {
				this.expires = tmpToken.substring( tmpToken.indexOf( "=" ) + 1, tmpToken.length() );
			} else if( tmpToken.toUpperCase().startsWith( "DOMAIN" ) ) {
				this.domain = tmpToken.substring( tmpToken.indexOf( "=" ) + 1, tmpToken.length() );
			} else if( tmpToken.toUpperCase().startsWith( "PATH" ) ) {
				this.path = tmpToken.substring( tmpToken.indexOf( "=" ) + 1, tmpToken.length() );
			} else {
				this.keyValue += tmpToken + "; ";
			}
		}
	}

	/**
	 * Trims the initial space off of a string, if the string
	 * begins with a space.
	 *
	 * @param	value		The String value to be trimmed.
	 */
	private String shaveFirstSpace( String value ) {
		if( value.startsWith( " " ) ) {
			return value.substring( 1, value.length() );
		}
		return value;
	}


	/**
	 * Returns the key/value portion of the string.
	 *
	 * @return	A string representing the key and value (e.g.
	 * foo=bar).
	 */
	public String getKeyValue() {
		if( keyValue!=null ) {
			return this.keyValue;
		}
		return "";
	}


	/**
	 * Returns the key portion of the key/value portion of the
	 * cookie.
	 *
	 * @return	The key.
	 */
	public String getKey() {
		return this.keyValue.substring( 0, keyValue.indexOf( "=" ) );
	}


	/**
	 * Returns the value portion of the key/value portion of the
	 * cookie.
	 *
	 * @return	The key.
	 */
	public String getValue() {
		return this.keyValue.substring( keyValue.indexOf( "=" ) + 1, keyValue.length() );
	}


	/**
	 * Indicates when the cookie is to expire.
	 *
	 * @return	The expiration date. An empty string if none
	 * exists.
	 */
	public String getExpires() {
		if( expires!=null ) {
			return this.expires;
		}
		return "";
	}


	/**
	 * Indicates the domain the cookie corresponds to.
	 *
	 * @return	The domain. An empty string if none
	 * exists.
	 */
	public String getDomain() {
		if( domain!=null ) {
			return this.domain;
		}
		return "";
	}


	/**
	 * Sets the domain the cookie is to correspond to.
	 *
	 * @param newDomain The domain.
	 */
	public void setDomain( String newDomain ) {
		this.domain = newDomain;
	}


	/**
	 * Indicates the path the cookie corresponds to.
	 *
	 * @return	The path. An empty string if none
	 * exists.
	 */
	public String getPath() {
		if( path!=null ) {
			return this.path;
		}
		return "";
	}
}
