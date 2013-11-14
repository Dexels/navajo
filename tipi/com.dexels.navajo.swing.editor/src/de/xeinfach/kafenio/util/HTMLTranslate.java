// this class was taken from derrick oswalds htmlparser library:
// HTMLParser Library $Name:  $ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Derrick Oswald
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//

package de.xeinfach.kafenio.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * HTMLTranslate numeric character references and character entity references to unicode characters.
 * Based on tables found at <a href="http://www.w3.org/TR/REC-html40/sgml/entities.html">
 * http://www.w3.org/TR/REC-html40/sgml/entities.html</a>
 * <p><b>Note: Do not edit! This class is created by the Generate class.</b>
 * <p>Typical usage:
 * <pre>
 *      String s = HTMLTranslate.decode (getTextFromHtmlPage ());
 * </pre>
 * @author DerrickOswald@users.sourceforge.net
 * @author Karsten Pawlik // removed not necessary methods.
 */
public final class HTMLTranslate {
    /**
     * Table mapping entity reference kernel to character.
     * <p><code>String</code>-><code>Character</code>
     */
    private static Map ref2CharMap;
	private static Map char2RefMap;
    private static Properties ref2CharProps = null;
	private static LeanLogger log = new LeanLogger("HTMLTranslate.class");    

    static {
        ref2CharMap = new HashMap(1000);

		try {
			log.debug("loading HTML-Reference-to-Character mappings from properties file.");
			ref2CharProps = new Properties();
			InputStream resource = HTMLTranslate.class.getResourceAsStream("HTMLTranslate.properties");
			ref2CharProps.load(resource);
			Iterator iterator = ref2CharProps.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String)iterator.next();
				Character character = new Character(((String)ref2CharProps.get(key)).toCharArray()[0]);
				ref2CharMap.put(key, character);
			}
		} catch (Exception e) {
			log.error("could not load HTML-reference-to-character mappings." + e.fillInStackTrace());
		}
		
		log.error("creating the character-to-reference mapping table");
        char2RefMap = new HashMap(ref2CharMap.size());
        Iterator iterator = ref2CharMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            Character character = (Character)ref2CharMap.get(key);
            char2RefMap.put(character, key);
        }
    }

    /**
     * Private constructor.
     * This class is fully static and thread safe.
     */
    private HTMLTranslate() {}

    /**
     * Convert a reference to a unicode character.
     * Convert a single numeric character reference or character entity reference
     * to a unicode character.
     * @param string The string to convert. Of the form &xxxx; or &amp;#xxxx; with
     * or without the leading ampersand or trailing semi-colon.
     * @return The converted character or '\0' (zero) if the string is an
     * invalid reference.
     */
    public static char convertToChar(String string) {
        Character item;
        int start;
        int end;
        char ret;

        start = 0;
        end = string.length();

        if (0 < end) {
            if ('&' == string.charAt(0)) start++;
            if (0 < end) {
                if (';' == string.charAt(end - 1)) --end;
                if (0 < end) {
                    if ('#' == string.charAt(start)) {
                        try {
                            return (char)Integer.parseInt(string.substring(start + 1, end));
                        } catch (NumberFormatException nfe) {
                            return 0;
                        }
					} else {
                        item = (Character)ref2CharMap.get(string.substring(start,end));
                        if (null != item)
                            return item.charValue();
                    }
                }
            }
        }
		return 0;
    }

    /**
     * Decode a string containing references.
     * Change all numeric character reference and character entity references
     * to unicode characters.
     * @param string The string to translate.
     * @return returns the input string without html-entities.
     */
    public static String decode(String string) {
        int index;
        int length;
        int amp;
        int semi;
        String code;
        char character;
        StringBuffer ret;

        ret = new StringBuffer(string.length());

        index = 0;
        length = string.length();
        while ((index < length) && (-1 != (amp = string.indexOf('&', index)))) {
            ret.append(string.substring(index, amp));
            index = amp + 1;
            if (amp < length - 1) {
                semi = string.indexOf(';', amp);
                if (-1 != semi)
                    code = string.substring(amp, semi + 1);
                else
                    code = string.substring(amp);
                if (0 != (character = convertToChar(code)))
                    index += code.length() - 1;
                else
                    character = '&';
            } else {
				character = '&';
            }
            ret.append(character);
        }
        if (index < length) ret.append(string.substring(index));

        return (ret.toString());
    }

    /**
     * Convert a character to a character entity reference.
     * Convert a unicode character to a character entity reference of
     * the form &xxxx;.
     * @param character The character to convert.
     * @return The converted character or <code>null</code> if the character
     * is not one of the known entity references.
     */
    public static String convertToString(Character character) {
        StringBuffer buffer;
        String ret;

        if (null != (ret = (String)char2RefMap.get(character))) {
            buffer = new StringBuffer(ret.length() + 2);
            buffer.append('&');
            buffer.append(ret);
            buffer.append(';');
            ret = buffer.toString();
        }

        return (ret);
    }

    /**
     * Convert a character to a numeric character reference.
     * Convert a unicode character to a numeric character reference of
     * the form &amp;#xxxx;.
     * @param character The character to convert.
     * @return The converted character.
     */
    public static String convertToString(int character) {
        StringBuffer ret;

        ret = new StringBuffer(13);
        ret.append("&#");
        ret.append(character);
        ret.append(';');

        return (ret.toString());
    }

    /**
     * Encode a string to use references.
     * Change all characters that are not ASCII to their numeric character
     * reference or character entity reference.
     * This implementation is inefficient, allocating a new
     * <code>Character</code> for each character in the string,
     * but this class is primarily intended to decode strings
     * so efficiency and speed in the encoding was not a priority.
     * @param string The string to translate.
     * @return returns the encoded input string.
     */
    public static String encode(String string) {
        int length;
        char c;
        Character character;
        String value;
        StringBuffer ret;

        ret = new StringBuffer(string.length() * 6);
        length  = string.length();
        for (int i = 0; i < length; i++) {
            c = string.charAt(i);
            character = new Character(c);
            if (null != (value = convertToString(character))) {
				ret.append(value);
            } else if (!((c > 0x001F) && (c < 0x007F))) {
                value = convertToString(c);
                ret.append(value);
            } else {
				ret.append(character);
            }
        }
        return (ret.toString());
    }
}
