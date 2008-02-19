package com.dexels.navajo.tipi.components.swingimpl.parsers;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class CookieParser extends TipiTypeParser {
    public Object parse(TipiComponent source, String expression, TipiEvent event) {
        return parseCookie(expression);
    }
//    private Object parseBorder(String s) {
//        return parseBorder(s);
//    }

    public Object parseCookie(String s) {
    	  if(s==null) {
            return null;
        }
//        Command c = new Command(){};
//        ContainerContext containerContext = (ContainerContext) ((EchoTipiContext)myContext).getInstance().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
//        System.err.println("Context....");
//        if(containerContext==null) {
//        	System.err.println("No containerContext!");
//        }
//        Cookie[] cc = containerContext.getCookies();
//        System.err.println("Cookies....");
//        System.err.println("# of cookies: "+cc.length);
//        for (int i = 0; i < cc.length; i++) {
//        	System.err.println("Cookie # "+i+" has name: "+cc[i].getName()+" and value: "+cc[i].getValue());
//			if(cc[i].getName().equals(s)) {
//				return cc[i].getValue();
//			}
//		}
        return null;
    }

}
