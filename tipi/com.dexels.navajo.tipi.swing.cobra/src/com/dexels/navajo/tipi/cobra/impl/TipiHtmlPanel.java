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
