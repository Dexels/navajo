/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl.parsers;


import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiTypeParser;
import com.dexels.navajo.tipi.internal.TipiEvent;


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
	private static final long serialVersionUID = 5256119276821274061L;

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
        return parseCookie(expression,source);
    }
//    private Object parseBorder(String s) {
//        return parseBorder(s);
//    }

    public Object parseCookie(String s,TipiComponent source) {
    	 if(s==null) {
            return null;
        }
    	 return source.getContext().getCookie(s);
//        Command c = new Command(){};
//        ContainerContext containerContext = (ContainerContext) ((EchoTipiContext)myContext).getInstance().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
//        if(containerContext==null) {
//        	logger.info("No containerContext!");
//        }
//        Cookie[] cc = containerContext.getCookies();
//     
//        for (int i = 0; i < cc.length; i++) {
//    		if(cc[i].getName().equals(s)) {
//				return cc[i].getValue();
//			}
//		}
//        return null;
    }

}
