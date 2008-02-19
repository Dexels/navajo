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
public class CookieRefParser extends TipiTypeParser {
    public Object parse(TipiComponent source, String expression, TipiEvent event) {
    	return parseCookie(expression);
    }
//    private Object parseBorder(String s) {
//        return parseBorder(s);
//    }

    public Object parseCookie(String s) {
    	System.err.println("Parsing cookie: "+s);
    	if(s==null) {
            return null;
        }
//        Cookie cc = getCookie(s);
//        if(cc==null) {
//        	cc = createCookie(s);
//        }
//        return new CookieRef(cc);
    	return null;
    }
//    
//private Cookie createCookie(String s) {
//	Cookie cc = new Cookie(s, "");
//	cc.setPath("/");
//	cc.setMaxAge(60*60*24*365);
//
//	return cc;
//}
//private Cookie getCookie(String s) {
//	ContainerContext containerContext = (ContainerContext)ApplicationInstance.getActive().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
//	Cookie[] cc = containerContext.getCookies();
//	for (int i = 0; i < cc.length; i++) {
//		if(cc[i].getName().equals(s)) {
//			return cc[i];
//		}
//	}
//	return null;
//}

}
