/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Property.java
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

import java.io.Serializable;
import org.w3c.dom.css.CSSValue;

/** 
 *
 * @author  David Schweinsberg
 * @version $Release$
 */
public class Property implements Serializable {

    private String _name;
    private CSSValue _value;
    private boolean _important;

    /** Creates new Property */
    public Property(String name, CSSValue value, boolean important) {
        _name = name;
        _value = value;
        _important = important;
    }

    public String getName() {
        return _name;
    }

    public CSSValue getValue() {
        return _value;
    }

    public boolean isImportant() {
        return _important;
    }

    public void setValue(CSSValue value) {
        _value = value;
    }
    
    public void setImportant(boolean important) {
        _important = important;
    }
    
    public String toString() {
        return _name + ": "
            + _value.toString()
            + (_important ? " !important" : "");
    }
}