package com.dexels.navajo.tipi.components.core.parsers;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;

abstract class BaseTipiParser extends TipiTypeParser {
	private static final String TIPI_HOME_SYMBOL = "~";

	protected TipiDataComponent getTipiByPath(TipiComponent source, String path) {
		return (TipiDataComponent) getTipiComponent(source, path);
	}

	protected TipiComponent getTipiComponent(TipiComponent source, String totalpath) {
		String path = getComponentPart(totalpath);
		if(TIPI_HOME_SYMBOL.equals(path)) {
			return source.getHomeComponent();
		}
		if(path.startsWith(TIPI_HOME_SYMBOL+"/")) {
			source = source.getHomeComponent();
			String pathExpression = path.substring(2,path.length());
//			System.err.println("Home component: "+source.getPath()+" expression: "+pathExpression);
//			myContext.debugTipiComponentTree(source, 5);
			return source.getTipiComponentByPath(pathExpression);
		}
		if (path.startsWith(".")) { // Relative path
			return source.getTipiComponentByPath(path);
		} else { // Absolute path
			return myContext.getTipiComponentByPath(path);
		}
	}

	protected Object getAttributePropertyValueByPath(TipiComponent source, String path) throws TipiException {
		Property p = getAttributePropertyByPath(source, path);
		if(p!=null) {
			return p.getTypedValue();
		}
		throw new TipiException("Attributeproperty not found: "+path+" in component "+source.getPath());
	}
	protected Property getAttributePropertyByPath(TipiComponent source, String path) throws TipiException {
		StringTokenizer counter = new StringTokenizer(path,":");
		int tokencount = counter.countTokens();
		if(tokencount==2) {
			String tipiPath = counter.nextToken();
			TipiComponent myTipi = getTipiComponent(source, tipiPath);
			String value = counter.nextToken();
			Property attrProp = myTipi.getAttributeProperty(value);
			return attrProp;
		}
		throw new TipiException("Illegal attribute path property: "+path);
	}
	
	protected Property getPropertyByPath(TipiComponent source, String path) {
		StringTokenizer counter = new StringTokenizer(path,":");
		int tokencount = counter.countTokens();
		if(tokencount<=2) {
			Navajo n = null;
			if (tokencount==1) {
				n = source.getNearestNavajo();
			} else {
				String navajo = counter.nextToken();
				n = myContext.getNavajo(navajo);
			}
			String propertyPath = counter.nextToken();
			if(n!=null) {
				return n.getProperty(propertyPath);
			} else {
				myContext.showInternalError("No navajo found, while looking for path: "+path+" Available: "+myContext.getNavajoNames(),new Exception());
				return null;
			}
		}
					StringTokenizer st = new StringTokenizer(path, ":");
		// skip one:
		st.nextToken();
		String partTwo = st.nextToken();
		String partThree = st.nextToken();
		TipiComponent myTipi = getTipiComponent(source, path);
		if (partTwo.equals(".")) {
			return myTipi.getNavajo().getProperty(partThree);
		} else {
			
			Message msg = (Message) myTipi.getValue(partTwo);
			if (msg == null) {
				return null;
			}
			return msg.getProperty(partThree);
		}
	}

	protected Message getMessageByPath(TipiComponent source, String path) {
		StringTokenizer counter = new StringTokenizer(path,":");
		int tokencount = counter.countTokens();
		if(tokencount==2) {
			String navajo = counter.nextToken();
			String messagePath = counter.nextToken();
			Navajo n = myContext.getNavajo(navajo);
			if(n!=null) {
				return n.getMessage(messagePath);
			} else {
				System.err.println("No navajo..");
				return null;
			}
			
		}
		StringTokenizer st = new StringTokenizer(path, ":");
		// skip one
		st.nextToken();
		String partTwo = st.nextToken();
		TipiComponent myTipi = getTipiComponent(source, path);
		if (partTwo.equals(".")) {
			String partThree = st.nextToken();
			return myTipi.getNavajo().getMessage(partThree);
		} else {
			Message msg = (Message) myTipi.getValue(partTwo);
			if (msg == null) {
				return null;
			}
			// msg.write(System.err);
			if (st.hasMoreTokens()) {
				String partThree = st.nextToken();
				return msg.getMessage(partThree);
			} else {
				return msg;
			}
		}
	}

	protected String getComponentPart(String s) {
		return s.split(":")[0];
	}
}
