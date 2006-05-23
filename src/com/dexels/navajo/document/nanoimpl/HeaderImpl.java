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

import java.io.*;

public final class HeaderImpl
    extends BaseHeaderImpl
    implements Header, NanoElement {

    private String myRequestId;
  public HeaderImpl(com.dexels.navajo.document.Navajo n, String user,
                    String password, String service) {
    super(n);
    setIdentification(user, password, service);
  }

  public HeaderImpl(com.dexels.navajo.document.Navajo n) {
    super(n);
  }

  public final void fromXml(XMLElement e) {
    Enumeration en = e.enumerateChildren();
//    System.err.println("\n\nPARSING TO HEADER: ");
//    System.err.println(e.toString());
//      System.err.println("\n\nEND OF PARSING HEADER\n");
    while (en.hasMoreElements()) {
      XMLElement child = (XMLElement) en.nextElement();
      if (child.getName().equals("transaction")) {
        setIdentification(child.getStringAttribute("rpc_usr"),
                          child.getStringAttribute("rpc_pwd"),
                          child.getStringAttribute("rpc_name"));
        // Set request id.
        myRequestId = child.getStringAttribute("requestid");
        if (child.getStringAttribute("expiration_interval") != null &&
            !child.getStringAttribute("expiration_interval").equals("")) {
          setExpiration(Long.parseLong(child.getStringAttribute(
              "expiration_interval")));
        }
      }
      if (child.getName().equals("callback")) {
//        System.err.println("Parsing callback");
        Enumeration enum2 = child.enumerateChildren();
        while (enum2.hasMoreElements()) {
          XMLElement child2 = (XMLElement) enum2.nextElement();
          if (child2.getName().equals("object")) {
//            System.err.println("Parsing object");
            setCallBack(child2.getStringAttribute("name"),
                        child2.getStringAttribute("ref"),
                        child2.getIntAttribute("perc_ready"),
                        child2.getBooleanAttribute("finished", "true", "false", false),
                        child2.getStringAttribute("interrupt"));
          }
        }
      }
    }
    Enumeration ee = e.enumerateAttributeNames();
    while (ee.hasMoreElements()) {
        String element = (String) ee.nextElement();
//        System.err.println("Found header attribute: "+element);
        setAttribute(element, e.getStringAttribute(element));
    }

  }
  public final XMLElement toXml() {
      return toXml(null);
  }
  
  public final XMLElement toXml(XMLElement parent) {

//    System.err.println("Finished: "+isFinished);
//    System.err.println("PercReady: "+percReady);
//    System.err.println("Name: "+myCallbackName);
//    System.err.println("pointer: "+myCallbackPointer);
    try {
      XMLElement header = new CaseSensitiveXMLElement();
//      System.err.println("MY USERNAME: "+getRPCUser());
      header.setName("header");

      XMLElement transaction = new CaseSensitiveXMLElement();
      transaction.setName("transaction");
      if (getRPCName() != null) {
        transaction.setAttribute("rpc_name", getRPCName());
      }
      if (getRPCUser() != null) {
        transaction.setAttribute("rpc_usr", getRPCUser());
      }
      if (getRPCPassword() != null) {
        transaction.setAttribute("rpc_pwd", getRPCPassword());
      }
      transaction.setAttribute("expiration_interval", this.expiration + "");
      if ( myRequestId != null ) {
    	  transaction.setAttribute("requestid", myRequestId);
      }

      header.addChild(transaction);
//      if (myCallbackPointer!=null) {
        XMLElement callback = new CaseSensitiveXMLElement();
        callback.setName("callback");
//      header.addChild(callback);
        XMLElement obj = new CaseSensitiveXMLElement();
        obj.setName("object");
        transaction.addChild(callback);
        if (myCallbackPointer!=null) {
          obj.setAttribute("finished", "" + isFinished);
        }
        if (myCallbackName!=null) {
          obj.setAttribute("name",myCallbackName);
        }
        if (myCallbackPointer!=null) {
          obj.setAttribute("ref",myCallbackPointer);
        }
        if (myCallbackPointer!=null) {
          obj.setIntAttribute("perc_ready", percReady);
        }
        if (myInterrupt != null) {
          obj.setAttribute("interrupt", myInterrupt);
        }
        callback.addChild(obj);
//      }

        if (attributeMap!=null) {
            for (Iterator iter = attributeMap.keySet().iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                Object localValue = getAttribute(element);
                if ( localValue!=null) {
                    header.setAttribute(element, localValue);
                }
            }
        }
       return header;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }

  }



  public final void merge(HeaderImpl n) {
    setExpiration(n.getExpirationInterval());
    lazyMessageList.putAll(n.getLazyMessageMap());
    setRPCUser(n.getRPCUser());
    setService(n.getRPCName());
    setRPCPassword(n.getRPCPassword());
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

public Object getRef() {
    return toXml(null);
}

public final void writeComponent(Writer w) throws IOException {
    toXml().write(w);
}
}
