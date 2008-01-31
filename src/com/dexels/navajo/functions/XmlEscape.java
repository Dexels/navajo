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
public class XmlEscape extends FunctionInterface {

	  public final static String XML_ESCAPE_DELIMITERS = "&'<>\"";

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Escapes the following characters in the characters: &'<>\"";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	public String usage() {
		return "XmlEscape(String)";
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
			return XMLEscape((String) o);
		}  else {
			throw new TMLExpressionException(this, "Invalid operand: " + o.getClass().getName());
		}
	}

	 /**
	   * Replace all occurrences of the characters &, ', ", < and > by the escaped
	   * characters &amp;, &quot;, &apos;, &lt; and &gt;
	   */
	  private static final String XMLEscape(String s) {
	    
	    boolean contains = false;
	    for ( int i = 0; i < XML_ESCAPE_DELIMITERS.length(); i++ ) {
	        if ( s.indexOf( XML_ESCAPE_DELIMITERS.charAt(i) ) != -1 ) {
	            contains = true;
	        }
	    }
	    
	    if ( ! contains ) {
	        return s;
	    }
	    
	      if ((s == null) || (s.length() == 0)) {
	          return s;
	      }

	      StringTokenizer tokenizer = new StringTokenizer(s, XML_ESCAPE_DELIMITERS, true);
	      StringBuffer    result    = new StringBuffer();

	      while (tokenizer.hasMoreElements()) {
	          String substring = tokenizer.nextToken();

	          if (substring.length() == 1) {
	              switch (substring.charAt(0)) {

	              case '&' :
	                  result.append("&amp;");
	                  break;

	              //case '\'' :
	              //    result.append("&apos;");
	              //    break;

	              case ';' :
	                  result.append("\\;");
	                  break;

	              case '<' :
	                  result.append("&lt;");
	                  break;

	              case '>' :
	                  result.append("&gt;");
	                  break;

	              case '\"' :
	                  result.append("&quot;");
	                  break;

//	              case '\n' :
//	                  result.append("\\n");
//	                  break;

	              default :
	                  result.append(substring);
	              }
	          }
	          else {
	              result.append(substring);
	          }
	      }

	      return result.toString();
	  }


}
