package com.dexels.navajo.wsdl;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.xml.XMLDocumentUtils;
import java.io.*;
import java.util.*;
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

  public void generateOutputPart(Message parent, Navajo result, Node offset) throws NavajoException, REException {

      // Create message.
      if (offset.getNodeType() == Node.ELEMENT_NODE) {

        if (((Element) offset).getTagName().equals(Message.MSG_DEFINITION)) {
          String parentName = ((Element) offset).getAttribute(Message.MSG_NAME);
          Message newMessage = Message.create(result, parentName);
          if (parent != null)
            parent.addMessage(newMessage);
          else
            result.addMessage(newMessage);
          parent = newMessage;
          // Does message has a "ref" has child, if so possibly multiple occurences.
          NodeList allChildren = offset.getChildNodes();
          for (int j = 0; j < allChildren.getLength(); j++) {
            if (allChildren.item(j).getNodeType() == Node.ELEMENT_NODE) {
              Element child = (Element) allChildren.item(j);
              if (child.getTagName().equals("map")) { // Yes.
                //System.out.println("GOT MAP: object = " + child.getAttribute("object") + ", ref = " +
                //                  child.getAttribute("ref"));
                if ((child.getAttribute("ref") != null) && !child.getAttribute("ref").equals("")) {
                  //System.out.println("MULTIPLE MESSAGE!");
                  parent.setName(parentName);
                  parent.setIndex(0);
                }
                NodeList l = child.getChildNodes();
                for (int i = 0; i < l.getLength(); i++) {
                  if (l.item(i).getNodeType() == Node.ELEMENT_NODE) {
                      //System.out.println("Generating output for: " + l.item(i));
                      generateOutputPart(parent, result, l.item(i));
                  }
                }
              } else {
                generateOutputPart(parent, result, child);
              }
            }
          }
        } else if (((Element) offset).getTagName().equals(Property.PROPERTY_DEFINITION)) {
           Element child = (Element) offset;
           String propName = child.getAttribute("name");
           String propType = child.getAttribute("type");
           Property prop = Property.create(result, propName, propType, "", 0, "", Property.DIR_IN);
           parent.addProperty(prop);
        } else {
          NodeList list = offset.getChildNodes();
          for (int i = 0; i < list.getLength(); i++)
            generateOutputPart(parent, result, list.item(i));
        }
      } else {
        NodeList list = offset.getChildNodes();
        for (int i = 0; i < list.getLength(); i++)
          generateOutputPart(parent, result, list.item(i));
      }
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

              //System.out.println("Checking expression value:  " + parameter);
              REMatch [] matches = re.getAllMatches(parameter);

              for (int j = 0; j < matches.length; j++) {
                  String value = matches[j].toString();
                  value = value.substring(1, value.length()-1);

                  if (value.indexOf("__parms__") == -1) { // Skip parameters!
                      //System.out.println("Processing input property: " + value);
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

  public Document readTmlFile(String scriptName) throws FileNotFoundException, NavajoException {

          Document d = null;
          FileInputStream input = new FileInputStream(new File(scriptName+".tml"));
          d = XMLDocumentUtils.createDocument(input, false);
          d.getDocumentElement().normalize();

          return d;
  }

  public void createMessageDefinition(Document d, String name, HashSet parts) {
      Element e = d.createElement("wsdl_message");
      d.appendChild(e);
      e.setAttribute("name", name);
      Iterator iter = parts.iterator();
      while (iter.hasNext()) {
        Element c = d.createElement("wsdl_part");
        String partName = iter.next().toString();
        c.setAttribute("name", partName);
        c.setAttribute("type", partName);
        e.appendChild(c);
      }
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

      // Determine input messages:
      ArrayList msgs = result.getAllMessages();
      HashSet inputMessages = new HashSet();
      for (int i = 0; i < msgs.size(); i++) {
        inputMessages.add(((Message) msgs.get(i)).getName());
      }

      HashSet outputMessages = new HashSet();

      list = script.getElementsByTagName("tsl").item(0).getChildNodes();
      for (int i = 0; i < list.getLength(); i++) {
           if (list.item(i).getNodeName().equals("message")) {
             Element e = (Element) list.item(i);
             outputMessages.add(e.getAttribute("name"));
           }
            gen.generateOutputPart(null, result, list.item(i));
      }

      Document staticPart = gen.readTmlFile(args[0]);
      list = staticPart.getElementsByTagName("tml").item(0).getChildNodes();
      for (int i = 0; i < list.getLength(); i++) {
          if (list.item(i).getNodeName().equals("message")) {
             Element e = (Element) list.item(i);
             outputMessages.add(e.getAttribute("name"));
           }
            gen.generateOutputPart(null, result, list.item(i));
      }

      Document input = result.getMessageBuffer();
      //String inputMessage = "get"+args[0]+"In";
      //gen.createMessageDefinition(input.getFirstChild(), inputMessage, inputMessages);
      //String outputMessage = "get"+args[0]+"Out";
      //gen.createMessageDefinition(input.getFirstChild(), outputMessage, outputMessages);

      String wsdlResult = XMLDocumentUtils.transform(result.getMessageBuffer(),
                    new File("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/xsl/tml2wsdl.xsl"));

     System.out.println(wsdlResult);

  }
}