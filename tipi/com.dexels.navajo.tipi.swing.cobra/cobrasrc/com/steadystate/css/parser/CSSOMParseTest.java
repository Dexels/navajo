/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * CSSOMParseTest.java
 *
 * Steady State CSS2 Parser
 *
 * Copyright (C) 1999, 2002 Steady State Software Ltd.  All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * To contact the authors of the library, write to Steady State Software Ltd.,
 * 49 Littleworth, Wing, Buckinghamshire, LU7 0JX, England
 *
 * http://www.steadystate.com/css/
 * mailto:css@steadystate.co.uk
 *
 * $Id$
 */
 
package com.steadystate.css.parser;

import java.io.*;
import org.w3c.css.sac.*;
import org.w3c.dom.css.*;

/** 
 *
 * @author  David Schweinsberg
 * @version $Release$
 */
public class CSSOMParseTest extends Object {

    /** Creates new CSSOMParseTest */
    public CSSOMParseTest() {
        try {
            Reader r = new FileReader("stylesheets/page_test.css");
//            Reader r = new StringReader("FOO { color: rgb(1,2,3) }");
            InputSource is = new InputSource(r);
            CSSOMParser parser = new CSSOMParser();
            CSSStyleSheet ss = parser.parseStyleSheet(is);
            logger.info(ss.toString());
            
            CSSRuleList rl = ss.getCssRules();
            for (int i = 0; i < rl.getLength(); i++) {
                CSSRule rule = rl.item(i);
                if (rule.getType() == CSSRule.STYLE_RULE) {
                    CSSStyleRule sr = (CSSStyleRule) rule;
                    CSSStyleDeclaration style = sr.getStyle();
                    for (int j = 0; j < style.getLength(); j++) {
                        CSSValue value = style.getPropertyCSSValue(style.item(j));
                        if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
                            CSSPrimitiveValue pv = (CSSPrimitiveValue) value;
                            logger.info(">> " + pv.toString());
                            if (pv.getPrimitiveType() == CSSPrimitiveValue.CSS_COUNTER) {
                                logger.info("CSS_COUNTER(" + pv.toString() + ")");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public static void main(String[] args) {
        new CSSOMParseTest();
    }
}
