/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*******************************************************************************
 * Copyright (c) 2008, Original authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR <angelo.zerr@gmail.com>
 *******************************************************************************/
package org.akrogen.tkui.css.core.exceptions;

import java.util.ListResourceBundle;

/**
 * DOM Exception resource.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class ExceptionResource extends ListResourceBundle {

    public Object[][] getContents() {
        return contents;
    }

    static final Object[][] contents = {
        {"s0", "Syntax error"},
        {"s1", "Array out of bounds error"},
        {"s2", "This style sheet is read only"},
        {"s3", "The text does not represent an unknown rule"},
        {"s4", "The text does not represent a style rule"},
        {"s5", "The text does not represent a charset rule"},
        {"s6", "The text does not represent an import rule"},
        {"s7", "The text does not represent a media rule"},
        {"s8", "The text does not represent a font face rule"},
        {"s9", "The text does not represent a page rule"},
        {"s10", "This isn't a Float type"},
        {"s11", "This isn't a String type"},
        {"s12", "This isn't a Counter type"},
        {"s13", "This isn't a Rect type"},
        {"s14", "This isn't an RGBColor type"},
        {"s15", "A charset rule must be the first rule"},
        {"s16", "A charset rule already exists"},
        {"s17", "An import rule must preceed all other rules"},
        {"s18", "The specified type was not found"}
    };
}
