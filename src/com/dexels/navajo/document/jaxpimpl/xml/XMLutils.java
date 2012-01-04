/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document.jaxpimpl.xml;

import java.io.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;

import com.dexels.navajo.document.*;

public class XMLutils {

    public final static String XML_ESCAPE_DELIMITERS = "&'<>\"";
	private final static Logger logger = LoggerFactory
			.getLogger(XMLutils.class);
    public static String getElementValue(Node node) {
    	
	return node.getNodeValue();
    }

    public static Navajo createNavajoInstance(String filename) throws NavajoException, FileNotFoundException {


          Document d = null;
          FileInputStream input = null;
          Navajo navajo = null;

          try {
            input = new FileInputStream(new File(filename));
            d = XMLDocumentUtils.createDocument(input, false);
            d.getDocumentElement().normalize();
            navajo = NavajoFactory.getInstance().createNavajo(d);
          } finally {
            if (input != null) {
              try {
                input.close();
              }
              catch (IOException ex) {
              }
            }
          }
          return navajo;


    }

    public static Node findNode(Document d, String name) {

      Node body = d.getDocumentElement();

      return actualFindNode(body, name);
    }

    private static Node actualFindNode(Node node, String name)
    {
   
    
    	String nodeName = node.getNodeName();
    	nodeName = nodeName.substring( (nodeName.indexOf(":") != -1 ? nodeName.indexOf(":") + 1: 0) );
    	
       if (nodeName.equals(name)) {
            return node;
        }
        if (node.hasChildNodes())
            {
                NodeList list = node.getChildNodes();
                int size = list.getLength();

                for (int i=0; i < size; i++)
                    {
                        Node found = actualFindNode(list.item(i), name);
                        if (found != null) return found;
                    }
            }
        return null;
    }

    public static Node findNodeWithAttributeValue(Document d, String name, String attribute,
				    String value)     {
      Node body = d.getDocumentElement();

      return actualFindNodeWithAttributeValue(body, name, attribute, value);
    }

    public static Node findNodeWithAttributeValue(Node node, String name, String attribute,String value)     {
    	    return actualFindNodeWithAttributeValue(node, name, attribute, value);
    }
    
    public static Node findNode(Node node, String name)
    {
       String nodeName  = node.getNodeName();
       nodeName = nodeName.substring( (nodeName.indexOf(":") != -1 ? nodeName.indexOf(":") + 1 : 0) );
       name = name.substring( (name.indexOf(":") != -1 ? name.indexOf(":") + 1 : 0) );
       if (nodeName.equals(name)) {
            return node;
        }
        if (node.hasChildNodes())
            {
                NodeList list = node.getChildNodes();
                int size = list.getLength();

                for (int i=0; i < size; i++)
                    {
                        Node found = findNode(list.item(i), name);
                        if (found != null) return found;
                    }
            }
        return null;
    }

    private static Node actualFindNodeWithAttributeValue(Node node, String name, String attribute,
				    String value)     {

    	  String nodeName  = node.getNodeName();
          nodeName = nodeName.substring( (nodeName.indexOf(":") != -1 ? nodeName.indexOf(":") + 1 : 0) );
	if (nodeName.equals(name)) {
	    Element e = (Element) node;
	    if (e.getAttribute(attribute) != null && e.getAttribute(attribute).equals(value))
		return node;
	}
	if (node.hasChildNodes()) {
	    NodeList list = node.getChildNodes();
	    int size = list.getLength();
	    for (int i=0; i < size; i++)
		{
		    Node found =
			actualFindNodeWithAttributeValue(list.item(i), name, attribute, value);
		    if (found != null) return found;
		}
	}
        return null;
    }



    /**
     * Replace all occurrences of the characters &, ', ", < and > by the escaped
     * characters &amp;, &quot;, &apos;, &lt; and &gt;
     */
    public static String XMLEscape(String s) {
    	if ( s == null ) {
    		return "";
    	}
    	
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

//                case '\n' :
//                    result.append("\\n");
//                    break;

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

    /**
     * Replace all occurrences of the escaped characters &amp;, &quot;, &apos;,
     * &lt; and &gt; by the unescaped characters &, ', ", < and >.
     */
    public static String XMLUnescape(String s) {
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
    
    public static void main(String [] args) throws Exception {
    	Document d = XMLDocumentUtils.createDocument(new FileInputStream("/home/arjen/soap.xml"), true);
    	Node h = XMLutils.findNode(d, "Header");
    	logger.info("header = " + h);
    }
}
