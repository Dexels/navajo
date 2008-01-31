/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.functions;

import java.util.*;

import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * @author arjen
 *
 */
public class XmlUnescape extends FunctionInterface {

	  public final static String XML_ESCAPE_DELIMITERS = "&'<>\"";

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Unescapes the following characters in the characters: &'<>\", using xml escape codes";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	public String usage() {
		return "XmlUnescape(String)";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	public Object evaluate() throws TMLExpressionException {
		Object o = getOperand(0);
		if (o == null) {
			return null;
		}
		if (o instanceof String) {
			return XMLUnescape((String) o);
		}  else {
			throw new TMLExpressionException(this, "Invalid operand: " + o.getClass().getName());
		}
	}


	  /**
	   * Replace all occurrences of the escaped characters &amp;, &quot;, &apos;,
	   * &lt; and &gt; by the unescaped characters &, ', ", < and >.
	   */
	  public static final String XMLUnescape(String s) {
	      if ((s == null) || (s.length() == 0)) {
	          return s;
	      }

	      int    offset;
	      int    next;
	      String result;

	      // filter out all escaped ampersands
	      offset = 0;
	      result = "";

	      while ((next = s.indexOf("&amp;", offset)) >= 0) {
	          result += s.substring(offset, next) + "&";
	          offset = next + "&amp;".length();
	      }

	      result += s.substring(offset, s.length());    // characters after last &
	      s      = result;

	      // filter out all escaped double quotes
	      offset = 0;
	      result = "";

	      while ((next = s.indexOf("&quot;", offset)) >= 0) {
	          result += s.substring(offset, next) + "\"";
	          offset = next + "&quot;".length();
	      }

	      result += s.substring(offset, s.length());    // characters after last "
	      s      = result;

	      // filter out all escaped single quotes
	      offset = 0;
	      result = "";

	      while ((next = s.indexOf("&apos;", offset)) >= 0) {
	          result += s.substring(offset, next) + "\'";
	          offset = next + "&apos;".length();
	      }

	      result += s.substring(offset, s.length());    // characters after last "
	      s      = result;

	      // filter out all escaped less than characters
	      offset = 0;
	      result = "";

	      while ((next = s.indexOf("&lt;", offset)) >= 0) {
	          result += s.substring(offset, next) + "<";
	          offset = next + "&lt;".length();
	      }

	      result += s.substring(offset, s.length());    // characters after last <
	      s      = result;

	      // filter out all escaped greater than characters
	      offset = 0;
	      result = "";

	      while ((next = s.indexOf("&gt;", offset)) >= 0) {
	          result += s.substring(offset, next) + ">";
	          offset = next + "&gt;".length();
	      }

	      result += s.substring(offset, s.length());    // characters after last >
	      s      = result;

	      // filter out all escaped newlines
	      offset = 0;
	      result = "";

	      while ((next = s.indexOf("\\n", offset)) >= 0) {
	          result += s.substring(offset, next) + "\n";
	          offset = next + "\\n".length();
	      }

	      result += s.substring(offset, s.length());    // characters after last newline

	       // filter out all escaped ;'s
	      offset = 0;
	      result = "";

	      while ((next = s.indexOf("\\;", offset)) >= 0) {
	          result += s.substring(offset, next) + ";";
	          offset = next + "\\;".length();
	      }

	      result += s.substring(offset, s.length());    // characters after last newline

	      return result;
	  }
	  
	
}
