package org.dexels.servlet.smtp;

/**
 * Title:        Toolbox
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */
import java.util.*;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;

import gnu.regexp.*;

public class Utils {

  public static String getEmailAdress(String from) {
    if (from.indexOf("<") != -1)
      return from.substring(from.indexOf("<")+1, from.indexOf(">"));
    else
      return from;
  }

  public static String getElementValue(Node node) {

	return node.getNodeValue();
    }

    public static Node findNode(Document d, String name) {

      Node body = d.getDocumentElement();

      return findNode(body, name);
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

    public static Node findNodeWithAttributeValue(Document d, String name, String attribute,
				    String value)     {
      Node body = d.getDocumentElement();

      return actualFindNodeWithAttributeValue(body, name, attribute, value);
    }

    private static Node actualFindNodeWithAttributeValue(Node node, String name, String attribute,
				    String value)     {

	if (node.getNodeName().equals(name)) {
	    Element e = (Element) node;
	    if (e.getAttribute(attribute).equals(value))
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

    public static String trim(String s) {
      StringBuffer result = new StringBuffer();
      for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) != ' ')
          result.append(s.charAt(i));
      }
      return result.toString();
    }

    public static void main(String args[]) throws Exception {
      String line = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\"><HTML><HEAD><META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=utf-8\"></HEAD><BODY><DIV>1621AB 3</DIV></BODY></HTML>";
      //String line = " 1621AB 3";
      RE object = new RE("[1-9][0-9]{3} ?[A-z]{2} [1-9][0-9]* ?[A-z]*");
      RE postcode = new RE("[1-9][0-9]{3} ?[A-z]{2}");
      RE huisnummer = new RE("[1-9][0-9]*");
      gnu.regexp.REMatch matchObject = object.getMatch(line);
      String objectString = matchObject.toString();
      gnu.regexp.REMatch matchPostcode = postcode.getMatch(objectString);
      String postcodeString = matchPostcode.toString();
      gnu.regexp.REMatch matchHuisnummer = huisnummer.getMatch(objectString.substring(postcodeString.length(), objectString.length()));
      String huisnummerString = matchHuisnummer.toString();
      System.out.println("Postcode = " + trim(postcodeString));
      System.out.println("Huisnummer = " + huisnummerString);
    }
}