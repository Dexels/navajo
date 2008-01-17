package com.dexels.navajo.util;

import org.w3c.dom.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import javax.xml.parsers.DocumentBuilderFactory;

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
                System.out.println("GOT MAP: object = " + child.getAttribute("object") + ", ref = " +
                                  child.getAttribute("ref"));
                if ((child.getAttribute("ref") != null) && !child.getAttribute("ref").equals("")) {
                  System.out.println("MULTIPLE MESSAGE!");
//                  parent.setName(parentName);
//                  parent.setType(Message.MSG_TYPE_ARRAY);
//                  Message newSubMessage = NavajoFactory.getInstance().createMessage(result, parentName);
//                  parent.addMessage(newSubMessage);
//                  parent = newSubMessage;
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

	  //System.err.println("IN generateInputPart()....");

      if (!(offset instanceof Element))
        return;

      // Find all input properties in script [.*] pattern!
      NodeList list = offset.getChildNodes();


       Pattern re = Pattern.compile("(\\[[A-z,/]*\\])");
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

            //System.err.println("list.item("+i+") = " + list.item(i).getNodeName());

            if (list.item(i).getNodeName().equals("expression")) {

              @SuppressWarnings("unused")
              Node parentNode = list.item(i).getParentNode();

              String parameter = ((Element) list.item(i)).getAttribute("value");
              parameter = parameter.replaceAll(" ", "");
              //System.out.println("Checking expression value:  " + parameter);
              Matcher matcher = re.matcher(parameter);

              while (matcher.find() ) {
                  String value = matcher.group(1);
                  
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
                          @SuppressWarnings("unused")
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
                      msgName = msgName.trim();
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

  public static Document createDocument(InputStream input) throws FileNotFoundException, NavajoException {

          Document d = null;
          d = XMLDocumentUtils.createDocument(input, false);
          d.getDocumentElement().normalize();

          return d;
  }

  @SuppressWarnings("unchecked")
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

  public Navajo getInputPart(Navajo result, InputStream is) {
	  
	  System.err.println("IN GETINPUTPART");
	  try {
		  //Document wsdl = XMLDocumentUtils.createDocument();
		  if ( result == null ) {
			  result = NavajoFactory.getInstance().createNavajo();
		  }
		  Document script = createDocument(is);
		  
		  // Find map nodes.
		  if ( script.getElementsByTagName("tsl").getLength() == 0 ) {
			  return NavajoFactory.getInstance().createNavajo();
		  }
		  NodeList list = script.getElementsByTagName("tsl").item(0).getChildNodes();
		  
		  for (int i = 0; i < list.getLength(); i++) {
			  if (list.item(i).getNodeName().equals("map")) {
				  generateInputPart(null, result, list.item(i));
			  } else if (list.item(i).getNodeName().equals("param")) {
				  generateInputPart(null, result, list.item(i));
			  } else if (list.item(i).getNodeName().equals("message")) {
					  generateInputPart(null, result, list.item(i));
			  } else if (list.item(i).getNodeName().equals("include")) {
				  // Parse include also.
				  System.err.println("TO DO: PARSE INCLUDE FILE FOR INPUT PROPERTIES");
//				  Element e = (Element) list.item(i);
//				  String includeFile = e.getAttribute("script");
//				  InputStream is2 = Dispatcher.getInstance().getNavajoConfig().getScript(includeFile);
//				  getInputPart(result, is2);
//				  is2.close();
			  }
		  }
		  
		  return result;
	  } catch (Exception e) {
		  e.printStackTrace();
		    return NavajoFactory.getInstance().createNavajo();
	  }
  }
  
  public Navajo getOutputPart(InputStream is) {
	
	  try {
		  Navajo outputDoc = NavajoFactory.getInstance().createNavajo();
		  Document script = createDocument(is);
		  
		  //HashSet outputMessages = new HashSet();
		  
		  if ( script.getElementsByTagName("tsl").getLength() == 0 ) {
			    return NavajoFactory.getInstance().createNavajo();
		  }
		  NodeList list = script.getElementsByTagName("tsl").item(0).getChildNodes();
		  for (int i = 0; i < list.getLength(); i++) {
//			  if (list.item(i).getNodeName().equals("message")) {
//				  Element e = (Element) list.item(i);
//				  outputMessages.add(e.getAttribute("name"));
//			  }
			  generateOutputPart(null, outputDoc, list.item(i));
		  }
		  return outputDoc;
	  } catch (Exception e) {
		  e.printStackTrace();
		  return NavajoFactory.getInstance().createNavajo();
	  }
  }

  @SuppressWarnings({ "unchecked", "deprecation" })
  public static void main(String args[]) throws Exception {

      Generate gen = new Generate();

    
      
      // Determine input messages:
      FileInputStream fis = new FileInputStream("/home/arjen/projecten/NavajoStandardEdition/scripts/person/ProcessSearchPersons.xml");
      Navajo inputDoc = gen.getInputPart(null, fis);
      fis.close();
      ArrayList msgs = inputDoc.getAllMessages();
      HashSet inputMessages = new HashSet();
      for (int i = 0; i < msgs.size(); i++) {
        inputMessages.add(((Message) msgs.get(i)).getName());
      }
      //inputDoc.write(System.err);
      fis = new FileInputStream("/home/arjen/projecten/NavajoStandardEdition/scripts/person/ProcessSearchPersons.xml");
      Navajo outputDoc = gen.getOutputPart(fis);
      fis.close();
     
      System.out.println(outputDoc.toString());

      //Document input = (Document) result.getMessageBuffer();
      //String inputMessage = "get"+"ProcessInsertKernMember"+"In";
      //gen.createMessageDefinition(input, inputMessage, inputMessages);
      //String outputMessage = "get"+"ProcessInsertKernMember"+"Out";
      //gen.createMessageDefinition(input, outputMessage, outputMessages);

      javax.xml.parsers.DocumentBuilderFactory builderFactory  = DocumentBuilderFactory.newInstance();
      javax.xml.parsers.DocumentBuilder builder = builderFactory.newDocumentBuilder();
      
      Document dIn = builder.parse(new StringBufferInputStream(inputDoc.toString()));
      
      Document dOut = builder.parse(new StringBufferInputStream(outputDoc.toString()));
      
      System.err.println("INPUT XML:");
      inputDoc.write(System.err);
      
      String xmlInput = XMLDocumentUtils.transform(dIn, new File("/home/arjen/projecten/Navajo/soap/tml2xml.xsl"));
      String xmlOutput = XMLDocumentUtils.transform(dOut, new File("/home/arjen/projecten/Navajo/soap/tml2xml.xsl"));

      System.err.println("XML INPUT:");
      System.err.println(xmlInput);

      System.err.println("XML OUTPUT:");

      System.err.println(xmlOutput);
  }
}
