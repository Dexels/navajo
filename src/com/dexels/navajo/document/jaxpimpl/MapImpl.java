package com.dexels.navajo.document.jaxpimpl;

import com.dexels.navajo.document.MapTag;
import com.dexels.navajo.document.*;
import org.w3c.dom.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class MapImpl
    implements MapTag {
  public Element ref;
  private Navajo myRootDoc = null;

  public MapImpl(Element e) {
    this.ref = e;
  }

  public static final MapTag createObjectMap(Navajo tb, String object,
                                          String condition) throws
      NavajoException {

    MapTag p = null;

    Document d = (Document) tb.getMessageBuffer();

    Element n = (Element) d.createElement(MapTag.MAP_DEFINITION);

    p = new MapImpl(n);
    if (condition != null)
      p.setCondition(condition);
    p.setObject(object);

    return p;
  }

  public static final MapTag createRefMap(Navajo tb, String ref, String condition,
                                       String filter) throws NavajoException {

    MapTag p = null;

    Document d = (Document) tb.getMessageBuffer();

    Element n = (Element) d.createElement(MapTag.MAP_DEFINITION);

    p = new MapImpl(n);
    p.setCondition(condition);
    p.setRefAttribute(ref);
    p.setFilter(filter);

    return p;
  }

  public void addField(FieldTag e) {
   ref.appendChild((Node) e.getRef());
 }

 public void addParam(ParamTag e) {
   ref.appendChild( (Node) e.getRef());
}

  public void addMessage(Message m) {
    ref.appendChild((Node) m.getRef());
  }

  public String getObject() {
    return ref.getAttribute(MapTag.MAP_OBJECT);
  }

  public void setObject(String s) {
    ref.setAttribute(MapTag.MAP_OBJECT, s);
  }

  public String getCondition() {
    return ref.getAttribute(MapTag.MAP_CONDITION);
  }

  public void setCondition(String s) {
    ref.setAttribute(MapTag.MAP_CONDITION, s);
  }

  public String getRefAttribute() {
    return ref.getAttribute(MapTag.MAP_REF);
  }

  public void setRefAttribute(String s) {
    ref.setAttribute(MapTag.MAP_REF, s);
  }

  public String getFilter() {
    return ref.getAttribute(MapTag.MAP_FILTER);
  }

  public void setFilter(String s) {
    ref.setAttribute(MapTag.MAP_FILTER, s);
  }

  public Object getRef() {
    return this.ref;
  }

  public final int compareTo(Object p) {
    return -1;
  }

}