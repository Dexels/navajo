package com.dexels.navajo.document.base;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;

import com.dexels.navajo.document.ExpressionTag;
import com.dexels.navajo.document.FieldTag;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.MapTag;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Method;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.ParamTag;
import com.dexels.navajo.document.Point;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.json.JSONParser;
import com.dexels.navajo.document.saximpl.SaxHandler;
import com.dexels.navajo.document.saximpl.qdxml.QDParser;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class BaseNavajoFactoryImpl extends NavajoFactory implements Serializable {
	//  private final SaxHandler saxHandler;

	private static final long serialVersionUID = -4927821415020342916L;

	public BaseNavajoFactoryImpl() {
		//    saxHandler = new SaxHandler();
	}

	public Message create(Navajo n, String name) {
		return new BaseMessageImpl(n,name);
	}
	//

	public  Property createProperty(Navajo n,String name, String type, String value, int i, String desc, String direction) {
		BasePropertyImpl pi = new BasePropertyImpl(n,name,type,value,i,desc,direction);
		return pi;
	}


	public  Property createProperty(Navajo n,String name, String type, String value, int i, String desc, String direction, String subtype) {
		BasePropertyImpl pi = new BasePropertyImpl(n,name,type,value,i,desc,direction, subtype);
		return pi;
	}

	public  Property createProperty(Navajo n, String name, String cardinality, String desc, String direction) {
		return new BasePropertyImpl(n,name,cardinality,desc,direction);
	}

	public  Property createProperty(Navajo n, String name) {
		return new BasePropertyImpl(n,name);
	}

	public  Message createMessage(Navajo n, String name) {
		return new BaseMessageImpl(n,name);
	}

	public  Message createArrayMessage(Navajo n, String name) {
		Message m = new BaseMessageImpl(n,name);
		m.setType(Message.MSG_TYPE_ARRAY);
		return m;
	}

	public  Message createArrayElementMessage(Navajo n, String name) {
		Message m = new BaseMessageImpl(n,name);
		m.setType(Message.MSG_TYPE_ARRAY_ELEMENT);
		return m;
	}
	//
	//  public  LazyMessage createLazyMessage(Navajo n, String msgName) {
	//    return ((LazyMessage)(new LazyMessageImpl(n,msgName)));
	//  }

	public  Selection createSelection(BaseNavajoImpl n, String name, String value, boolean isSelected) {
		return new BaseSelectionImpl(n,name, value, isSelected);
	}

	public  Selection createSelection(BaseNavajoImpl n, String name, String value, int isSelected) {
		return new BaseSelectionImpl(n,name, value, ( isSelected > 0 ) );
	}
	public  Selection createSelection(BaseNavajoImpl n) {
		return new BaseSelectionImpl(n);
	}

	public  Header createHeader(BaseNavajoImpl n, String username, String password, String service) {
		return new BaseHeaderImpl(n, username,password, service);
	}

	public  Header createHeader(BaseNavajoImpl n) {
		return new BaseHeaderImpl(n);
	}
	public Point createPoint(Property p) throws com.dexels.navajo.document.NavajoException {
		throw new java.lang.UnsupportedOperationException("Method createPoint() not yet implemented.");
	}


	public Header createHeader(Navajo n, String rpcName, String rpcUser, String rpcPassword, long expiration_interval) {
		BaseHeaderImpl hi = new BaseHeaderImpl(n,rpcUser,rpcPassword,rpcName);
		hi.setExpiration(expiration_interval);
		return hi;
		//    throw new java.lang.UnsupportedOperationException("Method createHeader() not yet implemented.");
	}

	public NavajoException createNavajoException(Throwable e) {
		return new NavajoExceptionImpl(e);
	}

	public Selection createDummySelection() {
		return new BaseSelectionImpl(null,Selection.DUMMY_SELECTION,Selection.DUMMY_ELEMENT,true);
	}

	public Message createMessage(Navajo tb, String name, String type) {
		BaseMessageImpl mi =  new BaseMessageImpl(tb,name);
		mi.setType(type);
		return mi;
	}
	public Navajo createNavajo() {
		return new BaseNavajoImpl(this);
	}
	public Selection createSelection(Navajo tb, String name, String value, boolean selected) {
		BaseSelectionImpl si = new BaseSelectionImpl(tb,name,value,selected);
		return si;
	}
	public Selection createSelection(Navajo tb, String name, String value, int selected) {
		BaseSelectionImpl si = new BaseSelectionImpl(tb,name,value, ( selected > 0 ) );
		return si;
	}

	public Selection createSelection(Navajo tb, String name, String value, Object selected) {

		BaseSelectionImpl si = null;

		if ( selected == null ) {
			si = new BaseSelectionImpl(tb,name,value, false );
		}
		else if ( selected instanceof Boolean ) {
			si = new BaseSelectionImpl(tb,name,value, (Boolean) selected );
		} else {
			si = new BaseSelectionImpl(tb,name,value, Integer.parseInt(selected+"") > 0 );
		}

		return si;
	}

	public Method createMethod(Navajo tb, String name, String server) {
		BaseMethodImpl mi = new BaseMethodImpl(tb,name);
		mi.setServer(server);
		return mi;
	}
	public NavajoException createNavajoException(String message) {
		return new NavajoExceptionImpl(message);
		//    throw new java.lang.UnsupportedOperationException("Method createNavajoException() not yet implemented.");
	}
	public NavajoException createNavajoException(String message, Throwable t) {
		return new NavajoExceptionImpl(message,t);
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

	public Navajo createNavajo(InputStream stream) {
		try {
			SaxHandler sax = new SaxHandler();
			InputStreamReader isr = new InputStreamReader(stream,"UTF-8");

			QDParser.parse(sax,isr);
			return  sax.getNavajo();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}  
	}

	public Navajo createNavajo(Reader r) {
		try {
			SaxHandler sax = new SaxHandler();
			QDParser.parse(sax,r);
			return  sax.getNavajo();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}  
	}

	public Navajo createNavajoJSON(Reader r){
		try{
			JSONParser jsoParser = new JSONParser();
			jsoParser.parse(r);
			return jsoParser.getNavajo();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public Navajo createNavajo(Object representation) {
		// TODO Auto-generated method stub
		return null;
	}

	public Message createMessage(Object representation) {
		// TODO Auto-generated method stub
		return null;
	}

	public Property createProperty(Object representation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Operation createOperation(Navajo n, String method, String service,
			String entityName, Message extra) {
		BaseOperationImpl oi = new BaseOperationImpl(n);
		oi.setMethod(method);
		oi.setService(service);
		oi.setEntityName(entityName);
		oi.setExtraMessage(extra);
		return oi;
	}

}
