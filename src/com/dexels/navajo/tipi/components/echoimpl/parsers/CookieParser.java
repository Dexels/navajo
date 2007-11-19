package com.dexels.navajo.tipi.components.echoimpl.parsers;

import java.util.StringTokenizer;

import javax.servlet.http.Cookie;


import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Command;
import nextapp.echo2.webcontainer.ContainerContext;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiTypeParser;
import com.dexels.navajo.tipi.components.echoimpl.EchoTipiContext;
import com.dexels.navajo.tipi.internal.TipiEvent;

import echopointng.able.Expandable;

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
        Command c = new Command(){};
        ContainerContext containerContext = (ContainerContext) ((EchoTipiContext)myContext).getInstance().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
        if(containerContext==null) {
        	System.err.println("No containerContext!");
        }
        Cookie[] cc = containerContext.getCookies();
     
        for (int i = 0; i < cc.length; i++) {
    		if(cc[i].getName().equals(s)) {
				return cc[i].getValue();
			}
		}
        return null;
    }

}
