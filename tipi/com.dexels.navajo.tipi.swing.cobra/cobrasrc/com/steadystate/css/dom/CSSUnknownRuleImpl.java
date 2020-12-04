/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * CSSUnknownRuleImpl.java
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

package com.steadystate.css.dom;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import org.w3c.dom.*;
import org.w3c.dom.css.*;

/*
 *
 * @author  David Schweinsberg
 * @version $Release$
 */
public class CSSUnknownRuleImpl implements CSSUnknownRule, Serializable {

    CSSStyleSheetImpl _parentStyleSheet = null;
    CSSRule _parentRule = null;
    String _text = null;

    public CSSUnknownRuleImpl(
            CSSStyleSheetImpl parentStyleSheet,
            CSSRule parentRule,
            String text) {
        _parentStyleSheet = parentStyleSheet;
        _parentRule = parentRule;
        _text = text;
    }

    public short getType() {
        return UNKNOWN_RULE;
    }

    public String getCssText() {
        return _text;
    }

    public void setCssText(String cssText) throws DOMException {
/*
        if( _parentStyleSheet != null && _parentStyleSheet.isReadOnly() )
        throw new DOMExceptionImpl(
        DOMException.NO_MODIFICATION_ALLOWED_ERR,
        DOMExceptionImpl.READ_ONLY_STYLE_SHEET );

        try
        {
            //
            // Parse the rule string and retrieve the rule
            //
            StringReader sr = new StringReader( cssText );
            CSS2Parser parser = new CSS2Parser( sr );
            ASTStyleSheetRuleSingle ssrs = parser.styleSheetRuleSingle();
            CSSRule r = (CSSRule) ssrs.jjtGetChild( 0 );

            //
            // The rule must be an unknown rule
            //
            if( r.getType() == CSSRule.UNKNOWN_RULE )
            {
                _text = ((ASTUnknownRule)r)._text;
                setChildren( ((SimpleNode)r).getChildren() );
            }
            else
            {
                throw new DOMExceptionImpl(
                DOMException.INVALID_MODIFICATION_ERR,
                DOMExceptionImpl.EXPECTING_UNKNOWN_RULE );
            }
        }
        catch( ParseException e )
        {
            throw new DOMExceptionImpl(
            DOMException.SYNTAX_ERR,
            DOMExceptionImpl.SYNTAX_ERROR,
            e.getMessage() );
        }
*/
    }

    public CSSStyleSheet getParentStyleSheet() {
        return _parentStyleSheet;
    }

    public CSSRule getParentRule() {
        return _parentRule;
    }
    
    public String toString() {
        return getCssText();
    }
}
