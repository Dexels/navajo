package com.dexels.navajo.document.nanoimpl;
import com.dexels.navajo.document.*;
import java.io.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NavajoFactoryImpl extends NavajoFactory {
  public NavajoFactoryImpl() {
  }

  public Message create(Navajo n, String name) {
    return new MessageImpl(n,name);
  }
//
  public  Property createProperty(Navajo n,String name, String type, String value, int i, String desc, String direction) {
    return new PropertyImpl(n,name,type,value,i,desc,direction);
  }

  public  Property createProperty(Navajo n, String name, String cardinality, String desc, String direction) {
    return new PropertyImpl(n,name,cardinality,desc,direction);
  }

  public  Property createProperty(Navajo n, String name) {
    return new PropertyImpl(n,name);
  }

  public  Message createMessage(Navajo n, String name) {
    return new MessageImpl(n,name);
  }

  public  Message createArrayMessage(Navajo n, String name) {
    Message m = new MessageImpl(n,name);
    m.setType(Message.MSG_TYPE_ARRAY);
    return m;
  }

  public  Message createArrayElementMessage(Navajo n, String name) {
    Message m = new MessageImpl(n,name);
    m.setType(Message.MSG_TYPE_ARRAY_ELEMENT);
    return m;
  }
//
  public  LazyMessage createLazyMessage(Navajo n, String msgName) {
    return ((LazyMessage)(new LazyMessageImpl(n,msgName)));
  }

  public  Selection createSelection(NavajoImpl n, String name, String value, boolean isSelected) {
    return new SelectionImpl(n,name, value, isSelected);
  }
  public  Selection createSelection(NavajoImpl n) {
    return new SelectionImpl(n);
  }

  public  Header createHeader(NavajoImpl n, String username, String password, String service) {
    return new HeaderImpl(n, username,password, service);
  }

  public  Header createHeader(NavajoImpl n) {
    return new HeaderImpl(n);
  }
  public Point createPoint(Property p) throws com.dexels.navajo.document.NavajoException {
    throw new java.lang.UnsupportedOperationException("Method createPoint() not yet implemented.");
  }
  public Message createMessage(Object representation) {
    MessageImpl mi = new MessageImpl(null,"");
    mi.fromXml((XMLElement)representation);
//     throw new java.lang.UnsupportedOperationException("Method createHeader() not yet implemented.");
    return mi;
  }
  public Header createHeader(Navajo n, String rpcName, String rpcUser, String rpcPassword, long expiration_interval) {
    HeaderImpl hi = new HeaderImpl(n,rpcUser,rpcPassword,rpcName);
    hi.setExpiration(expiration_interval);
    return hi;
//    throw new java.lang.UnsupportedOperationException("Method createHeader() not yet implemented.");
  }

  public NavajoException createNavajoException(Exception e) {
    return new NavajoExceptionImpl(e);
  }
  public Navajo createNavajo(InputStream stream) {
    NavajoImpl n = new NavajoImpl();
    XMLElement xe = new XMLElement();
    try {
      xe.parseFromReader(new InputStreamReader(stream));
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
    catch (XMLParseException ex) {
      ex.printStackTrace();
    }
    n.fromXml(xe);
    return n;
  }
  public Selection createDummySelection() {
    return new SelectionImpl(null,Selection.DUMMY_SELECTION,Selection.DUMMY_ELEMENT,true);
  }
  public Navajo createNavajo(Object representation) {
    NavajoImpl n = new NavajoImpl();
    n.fromXml((XMLElement)representation);
    return n;
  }
  public Message createMessage(Navajo tb, String name, String type) {
    MessageImpl mi =  new MessageImpl(tb,name);
    mi.setType(type);
    return mi;
  }
  public Navajo createNavajo() {
    return new NavajoImpl();
  }
  public Selection createSelection(Navajo tb, String name, String value, boolean selected) {
    SelectionImpl si = new SelectionImpl(tb,name,value,selected);
    return si;
  }
  public Method createMethod(Navajo tb, String name, String server) {
    MethodImpl mi = new MethodImpl(tb,name);
    mi.setServer(server);
    return mi;
  }
  public Property createProperty(Object representation) {
    PropertyImpl pi = new PropertyImpl(null,"");
    pi.fromXml((XMLElement)representation);
    return pi;
  }
  public NavajoException createNavajoException(String message) {
    return new NavajoExceptionImpl(message);
//    throw new java.lang.UnsupportedOperationException("Method createNavajoException() not yet implemented.");
  }

}