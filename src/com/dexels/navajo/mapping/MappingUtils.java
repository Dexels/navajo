package com.dexels.navajo.mapping;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.util.Util;
import com.dexels.navajo.parser.*;

import java.lang.reflect.*;
import java.util.*;

import com.dexels.navajo.document.types.Memo;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.document.types.StopwatchTime;

public final class MappingUtils {

    public static final String getStrippedPropertyName(String name) {
        StringTokenizer tok = new StringTokenizer(name, Navajo.MESSAGE_SEPARATOR);
        String result = "";

        while (tok.hasMoreElements()) {
          result = tok.nextToken();
        }
        return result;
    }

    public static final String determineNavajoType(Object o) throws TMLExpressionException {

         if (o == null) {
           return "";
         }
         if (o instanceof Integer)
            return Property.INTEGER_PROPERTY;
        else if (o instanceof String)
            return Property.STRING_PROPERTY;
        else if (o instanceof java.util.Date)
            return Property.DATE_PROPERTY;
        else if (o instanceof Double)
            return Property.FLOAT_PROPERTY;
        else if (o instanceof ArrayList)
            return Property.SELECTION_PROPERTY;
        else if (o instanceof Boolean)
            return Property.BOOLEAN_PROPERTY;
        else if (o.getClass().getName().startsWith("[Ljava.util.Vector"))
            return Property.POINTS_PROPERTY;
        // Added by arjen 19/2/2004.
        else if (o instanceof Money)
          return Property.MONEY_PROPERTY;
        else if (o instanceof Percentage)
          return Property.PERCENTAGE_PROPERTY;
        else if (o instanceof ClockTime)
          return Property.CLOCKTIME_PROPERTY;
        else if (o instanceof StopwatchTime)
            return Property.STOPWATCHTIME_PROPERTY;
        // Added by frank... To enable tipi-expressions, without creating a dep
        else if (o.getClass().getName().startsWith("com.dexels.navajo.tipi"))
          return Property.TIPI_PROPERTY;
        else if (o instanceof Message)
          return Message.MSG_DEFINITION;
        else if (o instanceof Binary)
          return Property.BINARY_PROPERTY;
        else if (o instanceof Memo)
            return Property.MEMO_PROPERTY;
        else if (o instanceof Selection []) {
        	return Property.SELECTION_PROPERTY;
        }
        else
          return "unknown";

//            throw new TMLExpressionException("Could not determine NavajoType for Java type: " + o.getClass().getName());
    }

   public static final Message getMessageObject(String name, Message parent,
                                                boolean messageOnly,
                                                Navajo source, boolean array,
                                                String mode, int useElementIndex) throws NavajoException {
    Message msg = parent;

    if (name.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We have an absolute message reference.
      msg = null;
      name = name.substring(1, name.length());
    }
    StringTokenizer tok = new StringTokenizer(name, Navajo.MESSAGE_SEPARATOR);
    // Create and/or find all required messages.
    int count = tok.countTokens();
    Message newMsg = null;

    if (!messageOnly) {
      count = count - 1;
      newMsg = msg;
    }
    for (int i = 0; i < count; i++) {
      String messageName = tok.nextToken();
      while (messageName.equals(Navajo.PARENT_MESSAGE)) {
        messageName = tok.nextToken();
        msg = msg.getParentMessage();
        i++;
      }
      if (i < count) {
        if (msg == null) {
          newMsg = source.getMessage(messageName);
        }
        else {
          if (!msg.getType().equals(Message.MSG_TYPE_ARRAY) || (useElementIndex != -1)) { // For array type messages always add element message!!!
            if (!msg.getType().equals(Message.MSG_TYPE_ARRAY))
              newMsg = msg.getMessage(messageName);
            else
              newMsg = msg.getMessage(useElementIndex);
          }
        }
        if (newMsg == null) {
          newMsg = NavajoFactory.getInstance().createMessage(source,
              messageName,
              (array ? Message.MSG_TYPE_ARRAY : ""));
          if (!mode.equals("")) {
            newMsg.setMode(mode);
          }
          if (msg == null) {
            source.addMessage(newMsg);
          }
          else {
            msg.addMessage(newMsg);
          }
        }
        msg = newMsg;
      }
      else {
        newMsg = msg;
      }
    }

    if (array) {
      newMsg.setType(Message.MSG_TYPE_ARRAY);

    }
    return newMsg;
  }



   public static final Property setProperty(boolean parameter, Message msg, String name,
                                            Object value, String type, String subtype, String direction,
                                            String description,
                                            int length, Navajo outputDoc, Navajo tmlDoc, boolean remove)
       throws NavajoException, MappingException {

    Message ref = null;


    if (parameter) {
      if (msg == null) {
        msg = tmlDoc.getMessage("__parms__");
      }
      ref = getMessageObject(name, msg, false, tmlDoc, false, "", -1);
    }
    else {
      ref = getMessageObject(name, msg, false, outputDoc, false, "", -1);
    }

    String sValue = (value != null ? Util.toString(value, type) : null);


    if (ref == null) {
      ref = msg;
    }
    String actualName = getStrippedPropertyName(name);

    if (ref == null) {
      throw new MappingException("Property can only be created under a message");
    }

    Property prop = ref.getProperty(actualName);

    // Remove a parameter if remove flag is set.
    if (remove && prop != null && parameter) {
      ref.removeProperty(prop);
      return null;
    }
    if (prop == null && remove && parameter) {
      return null;
    }

    if (prop == null) { // Property does not exist.
    	if (!parameter) {
    		if (type.equals(Property.SELECTION_PROPERTY)) {
    			prop = NavajoFactory.getInstance().createProperty(outputDoc, actualName, "1", description, direction);
    			if ( value instanceof Selection [] ) {
    				prop.setCardinality("+");
    				prop.setValue((Selection []) value);
    			}
    		} else if (type.equals(Property.BINARY_PROPERTY)) {
    			prop = NavajoFactory.getInstance().createProperty(outputDoc, actualName, type, "", length, description, direction);
    			if (value != null && value instanceof Binary) {
    				prop.setValue( (Binary) value);
    			}
    		} 
    		else {
    			prop = NavajoFactory.getInstance().createProperty(outputDoc, actualName, type, sValue, length, description, direction);
    		}
    	}
    	else {
    		if (type.equals(Property.SELECTION_PROPERTY)) {
    			prop = NavajoFactory.getInstance().createProperty(tmlDoc, actualName, "1", description, direction);
    			if ( value instanceof Selection [] ) {
    				prop.setCardinality("+");
    				prop.setValue((Selection []) value);
    			}
    		} else if (type.equals(Property.BINARY_PROPERTY)) {
    			prop = NavajoFactory.getInstance().createProperty(tmlDoc, actualName, type, "", length, description, direction);
    			if (value != null && value instanceof Binary) {
    				prop.setValue( (Binary) value);
    			}
    		}
    		else {
    			prop = NavajoFactory.getInstance().createProperty(tmlDoc, actualName, type, sValue, length, description, direction);
    		}
    	}
    	ref.addProperty(prop);
    }
    else {
    	prop.setType(type);
    	if (type.equals(Property.BINARY_PROPERTY)) {
    		if (value != null && (value instanceof Binary)) {
    			prop.setValue( (Binary) value);
    		} else {
    			prop.clearValue();
    		}
    	}  else if ( type.equals(Property.SELECTION_PROPERTY) && value != null && value instanceof Selection [] ) {
    		prop.setCardinality("+");
    		prop.setValue((Selection []) value);
    	} else {
    		if (sValue != null) {
    			prop.setValue(sValue);
    		}
    		else {
    			prop.clearValue();
    		}
    	}

      prop.setName(actualName); // Should not matter ;)
    }

    // Set subtype if not empty.
    if (subtype != null && !subtype.equals("")) {
      prop.setSubType(subtype);
    }
    return prop;
  }

   public static final Message[] addMessage(Navajo doc, Message parent, String message,
                                      String template, int count,
                                      String type, String mode) throws java.io.IOException, NavajoException,
                                      org.xml.sax.SAXException, MappingException {

    if (message.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) {
      throw new MappingException(
          "No submessage constructs allowed in <message> tags: " + message);
    }
    Message[] messages = new Message[count];
    Message msg = null;
    int index = 0;

    // Check for existing message.
    Message existing = null;
    if (parent != null) {
      existing = parent.getMessage(message);
    } else {
      existing = doc.getMessage(message);
    }

    // If there is an existing message withe same name and this message has a parent that is NOT an arrayMessage
    // return this message as a result. If it has an array message parent, it is assumed that the new message
    // is put under the existing array message parent.
    if ((existing != null)) {
      if (parent != null && !parent.isArrayMessage()) {
        messages[0] = existing;
        return messages;
      } else if (parent == null) {
        messages[0] = existing;
        return messages;
      }
    }

    if (!template.equals("")) { // Read template file.
      throw new MappingException("TEMPLATES ARE NOT SUPPORTED");
      //Navajo tmp = NavajoFactory.getInstance().createNavajo(config.getTemplate(
      //    template));
      //Message bluePrint = tmp.getMessage(template);
      //bluePrint.setName(message);
      //msg = tmp.copyMessage(bluePrint, doc);
    }
    else {
      msg = NavajoFactory.getInstance().createMessage(doc, message);
    }

    if (!mode.equals("")) {
      msg.setMode(mode);
    }

    if (count > 1) {
      msg.setName(message + "0");
      msg.setIndex(0);
      //msg.setType(Message.MSG_TYPE_ARRAY);
      if (!mode.equals(Message.MSG_MODE_IGNORE)) {
          if (parent == null) {
            msg = doc.addMessage(msg, false);
          }
          else {
            msg = parent.addMessage(msg, false);
          }
        }
      messages[index++] = msg;
    } else if (count == 1) {
      if (!mode.equals(Message.MSG_MODE_IGNORE)) {
        if (parent == null) {
          msg = doc.addMessage(msg, false);
        }
        else {
          msg = parent.addMessage(msg, false);
        }
      }
      messages[index++] = msg;
      if (!type.equals("")) {
        msg.setType(type);
      }
    }

    // Add additional messages based on the first messages that was added.
    for (int i = 1; i < count; i++) {
      Message extra = doc.copyMessage(msg, doc);
      extra.setName(message + i);
      extra.setIndex(i);
      if (parent == null) {
        extra = doc.addMessage(extra, false);
      }
      else {
        extra = parent.addMessage(extra, false);
      }
      messages[index++] = extra;
    }

    return messages;
  }

  public static final  ArrayList getSelectedItems(Message msg, Navajo doc, String msgName) throws
      NavajoException {
    Message ref = null;
    Property prop = null;
    ArrayList result = new ArrayList();

    if (msg != null) {
      prop = msg.getProperty(msgName);
    }
    else {
      prop = doc.getProperty(msgName);
    }
    if (!prop.getType().equals(Property.SELECTION_PROPERTY)) {
      throw NavajoFactory.getInstance().createNavajoException(
          "Selection Property expected");
    }
    result = prop.getAllSelectedSelections();

    return result;
  }


  public static final ArrayList getMessageList(Message msg, Navajo doc, String str, String filter, MappableTreeNode o, 
  		Message currentParamMsg) throws
      NavajoException, SystemException, MappingException, TMLExpressionException {
    //try {
      ArrayList result = new ArrayList();

      if (str.equals("")) {
        result.add(NavajoFactory.getInstance().createMessage(doc,
            "__JUST_FOR_ITERATING_ONCE__"));
        return result;
      }
      else {
        if (str.startsWith(Navajo.MESSAGE_SEPARATOR)) {
          msg = null;
          str = str.substring(1, str.length());
        }
        if (msg != null) {
          result = msg.getMessages(str);
        }
        else {
          result = doc.getMessages(str);
          // If filter is defined use it to filter out the proper messages. Filter contains an expression that uses
          // the matched message as offset (parent) message.
        }
        if (!filter.equals("")) {
          ArrayList dummy = new ArrayList();
          for (int i = 0; i < result.size(); i++) {
            Message parent = (Message) result.get(i);
            boolean match = Condition.evaluate(filter, doc, o, parent, currentParamMsg);
            //System.err.println("getMessageList(), filter = " + filter + ", match = " + match);
            if (match) {
              dummy.add(parent);
            }
          }
          result = dummy;
        }
        return result;
      }
    //}
    //catch (com.dexels.navajo.parser.TMLExpressionException tmle) {
   //   tmle.printStackTrace();
   //   throw new MappingException(tmle.getMessage() + showNodeInfo(currentNode));
    //}
  }

  public static final boolean isMappable(Class c, String field, ClassLoader loader) throws UserException, ClassNotFoundException {
  	try {
        Class mappable = Class.forName("com.dexels.navajo.mapping.Mappable",true,loader);
        if (c.getField(field) == null) {
        	throw new UserException(-1, "No such field: " + field);
        }
        return mappable.isAssignableFrom(c.getField(field).getType());
      
  	} catch (NoSuchFieldException nsfe) {
        throw new UserException(-1, "Could not find field " + field + " in class " + c.getName());
    }    
  }
     
  public static final String getFieldType(Class c, String field) throws UserException {

    try {
      String type = c.getField(field).getType().getName();
      if (type.startsWith("[L")) { // We have an array determine member type.
        type = type.substring(2, type.length() - 1);
      }
      return type;
    } catch (NoSuchFieldException nsfe) {
      throw new UserException(-1, "Could not find field " + field + " in class " + c.getName());
    }
  }

  public static final Map getAllFields(Class c) {
      Map ll = new HashMap();
      Field[] f = c.getFields(); 
      for (int i = 0; i < f.length; i++) {
          boolean pblc = Modifier.isPublic(f[i].getModifiers());
          String type = f[i].getType().getName();
          if (type.startsWith("[L")) { // We have an array determine member type.
            type = type.substring(2, type.length() - 1);
          }
          if (pblc) {
              ll.put(f[i].getName(), type);
          }
    }
      return ll;
  }
  
  public static final boolean isSelection(Message msg, Navajo doc, String msgName) {

    Message ref = null;
    Property prop = null;

    if (msgName.startsWith(Navajo.MESSAGE_SEPARATOR)) { // Absolute reference!
      msg = null;
      msgName = msgName.substring(1, msgName.length());
    }
    if (msg != null) {
      prop = msg.getProperty(msgName);
    }
    else {
      prop = doc.getProperty(msgName);

    }
    if (prop == null) {
      return false;
    }
    if (prop.getType().equals(Property.SELECTION_PROPERTY)) {
      return true;
    }
    else {
      return false;
    }
  }

  public static final boolean isArrayAttribute(Class c, String field) throws NoSuchFieldException,
      MappingException {

      String objectType = c.getField(field).getType().getName();
      return objectType.startsWith("[L");

  }

  public static final String createPackageName(String packagePath) {

    if (packagePath.equals(""))
      return packagePath;

    packagePath = packagePath.replaceAll("/", ".");
    packagePath = (packagePath.charAt(packagePath.length()-1) == '.' ? packagePath.substring(0, packagePath.length()-1) : packagePath);
    return packagePath;
  }

  public static final String createPackagePath(String packageName) {

    if (packageName.equals(""))
      return "";

    packageName = packageName.replaceAll("\\.", System.getProperty("file.separator"));

//    packagePath = (packagePath.charAt(packagePath.length()-1) == '.' ? packagePath.substring(0, packagePath.length()-1) : packagePath);
    return System.getProperty("file.separator")+packageName;
  }

}
