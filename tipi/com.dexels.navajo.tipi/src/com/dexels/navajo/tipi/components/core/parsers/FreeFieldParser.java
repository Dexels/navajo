/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.core.parsers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class FreeFieldParser extends BaseTipiParser {
    private static final long serialVersionUID = -25891613642904632L;
    private static final Logger logger = LoggerFactory.getLogger(FreeFieldParser.class);
    
    public final static String WEBSERVICE = "vla/sportlinkkernel/navajostore/propertydescriptions/ProcessSearchFreeFieldsDescriptions";
    

    @Override
    public Object parse(TipiComponent tc, String expression, TipiEvent event) {
        return tc.getContext().getFreeField(expression);
        
    }



}
