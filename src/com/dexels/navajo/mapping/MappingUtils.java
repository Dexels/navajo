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

@SuppressWarnings("unchecked")
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
        else if (o instanceof Float)
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
     
      newMsg = null;
      String messageName = tok.nextToken();
      
      while (messageName.equals(Navajo.PARENT_MESSAGE)) {
        messageName = tok.nextToken();
        msg = msg.getParentMessage();
        i++;
      }
       
      if (i < count) {

    	  int arrayChild = messageName.indexOf("@");
    	  if ( arrayChild != -1 ) {
    		  useElementIndex =  Integer.parseInt(messageName.substring(arrayChild+1));
    		  messageName = messageName.substring(0, arrayChild);
    	  } 

    	  if (msg == null) {
    		  newMsg = source.getMessage(messageName);
    	  }
    	  else {
    		  if (!msg.getType().equals(Message.MSG_TYPE_ARRAY) || (useElementIndex != -1)) { // For array type messages always add element message!!!
    			  if (!msg.getType().equals(Message.MSG_TYPE_ARRAY)) {
    				  newMsg = msg.getMessage(messageName);
    			  }
    			  else {
    				  newMsg = msg.getMessage(useElementIndex);
    			  }
    		  }
    	  }

    	  if (newMsg == null) {

    		  if ( arrayChild != -1 ) {
    			  if ( msg != null ) 
    			  { 
    				  msg.setType("array"); 
    			  } else {
    				  throw NavajoFactory.getInstance().createNavajoException(new Exception("Can only create array elements inside array message"));
    			  }
    		  }

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
    			// Legacy mode hack, many scripts do not expect null valued string properties.
    			if ( type.equals(Property.STRING_PROPERTY) && sValue == null ) {
    				sValue = "";
    			}
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
       String type, String mode, String orderby) throws java.io.IOException, NavajoException,
       org.xml.sax.SAXException, MappingException {
	 
	 Message[] msgs = addMessage(doc, parent, message, template, count, type, mode);
	 
	 if ( orderby != null && !orderby.equals("")) {
		 for(int i=0;i<msgs.length;i++){
			 msgs[i].setOrderBy(orderby);
		 }	 
	 }
	 
	 return msgs;
   }

   public static final String getBaseMessageName(String name) {
	   if ( name.startsWith("../") ) {
		   return getBaseMessageName(name.substring(3));
	   }
	   return name;
   }
   
   /**
    * Get parent message of a (possibly non-existing) message name.
    * 
    * @param parent
    * @param name
    * @return
    */
   public static final Message getParentMessage(Message parent, String name) {
	   if ( name.startsWith("../") ) {
		   return getParentMessage(parent.getParentMessage(), name.substring(3));
	   }
	   return parent;
   }
   
   public static final Message[] addMessage(Navajo doc, Message parent, String message,
                                      String template, int count,
                                      String type, String mode) throws java.io.IOException, NavajoException,
                                      org.xml.sax.SAXException, MappingException {

	/**
	 * Added 22/5/2007: support for relative message creation.
	 */
    if ( message.indexOf(Navajo.MESSAGE_SEPARATOR) != -1 && parent == null ) {
      throw new MappingException(
          "No submessage constructs allowed in non-nested <message> tags: " + message);
    }
    
    Message[] messages = new Message[count];
    Message msg = null;
    int index = 0;

    // Check for existing message.
    Message existing = null;
    
    /**
     * Get the real parent message given the fact that message could contain a relative name.
     */
    parent = getParentMessage(parent, message);
    
    if (parent != null) {
      existing = parent.getMessage(getBaseMessageName(message));
    } else {
      existing = doc.getMessage(message);
    }

    if ( mode.equals(Message.MSG_MODE_OVERWRITE ) && existing != null ) {
    	// remove existing message.
    	
    	if ( parent != null ) {
    		parent.removeMessage(existing);
    	} else {
    		doc.removeMessage(existing);
    	}
    	existing = null;
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
      /**
       * Added getBaseMessageName to support relative message creation.
       */
      msg = NavajoFactory.getInstance().createMessage(doc, getBaseMessageName(message));
    }

    if (!mode.equals("")) {
      msg.setMode(mode);
    }
  
    if (count > 1) {
      msg.setName(getBaseMessageName(message) + "0");
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
      extra.setName(getBaseMessageName(message) + i);
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
    //Message ref = null;
    Property prop = null;
    ArrayList result = null;

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

      // Simply return current message if . is given.
      if (str.equals(".")) {
    	  result.add(msg);
    	  return result;
      }
      
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
  }

//  public static final boolean isMappable(Class c, String field, ClassLoader loader) throws UserException, ClassNotFoundException {
//	  Class mappable = Class.forName("com.dexels.navajo.mapping.Mappable",true,loader);
//
//	  try {
//		  if (c.getField(field) == null) {
//			  throw new UserException(-1, "No such field: " + field);
//		  }
//		  return mappable.isAssignableFrom(c.getField(field).getType());
//	  } catch (NoSuchFieldException nsfe) {
//		  try {
//			  return mappable.isAssignableFrom(c.getDeclaredField(field).getType());
//		  } catch (SecurityException e) {
//			  throw new UserException(-1, "Could not find field " + field + " in class " + c.getName());
//		  } catch (NoSuchFieldException e) {
//			  throw new UserException(-1, "Could not find field " + field + " in class " + c.getName());
//		  }
//	  }    
//  }
     
  public static final boolean isObjectMappable(String className) throws UserException {
	  try {
		  Class c = Class.forName(className, true, NavajoConfig.getInstance().getClassloader());
		  return ( c.newInstance() instanceof Mappable );
	  } catch (Exception e) {
		  e.printStackTrace(System.err);
		  throw new UserException(-1, "Could not handle class as either mappable or POJO bean: " + className + ", cause: " + e.getMessage());
	  }
  }
  
  public static final void callStoreMethod(Object o) throws MappableException,
  MappingException, UserException {
	  if (o == null || !(o instanceof Mappable)) {
		  return;
	  }
	  if (o instanceof Mappable) {
		  ( (Mappable) o).store();
	  }
  }
  
  public static final void callKillMethod(Object o) throws MappableException,
  MappingException, UserException {

	  if (o == null || !(o instanceof Mappable)) {
		  return;
	  }
	  if (o instanceof Mappable) {
		  ( (Mappable) o).kill();
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

		  try {
			  String type = c.getDeclaredField(field).getType().getName();
			  if (type.startsWith("[L")) { // We have an array determine member type.
				  type = type.substring(2, type.length() - 1);
			  }
			  return type;
		  } catch (NoSuchFieldException nsfe2) {
			  throw new UserException(-1, "Could not find field " + field + " in class " + c.getName());
		  }
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

    //Message ref = null;
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

  public static final boolean isArrayAttribute(Class c, String field) throws  MappingException {

	  String objectType;
	  try {
		  objectType = c.getField(field).getType().getName();
		  return objectType.startsWith("[L");
	  } catch (Exception e) {
		  try {
			  objectType = c.getDeclaredField(field).getType().getName();
			  return objectType.startsWith("[L");
		  } catch (Exception e1) {
			  // TODO Auto-generated catch block
			  throw new MappingException(e1.getMessage());
		  } 
	  } 
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

  public final static Object getAttributeObject(MappableTreeNode o, String name, Object[] arguments) 
     throws com.dexels.navajo.server.UserException, MappingException {

	  Object result = null;
	  String methodName = "";

	  try {
		  java.lang.reflect.Method m = o.getMethodReference(name, arguments);
		  result = m.invoke(o.myObject, arguments);
	  }
	  catch (IllegalAccessException iae) {
		  throw new MappingException(methodName +
				  " illegally accessed in mappable class: " +
				  o.myObject.getClass().getName());
	  }
	  catch (InvocationTargetException ite) {
		  ite.printStackTrace();
		  Throwable t = ite.getTargetException();
		  if (t instanceof com.dexels.navajo.server.UserException) {
			  throw (com.dexels.navajo.server.UserException) t;
		  }
		  else {
			  throw new MappingException("Illegal exception thrown: " + t.getMessage());
		  }
	  }
	  return result;
  }
  
  /**
   * The next two methods: getAttribute()/setAttribute() are used for the set/get functionality
   * of the new Mappable interface (see also Java Beans standard). The use of set/get methods
   * enables the use of triggers that can be implemented within the set/get methods.
   * So if there is a field:
   * private double noot;
   * within a Mappable object, the following methods need to be implemented:
   * public double getNoot();
   * and
   * public void setNoot(double d);
   */
  public final static Object getAttributeValue(MappableTreeNode o, String name, Object[] arguments) throws com.dexels.
      navajo.server.UserException,
      MappingException {

    Object result = null;
    // The ../ token is used to denote the parent of the current MappableTreeNode.
    // e.g., $../myField or $../../myField is used to identifiy respectively the parent
    // and the grandparent of the current MappableTreeNode.

    
    while ( (name.indexOf("../")) != -1) {
      o = o.parent;
      if (o == null) {
        throw new MappingException("Null parent object encountered: " + name);
      }
      name = name.substring(3, name.length());
    }

    result = getAttributeObject(o, name, arguments);

    if (result != null) {

      //String type = result.getClass().getName();

      if (result instanceof java.lang.String) {
        return result;
      } else
      if (result instanceof java.lang.Long) {
        return new Integer(result.toString());
      }
      else if (result instanceof java.lang.Float) {
        return new Double(result.toString());
      }
      else if (result instanceof java.lang.Boolean) {
        return result;
      }
      else if (result instanceof com.dexels.navajo.document.types.Binary) {
        return result;
      }
      else if (result instanceof com.dexels.navajo.document.types.ClockTime) {
        return result;
      }
      else if (result instanceof com.dexels.navajo.document.types.Money) {
        return result;
      }
      else if (result instanceof java.util.Date) {
        return result;
      }
      else if (result instanceof java.lang.Integer) {
        return result;
      }
      else if (result instanceof java.lang.Double) {
        return result;
      }
      else if (result.getClass().getName().startsWith("[Ljava.util.Vector")) {
        return result;
      }
      else if (result.getClass().getName().startsWith("[L")) {
        // Encountered array cast to ArrayList.
        Object[] array = (Object[]) result;
        ArrayList list = new ArrayList();
        for (int i = 0; i < array.length; i++) {
          list.add(array[i]);
        }
        return list;
      }
      else {
        return result.toString();
      }
    }
    else {
      return null;
    }
  }
  
  public static void main(String [] args) throws Exception {
	  Navajo n = NavajoFactory.getInstance().createNavajo();
	  setProperty(false, null, "/Aap/Aap@0/NootProp", "Apenoot", "string", "", "in", "", 20, n, null, false);
	  setProperty(false, null, "/Aap/Aap@0/Allemaal", "Beestjes", "string", "", "in", "", 20, n, null, false);
	  
	  setProperty(false, null, "/Kibbeling/NemoProp", "Is gek", "string", "", "in", "", 20, n, null, false);
	  setProperty(false, null, "/Kibbeling/Kibbeling/WalvisProp", "Moby", "string", "", "in", "", 20, n, null, false);
	  
	  setProperty(false, null, "/Worstebroodje/@0/Worst/Hema", "Moby", "string", "", "in", "", 20, n, null, false);
	  setProperty(false, null, "/Worstebroodje/@0/Worst/CenA", "Moby", "string", "", "in", "", 20, n, null, false);
	  
	 
	  n.write(System.err);
	  
  }
}
