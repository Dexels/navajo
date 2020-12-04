/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.cobra.impl;

import org.lobobrowser.html.gui.HtmlPanel;

public class TipiHtmlPanel extends HtmlPanel {

	private static final long serialVersionUID = 3001386950352936792L;
	private NavajoHtmlRendererContext renderingContext = null;
	
	public NavajoHtmlRendererContext getRenderingContext() {
		return renderingContext;
	}

	public void setRenderingContext(NavajoHtmlRendererContext renderingContext) {
		this.renderingContext = renderingContext;
	}

	@Override
	public String getSelectionText() {
		return super.getSelectionText();
	}

	
	public void setHtml(String text) {
		this.setHtml(text, "file://", getRenderingContext());
	}

}
