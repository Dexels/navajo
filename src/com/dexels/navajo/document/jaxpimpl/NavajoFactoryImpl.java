package com.dexels.navajo.document.jaxpimpl;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import org.w3c.dom.*;

public class NavajoFactoryImpl extends NavajoFactory {

  public NavajoException createNavajoException(String message) {
    return new NavajoExceptionImpl(message);
  }

  public NavajoException createNavajoException(Exception e) {
    return new NavajoExceptionImpl(e);
  }

  public Navajo createNavajo(java.io.InputStream stream) {
      try {
       Document docIn = XMLDocumentUtils.createDocument(stream, false);
       docIn.getDocumentElement().normalize();
       stream.close();
       return new NavajoImpl(docIn);
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
  }

  public Navajo createNavajo(Object representation) {
    return new NavajoImpl((Document) representation);
  }

  public Navajo createNavajo() {
    return new NavajoImpl();
  }

  public Header createHeader(Navajo n, String rpcName, String rpcUser, String rpcPassword, long expiration_interval) {
      Element e = NavajoImpl.createHeader((Document) n.getMessageBuffer(), rpcName, rpcUser, rpcPassword, expiration_interval, null);
      return new HeaderImpl(e);
  }

  public Message createMessage(Object representation) {
      return new MessageImpl((Element) representation);
  }

  public Message createMessage(Navajo n, String messageName) {
      return MessageImpl.create(n, messageName);
  }

  public Message createAntiMessage(Navajo n, String messageName) {
      return AntiMessage.create(n, messageName);
  }

  public Message createMessage(Navajo n, String messageName, String type) {
      return MessageImpl.create(n, messageName, type);
  }

  public Property createProperty(Object representation) {
    return new PropertyImpl((Element) representation);
  }

  public Property createProperty(Navajo tb, String name, String cardinality, String description, String direction) throws NavajoException {
      return PropertyImpl.create(tb, name, cardinality, description, direction);
  }

  public Property createProperty(Navajo tb, String name, String type, String value, int length,
            String description, String direction) throws NavajoException {
      return PropertyImpl.create(tb, name, type, value, length, description, direction);
  }

  public Selection createSelection(Navajo tb, String name, String value, boolean selected) {
      return SelectionImpl.create(tb, name, value, selected);
  }

  public Selection createDummySelection() {
      return SelectionImpl.createDummy();
  }

  public Method createMethod(Navajo tb, String name, String server) {
      return MethodImpl.create(tb, name, server);
  }

   public Point createPoint(Property p) throws NavajoException {
      return new PointImpl(p);
   }
}
