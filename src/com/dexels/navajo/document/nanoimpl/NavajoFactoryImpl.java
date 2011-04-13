package com.dexels.navajo.document.nanoimpl;
import java.io.*;
import java.lang.reflect.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.Method;
import com.dexels.navajo.document.base.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 * @deprecated
 */

@Deprecated
public final class NavajoFactoryImpl extends NavajoFactory {
	public NavajoFactoryImpl() {
	}

	public Message create(Navajo n, String name) {
		return new MessageImpl(n,name);
	}
	//

	public  Property createProperty(Navajo n,String name, String type, String value, int i, String desc, String direction) {
		PropertyImpl pi = new PropertyImpl(n,name,type,value,i,desc,direction);
		return pi;
	}


	public  Property createProperty(Navajo n,String name, String type, String value, int i, String desc, String direction, String subtype) {
		PropertyImpl pi = new PropertyImpl(n,name,type,value,i,desc,direction, subtype);
		return pi;
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
	//  public  LazyMessage createLazyMessage(Navajo n, String msgName) {
	//    return ((LazyMessage)(new LazyMessageImpl(n,msgName)));
	//  }

	public  Selection createSelection(NavajoImpl n, String name, String value, boolean isSelected) {
		return new SelectionImpl(n,name, value, isSelected);
	}
	public  Selection createSelection(NavajoImpl n, String name, String value, int isSelected) {
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
		HeaderImpl hi = new HeaderImpl(n,rpcUser,rpcPassword,rpcName );
		hi.setExpiration(expiration_interval);
		return hi;
		//    throw new java.lang.UnsupportedOperationException("Method createHeader() not yet implemented.");
	}

	public NavajoException createNavajoException(Throwable e) {
		return new NavajoExceptionImpl(e);
	}

	public NavajoException createNavajoException(String message, Throwable e) {
		return new NavajoExceptionImpl(message,e);
	}

	
	public Navajo createNavajo(InputStream stream) {
		NavajoImpl n = new NavajoImpl(this);
		XMLElement xe = new CaseSensitiveXMLElement();
		try {
			xe.parseFromReader(new InputStreamReader(stream, "UTF-8"));
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

	public Navajo createNavajo(Reader r) {
		NavajoImpl n = new NavajoImpl(this);
		XMLElement xe = new CaseSensitiveXMLElement();
		try {
			xe.parseFromReader(r);
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
		NavajoImpl n = new NavajoImpl(this);
		n.fromXml((XMLElement)representation);
		return n;
	}
	public Message createMessage(Navajo tb, String name, String type) {
		MessageImpl mi =  new MessageImpl(tb,name);
		mi.setType(type);
		return mi;
	}
	public Navajo createNavajo() {
		return new NavajoImpl(this);
	}
	public Selection createSelection(Navajo tb, String name, String value, boolean selected) {
		SelectionImpl si = new SelectionImpl(tb,name,value,selected);
		return si;
	}
	public Selection createSelection(Navajo tb, String name, String value, int selected) {
		SelectionImpl si = new SelectionImpl(tb,name,value,( selected > 0 ) );
		return si;
	}
	public Selection createSelection(Navajo tb, String name, String value, Object selected) {
		SelectionImpl si = null;

		if ( selected == null ) {
			si = new SelectionImpl(tb,name,value, false );
		}
		else if ( selected instanceof Boolean ) {
			si = new SelectionImpl(tb,name,value, (Boolean) selected );
		} else {
			si = new SelectionImpl(tb,name,value, Integer.parseInt(selected+"") > 0 );
		}

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
	public  Navajo createNavaScript(java.io.InputStream stream) {
		throw new java.lang.UnsupportedOperationException("Method createNavaScript() not yet implemented.");
	}

	public  Navajo createNavaScript(Object representation) {
		throw new java.lang.UnsupportedOperationException("Method createNavaScript() not yet implemented.");
	}

	public  Navajo createNavaScript() {
		throw new java.lang.UnsupportedOperationException("Method createNavaScript() not yet implemented.");
	}

	public ExpressionTag createExpression(Navajo tb, String condition, String value) throws
	NavajoException {
		throw new java.lang.UnsupportedOperationException(
		"Method createExpression() not yet implemented.");
	}

	public FieldTag createField(Navajo tb, String condition, String name) throws
	NavajoException {
		throw new java.lang.UnsupportedOperationException(
		"Method createExpression() not yet implemented.");
	}

	public MapTag createMapObject(Navajo tb, String object, String condition) throws
	NavajoException {
		throw new java.lang.UnsupportedOperationException(
		"Method createMapObject() not yet implemented.");
	}

	public MapTag createMapRef(Navajo tb, String ref, String condition,
			String filter) throws NavajoException {
		throw new java.lang.UnsupportedOperationException(
		"Method createMapRef() not yet implemented.");
	}

	public  ParamTag createParam(Navajo tb, String condition, String name) throws NavajoException {
		throw new java.lang.UnsupportedOperationException(
		"Method createParam() not yet implemented.");
	}

	public Navajo createNavajoJSON(Reader r){
		// First we determine Navajo -> JSON , see BaseNode
		return null;
	}
}
