package com.dexels.navajo.document.nanoimpl;

/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.base.*;

import java.util.regex.*;
import java.io.*;

public class MessageImpl
    extends BaseMessageImpl
    implements Message, NanoElement {

    
  public MessageImpl(Navajo n) {
    super(n);
   }

  public MessageImpl(Navajo n, String name) {
    super(n,name);
   }

  public final XMLElement generateTml(NavajoImpl d, Header h, XMLElement m, boolean condense, String method) {
    for (int i = 0; i < getChildMessageCount(); i++) {
      MessageImpl msg = (MessageImpl) getMessage(i);
      // Check if filter is defined for required message.
      if (msg != null && d.includeMessage(msg, method)) {
        m.addChild(msg.toXml(m, condense, method));
      }
    }
    return m;
  }

  public XMLElement toXml(XMLElement parent) {
  	return toXml(parent, false, null);
  }

  public XMLElement toXml(XMLElement parent, boolean condense, String method) {
    XMLElement m = new CaseSensitiveXMLElement();
    m.setAttribute("name", myName);
    toXmlElement(m, condense, method);
    m.setName("message");
   return m;
  }

  final void toXmlElement(XMLElement m, boolean condense, String method) {
    if (definitionMessage!=null && !condense) {
      m.addChild(((MessageImpl)definitionMessage).toXml(m));
    }
    if ( (getType() != null) && (!"".equals(getType())) &&
        (!Message.MSG_TYPE_SIMPLE.equals(getType()))) {
      m.setAttribute(MSG_TYPE, getType());
    }
    if (getType().equals(MSG_TYPE_ARRAY_ELEMENT)) {
      m.setAttribute("index", "" + getIndex());
    }
    if ( (getMode() != null) && (!"".equals(getMode()))) {
      m.setAttribute("mode", "" + getMode());
    }

    if (getStartIndex() >= 0) {
      m.setAttribute("startindex", "" + getStartIndex());
    }
    if (getEndIndex() >= 0) {
      m.setAttribute("endindex", "" + getEndIndex());
    }

    NavajoImpl d = (NavajoImpl) getRootDoc();

    for (int i = 0; i < getChildMessageCount(); i++) {
      MessageImpl msg = (MessageImpl) getMessage(i);
      if (msg != null && d.includeMessage(msg, method)) {
        m.addChild(msg.toXml(m, condense, method));
      }
    }

    Message parent = getArrayParentMessage();
    Message def = null;
    if (parent!=null ) {
      def = parent.getDefinitionMessage();
    }

       Iterator props = propertyList.iterator();
      while (props.hasNext()) {
        PropertyImpl p = (PropertyImpl) props.next();
        m.addChild(p.toXml(m, condense, method));
      }

  }

  public void fromXml(XMLElement e) {
    fromXml(e,null);
  }


  public void fromXml(XMLElement e, MessageImpl defParent) {
    for (int i = 0; i < e.countChildren(); i++) {
      XMLElement child = (XMLElement) e.getChildren().elementAt(i);
      String name = child.getName();
      if (name.equals("property")) {
        /** @todo Beware: Will things be affected? */
        PropertyImpl p = null;
        try {
          p = (PropertyImpl) NavajoFactory.getInstance().createProperty(
              myDocRoot, (String) child.getAttribute("name"), "", "", 0, "", "");
        }
        catch (NavajoException ex) {
          ex.printStackTrace();
        }
         if (defParent!=null) {
//           System.err.println("Defparent present");
          p.fromXml(child,defParent);
        } else {
//          if (myDefinitionList!=null) {
//            p.fromXml(child,this);
//          } else {
            p.fromXml(child);
//          }
        }
        this.addProperty(p);

      }
      if (name.equals("message")) {
        String childName = (String) child.getAttribute("name");
        String type = (String) child.getAttribute(MSG_TYPE);
        String index = (String) child.getAttribute(MSG_INDEX);
        String mode = (String) child.getAttribute(MSG_MODE);

        // Ok, now a simple implentation of the laziness check.
        MessageImpl msg = null;
//        if (false) {
        if (MSG_MODE_LAZY.equals(mode) && child.getAttribute(Message.MSG_LAZY_REMAINING)!=null) {
//          System.err.println("YES! A lazy message!");
//          System.err.println("CONSTRUCTING LAZY MESSAGE: \n");
//           System.err.println("\n\n");
          int lazyRemaining = Integer.parseInt( (String) child.getAttribute(
              Message.MSG_LAZY_REMAINING));
           int currentTotal = Integer.parseInt( (String) child.getAttribute(
              Message.MSG_ARRAY_SIZE));
          System.err.println("lazyRemaining = " + lazyRemaining +
                             ", current total = " + currentTotal + ", total = " +
                             child.getAttribute(Message.MSG_LAZY_COUNT));
          int windowSize = 100;
          if (lazyRemaining == 0) {
            windowSize = 0;
          }
          else if (lazyRemaining < currentTotal) {
            windowSize = lazyRemaining;
          }
          else {
            windowSize = currentTotal;
          }
          if (windowSize < 0) {
            windowSize = 0;
          }
          System.err.println("windowSize = " + windowSize);

          msg = (MessageImpl) NavajoFactory.getInstance().createLazyMessage(
              myDocRoot, childName, windowSize);
          if (type != null) {
            msg.setType(type);
          }
          if (definitionMessage!=null) {
            msg.fromXml(child,this);
          } else {
            msg.fromXml(child);
          }
          msg.setCurrentTotal(currentTotal);
          if ( (index != null) && !index.equals("")) {
            msg.setIndex(Integer.parseInt(index));
            msg.setType(MSG_TYPE_ARRAY_ELEMENT);
          }
        }
        else {
          msg = (MessageImpl) NavajoFactory.getInstance().createMessage(myDocRoot, childName);
          if (type != null) {
            msg.setType(type);
          }
          if ( (index != null) && !index.equals("") && MSG_TYPE_DEFINITION.equals(getType())) {
            msg.setIndex(Integer.parseInt(index));
            msg.setType(MSG_TYPE_ARRAY_ELEMENT);
          }
          if (definitionMessage!=null) {
            msg.fromXml(child,this);
          } else {
            msg.fromXml(child);
          }

        }


//            System.err.println("Defparent not present, definitionlist present");
        if (msg.getType().equals(MSG_TYPE_DEFINITION)) {
          definitionMessage = msg;
        } else {
        this.addMessage(msg);
        }
//        System.err.println("CONSTRUCTED THE FOLLOWING:");
      }
    }

    // Check for missing properties that exist in the definition
//    System.err.println("Parsing message. Looking for missing properties: ");
    if (defParent!=null && defParent.getDefinitionMessage()!=null) {
//      System.err.println("Ok, searching");
      ArrayList myDefinitionList = defParent.getDefinitionMessage().getAllProperties();
//      System.err.println("# of properties found in definition: "+myDefinitionList.size());
      for (int j = 0; j < myDefinitionList.size(); j++) {
        PropertyImpl pq = (PropertyImpl)myDefinitionList.get(j);
        String pname = pq.getName();
        if (getProperty(pname)==null) {
          //System.err.println("\n\nCreating prop: "+pname+" ::: "+getIndex());
          PropertyImpl pi = (PropertyImpl)pq.copy(getRootDoc());
          addProperty(pi);
          //System.err.println("pi::::::::::: "+pi.toXml(null).toString());
        }
      }
    }

  }

  public final Object getRef() {
    return toXml(null);
  }

  public final void write(java.io.Writer writer) {
    try {
      toXml(null).write(writer);
      // TODO REMOVE FLUSH?
      writer.flush();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }

  }

  public final void write(java.io.OutputStream o) {
    try {
      OutputStreamWriter w = new OutputStreamWriter(o);
      toXml(null).write(w);
      w.flush();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

public XMLElement toXml() {
    return toXml(null);
}

public final void writeComponent(Writer w) throws IOException {
    toXml().write(w);
}



}
