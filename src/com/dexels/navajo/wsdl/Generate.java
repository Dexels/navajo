package com.dexels.navajo.wsdl;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.xml.XMLDocumentUtils;
import java.io.*;
import java.util.StringTokenizer;
import gnu.regexp.*;


/**
 *
 * The purpose of this class is to generate a WSDL file from Navajo tml/xsl scripts.
 *
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class Generate {

  public Generate() {
  }

  public void generateInputPart(Message parent, Navajo result, Node offset) throws NavajoException, REException {

      // Find all input properties in script [.*] pattern!
      NodeList list = offset.getChildNodes();

      RE re = new RE("\\[.*\\]");
        // Construct TML document from request parameters.
      for (int i = 0; i < list.getLength(); i++) {

            if (list.item(i).getNodeName().equals("expression")) {

              Node parentNode = list.item(i).getParentNode();

              String parameter = ((Element) list.item(i)).getAttribute("name");

              System.out.println("Checking expression value:  " + parameter);
              REMatch [] matches = re.getAllMatches(parameter);

              for (int j = 0; j < matches.length; j++) {
                  String value = matches[j].toString();
                  value = value.substring(1, value.length()-1);

                  if (value.indexOf("__parms__") == -1) { // Skip parameters!
                      System.out.println("Processing input property: " + value);
                      Message msg = com.dexels.navajo.mapping.XmlMapperInterpreter.getMessageObject(value,
                                              parent, false, result);
                      String propName = com.dexels.navajo.mapping.XmlMapperInterpreter.getStrippedPropertyName(value);
                      Property prop = null;

                      if (propName.indexOf(":") == -1) {
                          prop = Property.create(result, propName, Property.STRING_PROPERTY, "", 0, "",
                                                 Property.DIR_IN);
                          msg.addProperty(prop);
                      } else {
                          StringTokenizer selProp = new StringTokenizer(propName, ":");
                          String propertyName = selProp.nextToken();
                          String selectionField = selProp.nextToken();
                          //Selection sel = Selection.create(result, value, value, true);

                          prop = msg.getProperty(propertyName);
                          if (prop == null) {
                              prop = Property.create(result, propertyName, "1", "", Property.DIR_IN);
                              msg.addProperty(prop);
                          }
                          //prop.addSelection(sel);
                      }
                  }
              }
            } else if (list.item(i).getNodeName().equals("map")) { // map tag can contain reference to input property!
                  Element map = (Element) list.item(i);
                  String filter = map.getAttribute("filter");
                  if ((filter != null) && (!filter.equals(""))) {
                    // Generate property in filter attribute (TODO)
                  }
                  String ref = map.getAttribute("ref");
                  System.out.println("Parent node of map: " + list.item(i).getParentNode().getNodeName());
                  if ((ref != null) && !ref.equals("") && list.item(i).getParentNode().getNodeName().equals("field")) {
                      System.out.println("REF = " + ref);
                      REMatch match = re.getMatch(ref);
                      StringTokenizer subMsgs = new StringTokenizer(ref, "/");
                      String msgName = "";
                      while (subMsgs.hasMoreTokens()) {
                          String sm = subMsgs.nextToken();
                          int rep = sm.indexOf(".*");
                          if (rep != -1)
                            sm = sm.substring(0, rep).concat("0");
                          msgName = msgName + "/" + sm;
                      }
                      if (!ref.startsWith(Navajo.MESSAGE_SEPARATOR) && msgName.startsWith(Navajo.MESSAGE_SEPARATOR))
                        msgName = msgName.substring(1);
                      msgName.trim();
                      System.out.println("De-regular-expressioned: " + msgName);
                      Message sub = com.dexels.navajo.mapping.XmlMapperInterpreter.getMessageObject(msgName,
                                                                    parent, true, result);
                      generateInputPart(sub, result, list.item(i));
                  } else
                    generateInputPart(parent, result, list.item(i));
            } else { // for all other nodes continue processing with the same reference parent.
                generateInputPart(parent, result, list.item(i));
            }

        }
        //System.out.println(result.toString());
  }

  public Document readXslFile(String scriptName) throws FileNotFoundException, NavajoException {

          Document d = null;
          FileInputStream input = new FileInputStream(new File(scriptName+".xsl"));
          d = XMLDocumentUtils.createDocument(input, false);
          d.getDocumentElement().normalize();

          return d;
  }

  public static void main(String args[]) throws Exception {

      Generate gen = new Generate();

      Document wsdl = com.dexels.navajo.xml.XMLDocumentUtils.createDocument();
      Navajo result = new Navajo();
      Document script = gen.readXslFile(args[0]);

      // Find map nodes.
      NodeList list = script.getElementsByTagName("tsl").item(0).getChildNodes();
      for (int i = 0; i < list.getLength(); i++) {
          if (list.item(i).getNodeName().equals("map"))
            gen.generateInputPart(null, result, list.item(i));
      }
      System.out.println(result.toString());
  }
}