package com.dexels.navajo.document.jaxpimpl;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

import java.io.*;

import org.w3c.dom.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.jaxpimpl.xml.*;

public final class NavajoFactoryImpl extends NavajoFactory {

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
       return new NavajoImpl(docIn, this);
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
  }

  public Navajo createNavajo(Reader r) {
	  throw new UnsupportedOperationException("Did not implement this function. Sorry.");
	}
  
  public Navajo createNavajo(Object representation) {
    return new NavajoImpl((Document) representation, this);
  }

  public Navajo createNavajo() {
    return new NavajoImpl(this);
  }

  public Navajo createNavaScript(java.io.InputStream stream) {
     try {
      Document docIn = XMLDocumentUtils.createDocument(stream, false);
      docIn.getDocumentElement().normalize();
      return new NavajoImpl(docIn, this);
     } catch (Exception e) {
       e.printStackTrace();
       return null;
     } finally {
      try {
        if (stream != null) {
          stream.close();
        }
      }
      catch (IOException ex) {
      }
     }
 }

 public Navajo createNavaScript(Object representation) {
   return new NavajoImpl((Document) representation, this);
 }

 public Navajo createNavaScript() {
   return new NavajoImpl(Navajo.SCRIPT_BODY_DEFINITION, this);
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

  public Message createMessage(Navajo n, String messageName, String type) {
      return MessageImpl.create(n, messageName, type);
  }

  public  ExpressionTag createExpression(Navajo tb, String condition, String value) throws NavajoException {
    return ExpressionImpl.create(tb, value, condition);
  }

  public  FieldTag createField(Navajo tb, String condition, String name) throws NavajoException {
    return FieldImpl.create(tb, name, condition);
  }

  public  ParamTag createParam(Navajo tb, String condition, String name) throws NavajoException {
    return ParamImpl.create(tb, name, condition);
  }


  public  MapTag createMapObject(Navajo tb, String object, String condition) throws NavajoException {
     return MapImpl.createObjectMap(tb, object, condition);
   }

   public  MapTag createMapRef(Navajo tb, String ref, String condition, String filter) throws NavajoException {
      return MapImpl.createRefMap(tb, ref, condition, filter);
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

  public Property createProperty(Navajo tb, String name, String type, String value, int length,
            String description, String direction, String subType) throws NavajoException {
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
  public LazyMessagePath createLazyMessagePath(Navajo tb, String path, int startIndex, int endIndex, int total) {
    throw new java.lang.UnsupportedOperationException("Method createLazyMessagePath() not yet implemented.");
  }
  public LazyMessage createLazyMessage(Navajo tb, String name, int windowSize) {
    throw new java.lang.UnsupportedOperationException("Method createLazyMessage() not yet implemented.");
}

}
