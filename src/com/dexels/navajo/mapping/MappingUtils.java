package com.dexels.navajo.mapping;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.util.Util;
import com.dexels.navajo.parser.*;

import java.util.StringTokenizer;
import java.util.ArrayList;

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
        else if (o instanceof Double) {
            return Property.FLOAT_PROPERTY;
        } else if (o instanceof ArrayList)
            return Property.SELECTION_PROPERTY;
        else if (o instanceof Boolean)
            return Property.BOOLEAN_PROPERTY;
          else if (o instanceof Property)
              return "unknown";
        else if (o.getClass().getName().startsWith("[Ljava.util.Vector")) {
            return Property.POINTS_PROPERTY;
        }
        // Added by frank... To enable tipi-expressions, without creating a dep
            else if (o.getClass().getName().startsWith("com.dexels.navajo.tipi"))
                return Property.TIPI_PROPERTY;
              else if (o instanceof Message)
                  return Message.MSG_DEFINITION;
         else
            throw new TMLExpressionException("Could not determine NavajoType for Java type: " + o.getClass().getName());
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
                                            Object value, String type, String direction,
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
        if (type.equals(Property.SELECTION_PROPERTY))
          prop = NavajoFactory.getInstance().createProperty(outputDoc, actualName, "1", description, direction);
        else
          prop = NavajoFactory.getInstance().createProperty(outputDoc, actualName, type, sValue, length, description,
                                                         direction);
      }
      else {
        prop = NavajoFactory.getInstance().createProperty(tmlDoc, actualName, type, sValue, length, description,
                                                          direction);
      }
      ref.addProperty(prop);
    }
    else {
      prop.setType(type);
      if (sValue != null)
          prop.setValue(sValue);
      else
         prop.clearValue();

      prop.setName(actualName); // Should not matter ;)
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
      if (parent == null) {
        msg = doc.addMessage(msg, false);
      }
      else {
        msg = parent.addMessage(msg, false);
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

  public static final ArrayList getMessageList(Message msg, Navajo doc, String str, String filter, MappableTreeNode o) throws
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
            boolean match = Condition.evaluate(filter, doc, o, parent);
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

  public static final String getFieldType(Class c, String field) throws NoSuchFieldException {

      String type = c.getField(field).getType().getName();
      if (type.startsWith("[L")) { // We have an array determine member type.
        type = type.substring(2, type.length() - 1);
      }
      return type;

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