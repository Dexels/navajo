package com.dexels.navajo.server.listener.soap.wsdl;

import org.w3c.dom.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import java.io.*;
import java.util.*;
import java.util.regex.*;

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

  public Navajo generateNavajoOutput(String file) throws Exception {

      Navajo result = NavajoFactory.getInstance().createNavajo();

      Document d = XMLDocumentUtils.createDocument();
      FileInputStream input = new FileInputStream(new File(file));
      d = XMLDocumentUtils.createDocument(input, false);
      d.getDocumentElement().normalize();

      // Find map nodes.
      Element body = (Element) d.getElementsByTagName("tsl").item(0);
      NodeList list = body.getChildNodes();
      for (int i = 0; i < list.getLength(); i++) {
        generateOutputPart(null, result, list.item(i));
      }

      return result;
  }

  public void generateOutputPart(Message parent, Navajo result, Node offset) throws NavajoException {

      // Create message.
      if (offset.getNodeType() == Node.ELEMENT_NODE) {

        if (((Element) offset).getTagName().equals(Message.MSG_DEFINITION)) {
          String parentName = ((Element) offset).getAttribute(Message.MSG_NAME);
          String type = ((Element) offset).getAttribute(Message.MSG_TYPE);
          String ignore = ((Element) offset).getAttribute(Message.MSG_MODE);
          
          if ( ignore != null && ignore.equals("ignore") ) {
        	  return;
          }
          
          Message newMessage = null;
          if (parent == null) {
            newMessage = result.getMessage(parentName);
            parent = newMessage;
          }
          else {
            newMessage = parent.getMessage(parentName);
          }

          //System.err.println("FOUND newMessage: " + parentName + ", TYPE is: " + type);
          if (newMessage == null && type.equals(Message.MSG_TYPE_ARRAY)) {
        	//System.err.println("CREATING ARRAY MESSAGE");
        	newMessage = NavajoFactory.getInstance().createMessage(result, parentName, Message.MSG_TYPE_ARRAY);
            Message newSubMessage = NavajoFactory.getInstance().createMessage(result, parentName);
            newMessage.addMessage(newSubMessage);
            if (parent == null) result.addMessage(newMessage); else parent.addMessage(newMessage);
            parent = newSubMessage;
          } else if (newMessage == null) {
        	newMessage = NavajoFactory.getInstance().createMessage(result, parentName);
        	if (parent == null) result.addMessage(newMessage); else parent.addMessage(newMessage);
            parent = newMessage;
          }
          
  

          // Does message has a "ref" has child, if so possibly multiple occurences.
          NodeList allChildren = offset.getChildNodes();
          for (int j = 0; j < allChildren.getLength(); j++) {
            if (allChildren.item(j).getNodeType() == Node.ELEMENT_NODE) {
              Element child = (Element) allChildren.item(j);
              if (child.getTagName().equals("map")) { // Yes.
//                System.out.println("GOT MAP: object = " + child.getAttribute("object") + ", ref = " +
//                                  child.getAttribute("ref"));
                if ((child.getAttribute("ref") != null) && !child.getAttribute("ref").equals("")) {
                  //System.out.println("MULTIPLE MESSAGE!");
                  parent.setName(parentName);
                  parent.setType(Message.MSG_TYPE_ARRAY);
                  Message newSubMessage = NavajoFactory.getInstance().createMessage(result, parentName);
                  parent.addMessage(newSubMessage);
                  parent = newSubMessage;
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
           Property prop = NavajoFactory.getInstance().createProperty(result, propName, propType, "", 30, "", Property.DIR_IN);
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

  public void generateInputPart(Message parent, Navajo result, Node offset) throws NavajoException {


      if (!(offset instanceof Element))
        return;

      // Find all input properties in script [.*] pattern!
      NodeList list = offset.getChildNodes();


      Pattern re = Pattern.compile("\\[[A-z,/]*\\]");
      // Construct TML document from request parameters.

      if (list.getLength() == 0) {
    	  
    	  if ( parent == null ) {
    		  return;
    	  }
    	  
          //System.out.println("offset = " + offset + ", tagname = " + offset.getNodeName() + ", parent = " + parent);
          String propertyName = ((Element) offset).getAttribute("name");
          Property prop = parent.getProperty(propertyName);
          if (prop == null) {
            prop = NavajoFactory.getInstance().createProperty(result, propertyName, "1", "", Property.DIR_IN);
            parent.addProperty(prop);
          }
      }

      for (int i = 0; i < list.getLength(); i++) {

            //System.out.println("list.item("+i+") = " + list.item(i).getNodeName());

            if (list.item(i).getNodeName().equals("expression")) {

              Node parentNode = list.item(i).getParentNode();

              String parameter = ((Element) list.item(i)).getAttribute("value");

              //System.out.println("Checking expression value:  " + parameter);
              Matcher matcher = re.matcher(parameter);

              while (matcher.find() && parameter.indexOf("'") == -1) {
                  String value = matcher.group();
                  
                  value = value.substring(1, value.length()-1);
                  if (value.indexOf("__parms__") == -1 && value.indexOf("@") == -1) { // Skip parameters!
                	 
                	  
                      Message msg = com.dexels.navajo.mapping.MappingUtils.getMessageObject(value,
                                              parent, false, result, false, "", -1);
                      String propName = com.dexels.navajo.mapping.MappingUtils.getStrippedPropertyName(value);
                      Property prop = null;

                      if (propName.indexOf(":") == -1) {
                          prop = NavajoFactory.getInstance().createProperty(result, propName, Property.STRING_PROPERTY, "", 30, "",
                                                 Property.DIR_IN);
                          msg.addProperty(prop);
                      } else {
                          StringTokenizer selProp = new StringTokenizer(propName, ":");
                          String propertyName = selProp.nextToken();
                          String selectionField = selProp.nextToken();
                          //Selection sel = Selection.create(result, value, value, true);

                          prop = msg.getProperty(propertyName);
                          if (prop == null) {
                              prop = NavajoFactory.getInstance().createProperty(result, propertyName, "1", "", Property.DIR_IN);
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
                  //System.out.println("Parent node of map: " + list.item(i).getParentNode().getNodeName());
                  if ((ref != null) && !ref.equals("") && list.item(i).getParentNode().getNodeName().equals("field")) {
                      //System.out.println("REF = " + ref);
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
                      //System.out.println("De-regular-expressioned: " + msgName);
                      Message sub = com.dexels.navajo.mapping.MappingUtils.getMessageObject(msgName,
                                                                    parent, true, result, true, "", -1);
                      Message sub2 = NavajoFactory.getInstance().createMessage(result, msgName);
                      sub.addElement(sub2);
                      generateInputPart(sub2, result, list.item(i));
                  } else
                    generateInputPart(parent, result, list.item(i));
            //} else if (offset.getNodeName().equals("property")) {
               //String propertyName = ((Element) offset).getAttribute("name");
               //Property prop = parent.getProperty(propertyName);
               //if (prop == null) {
               //   prop = NavajoFactory.getInstance().createProperty(result, propertyName, "1", "", Property.DIR_IN);
               //   parent.addProperty(prop);
               //}
            } else { // for all other nodes continue processing with the same reference parent.
                generateInputPart(parent, result, list.item(i));
            }

        }
        //System.out.println(result.toString());
  }

  public Document readXslFile(String scriptName) throws FileNotFoundException, NavajoException {

          Document d = null;
          FileInputStream input = new FileInputStream(new File(scriptName+".xml"));
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

  public Navajo getInputPart(String scriptName) {
	  try {
		  Document wsdl = XMLDocumentUtils.createDocument();
		  Navajo inputDoc = NavajoFactory.getInstance().createNavajo();
		  Document script = readXslFile(scriptName);
		  
		  // Find map nodes.
		  if ( script.getElementsByTagName("tsl").getLength() == 0 ) {
			  return NavajoFactory.getInstance().createNavajo();
		  }
		  NodeList list = script.getElementsByTagName("tsl").item(0).getChildNodes();
		  
		  for (int i = 0; i < list.getLength(); i++) {
			  if (list.item(i).getNodeName().equals("map"))
				  generateInputPart(null, inputDoc, list.item(i));
		  }
		  
		  return inputDoc;
	  } catch (Exception e) {
		  e.printStackTrace();
		    return NavajoFactory.getInstance().createNavajo();
	  }
  }
  
  public Navajo getOutputPart(String scriptName) {
	  try {
		  Navajo outputDoc = NavajoFactory.getInstance().createNavajo();
		  Document script = readXslFile(scriptName);
		  
		  HashSet outputMessages = new HashSet();
		  
		  if ( script.getElementsByTagName("tsl").getLength() == 0 ) {
			    return NavajoFactory.getInstance().createNavajo();
		  }
		  NodeList list = script.getElementsByTagName("tsl").item(0).getChildNodes();
		  for (int i = 0; i < list.getLength(); i++) {
			  if (list.item(i).getNodeName().equals("message")) {
				  Element e = (Element) list.item(i);
				  outputMessages.add(e.getAttribute("name"));
			  }
			  generateOutputPart(null, outputDoc, list.item(i));
		  }
		  return outputDoc;
	  } catch (Exception e) {
		  e.printStackTrace();
		  return NavajoFactory.getInstance().createNavajo();
	  }
  }

  public static void main(String args[]) throws Exception {

      Generate gen = new Generate();

      

      // Determine input messages:
      Navajo inputDoc = gen.getInputPart("/home/arjen/projecten/sportlink-serv/navajo/auxilary/scripts/InitBM");
      ArrayList msgs = inputDoc.getAllMessages();
      HashSet inputMessages = new HashSet();
      for (int i = 0; i < msgs.size(); i++) {
        inputMessages.add(((Message) msgs.get(i)).getName());
      }

      Navajo outputDoc = gen.getOutputPart("/home/arjen/projecten/sportlink-serv/navajo/auxilary/scripts/InitBM");
     
      System.out.println(outputDoc.toString());

      //Document input = (Document) result.getMessageBuffer();
      //String inputMessage = "get"+"ProcessInsertKernMember"+"In";
      //gen.createMessageDefinition(input, inputMessage, inputMessages);
      //String outputMessage = "get"+"ProcessInsertKernMember"+"Out";
      //gen.createMessageDefinition(input, outputMessage, outputMessages);

      String xmlInput = XMLDocumentUtils.transform((Document) inputDoc.getMessageBuffer(),
                    new File("/home/arjen/projecten/Navajo/soap/tml2xml.xsl"));
      String xmlOutput = XMLDocumentUtils.transform((Document) outputDoc.getMessageBuffer(),
                    new File("/home/arjen/projecten/Navajo/soap/tml2xml.xsl"));

      System.out.println("XML INPUT:");
      System.out.println(xmlInput);

      System.out.println("XML OUTPUT:");

      System.out.println(xmlOutput);
  }
}
