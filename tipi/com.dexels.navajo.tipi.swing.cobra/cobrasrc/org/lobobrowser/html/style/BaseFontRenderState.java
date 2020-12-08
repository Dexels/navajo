/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.lobobrowser.html.style;

public class BaseFontRenderState extends RenderStateDelegator {
	private final int fontBase;
	
	public BaseFontRenderState(final RenderState prevRenderState, int fontBase) {
		super(prevRenderState);
		this.fontBase = fontBase;
	}

	public int getFontBase() {
		return this.fontBase;
	}
}
