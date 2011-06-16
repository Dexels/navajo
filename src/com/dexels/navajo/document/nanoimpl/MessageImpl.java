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

import java.io.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.base.*;

/**
 * 
 * @author Frank Lyaruu
 * @deprecated
 */
@Deprecated
public class MessageImpl
    extends BaseMessageImpl
    implements Message, NanoElement {

    
	private static final long serialVersionUID = 8315435096586830301L;

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

   // Message parent = getArrayParentMessage();
//    Message def = null;
//    if (parent!=null ) {
//      def = parent.getDefinitionMessage();
//    }

    if (propertyList!=null) {
        Iterator<Property> props = propertyList.iterator();
        while (props.hasNext()) {
          PropertyImpl p = (PropertyImpl) props.next();
          m.addChild(p.toXml(m, condense, method));
        }
	}

  }

  public void fromXml(XMLElement e) {
    fromXml(e,null);
  }


  public void fromXml(XMLElement e, MessageImpl defParent) {
    for (int i = 0; i < e.countChildren(); i++) {
      XMLElement child = e.getChildren().get(i);
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
        if ( p == null ) {
      	  return;
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
//        String mode = (String) child.getAttribute(MSG_MODE);

        // Ok, now a simple implentation of the laziness check.
        MessageImpl msg = null;
//        if (false) {

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

//            System.err.println("Defparent not present, definitionlist present");
        if (msg.getType().equals(MSG_TYPE_DEFINITION)) {
          this.setDefinitionMessage(msg);
        } else {
        	this.addMessage(msg);
        }
//        System.err.println("CONSTRUCTED THE FOLLOWING:");
      }
    }

  }

  public final Object getRef() {
    return toXml(null);
  }

  public final void write(java.io.Writer writer) {
    try {
      toXml(null).write(writer);
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
