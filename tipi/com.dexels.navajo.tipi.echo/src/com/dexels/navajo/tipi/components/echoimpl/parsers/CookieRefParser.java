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
public class CookieRefParser extends TipiTypeParser {

	private static final long serialVersionUID = -366656459338848632L;

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
        return new CookieRef(expression,source.getContext());
   }

    


}
