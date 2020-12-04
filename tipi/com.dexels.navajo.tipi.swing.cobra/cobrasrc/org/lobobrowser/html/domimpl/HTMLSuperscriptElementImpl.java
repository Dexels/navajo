/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.style.FontStyleRenderState;
import org.lobobrowser.html.style.RenderState;

/**
 * Element used for SUB
 */

public class HTMLSuperscriptElementImpl extends HTMLAbstractUIElement {
    private int superscript;

    public HTMLSuperscriptElementImpl(String name, int superscript) {
        super(name);
        this.superscript = superscript;
    }

    protected RenderState createRenderState(RenderState prevRenderState) {
        prevRenderState = FontStyleRenderState.createSuperscriptFontStyleRenderState(prevRenderState, new Integer(this.superscript));
        return super.createRenderState(prevRenderState);
    }
}
