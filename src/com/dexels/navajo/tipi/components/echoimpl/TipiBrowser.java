package com.dexels.navajo.tipi.components.echoimpl;

import java.io.*;
import java.net.*;

import org.w3c.tidy.*;

import com.dexels.navajo.document.types.*;
import com.dexels.navajo.functions.*;
import com.dexels.navajo.tipi.components.echoimpl.parsers.ColorParser;

import echopointng.*;
import echopointng.image.*;
import echopointng.template.*;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiBrowser extends TipiEchoComponentImpl {
	private TemplatePanel templatePanel = null;

	public TipiBrowser() {
	}

	public Object createContainer() {
		// DirectHtml directHtml = new DirectHtml(markup);

		templatePanel = new TemplatePanel();
		return templatePanel;
	}

	protected void setComponentValue(String name, Object object) {
        if ("url".equals(name)) {
        	String bare = ""+object;
        	setUrl(bare);
        }
        super.setComponentValue(name, object);
    }


	private void setUrl(String url) {
		String emb = "<iframe xmlns=\"http://www.w3.org/1999/xhtml\" height=\"100%\" width=\"100%\" frameborder=\"0\" src=\"" + url + "\"/>";

		System.err.println("Result: " + emb);
		StringTemplateDataSource stds = new StringTemplateDataSource(emb);
		templatePanel.setTemplateDataSource(stds);
		System.err.println("Total result: "+emb);
	}

}
