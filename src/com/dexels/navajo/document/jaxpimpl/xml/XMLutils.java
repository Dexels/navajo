/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document.jaxpimpl.xml;

import com.dexels.navajo.document.*;

import org.w3c.dom.*;
import java.io.*;
import java.util.StringTokenizer;

public class XMLutils {

    public final static String XML_ESCAPE_DELIMITERS = "&'<>\"";
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
       if (node.getNodeName().equals(name)) {
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

    public static Node findNode(Node node, String name)
    {

       if (node.getNodeName().equals(name)) {
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

	if (node.getNodeName().equals(name)) {
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

    public static String string2unicode( String s ){

        StringBuffer tempBuffer = new StringBuffer(s);
        for (int i = 0; i < tempBuffer.length(); i++) {
            //if ( tempBuffer.charAt(i)=='&' ){
            //    tempBuffer.replace(i,i+1,"&amp;");
            //} else
            if ( tempBuffer.charAt(i)=='<' ){
                tempBuffer.replace(i,i+1,"&lt;");
            } else if ( tempBuffer.charAt(i)=='>' ){
                tempBuffer.replace(i,i+1,"&gt;");
            } else if ( tempBuffer.charAt(i)=='\"' ){
                tempBuffer.replace(i,i+1,"&quot;");
            }  else if ( tempBuffer.charAt(i)=='\'' ){
                tempBuffer.replace(i,i+1,"&apos;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x20ac;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x0192;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00a1;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00a2;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00a3;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00a4;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00a5;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00a6;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00a7;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00a8;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00a9;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00aa;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00ab;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00ae;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00af;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00b0;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00b1;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00b2;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00b3;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00b4;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00b5;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00b7;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00b8;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00b9;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00ba;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00bb;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00bc;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00bd;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00be;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00bf;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00c0;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00c1;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00c2;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00c3;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00c4;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00c5;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00c6;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00c7;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00c8;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00c9;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00ca;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00cb;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00cc;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00cd;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00ce;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00cf;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00d0;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00d1;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00d2;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00d3;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00d4;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00d5;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00d6;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00d7;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00d8;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00d9;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00da;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00db;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00dc;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00dd;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00de;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00df;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00e0;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00e1;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00e2;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00e3;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00e4;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00e5;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00e6;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00e7;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00e8;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00e9;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00ea;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00eb;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00ec;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00ed;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00ee;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00ef;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00f0;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00f1;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00f2;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00f3;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00f4;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00f5;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00f6;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00f7;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00f8;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00f9;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00fa;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00fb;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00fc;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00fd;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00fe;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x00ff;");
            } else if ( tempBuffer.charAt(i)=='�' ){
                tempBuffer.replace(i,i+1,"&#x2030;");
            }
        }

        return tempBuffer.toString();
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
}
