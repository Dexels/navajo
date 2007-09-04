package com.dexels.navajo.tipi.components.core.parsers;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;

abstract class BaseTipiParser extends TipiTypeParser {
	protected TipiDataComponent getTipiByPath(TipiComponent source, String path) {
		return (TipiDataComponent) getTipiComponent(source, path);
	}

	protected TipiComponent getTipiComponent(TipiComponent source, String totalpath) {
		String path = getComponentPart(totalpath);
		if (path.startsWith(".")) { // Relative path
			return source.getTipiComponentByPath(path);
		} else { // Absolute path
			return myContext.getTipiComponentByPath(path);
		}
	}

	protected Property getPropertyByPath(TipiComponent source, String path) {
		StringTokenizer counter = new StringTokenizer(path,":");
		int tokencount = counter.countTokens();
		if(tokencount==2) {
			String navajo = counter.nextToken();
			String propertyPath = counter.nextToken();
			Navajo n = myContext.getNavajo(navajo);
			if(n!=null) {
				return n.getProperty(propertyPath);
			} else {
				return null;
			}
		}
					StringTokenizer st = new StringTokenizer(path, ":");
		String partOne = st.nextToken();
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
			// msg.write(System.err);
			return msg.getProperty(partThree);
		}
	}

	protected Message getMessageByPath(TipiComponent source, String path) {
		StringTokenizer counter = new StringTokenizer(path,":");
		int tokencount = counter.countTokens();
		if(tokencount==2) {
			System.err.println("jahoorrr.r..");
			String navajo = counter.nextToken();
			String messagePath = counter.nextToken();
			Navajo n = myContext.getNavajo(navajo);
			System.err.println(">>> "+myContext.getNavajoNames());
			if(n!=null) {
				System.err.println("Lookin for message: "+messagePath);
				return n.getMessage(messagePath);
			} else {
				System.err.println("No navajo..");
				return null;
			}
			
		}
		StringTokenizer st = new StringTokenizer(path, ":");
		String partOne = st.nextToken();
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
