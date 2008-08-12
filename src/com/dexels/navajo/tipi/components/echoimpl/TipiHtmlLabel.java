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

public class TipiHtmlLabel extends TipiEchoComponentImpl {
	private TemplatePanel templatePanel = null;

	public TipiHtmlLabel() {
	}

	public Object createContainer() {
		// DirectHtml directHtml = new DirectHtml(markup);

		templatePanel = new TemplatePanel();
		return templatePanel;
	}

	protected void setComponentValue(String name, Object object) {
        if ("text".equals(name)) {
        	String bare = ""+object;
        	setText(bare);
        }
        if("binary".equals(name)) {
        	Binary b = (Binary)object;
        	String text;
//			try {
				text = new String(b.getData());
				StripBody sb = new StripBody();
				
				
				text = sb.bodyStrip(text);
				text = sb.tidyString(text);
				text = sb.bodyStrip(text);
				
				
				setText(text);
				
//	        	System.err.println("HTMLLABEL TEXT:\n\n"+text+"\n\n");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
        }
        super.setComponentValue(name, object);
    }

	private String stripBody(String s) {
		int startIndex = s.indexOf("<body");
		if (startIndex != -1) {
			startIndex = s.indexOf(">", startIndex + ("<body".length()));
		}
		s = s.substring(startIndex + 1);
		int endIndex = s.indexOf("</body>");
		s = s.substring(0, endIndex);
		System.err.println("Result: " + s);

		// return "<h3>aap</h3><br/> bla bla bla <br/>";
		return s;
	}

	private void setText(String bare) {
		String emb = "<div xmlns='http://www.w3.org/1999/xhtml'>" + bare + "</div>";
		System.err.println("Result: " + emb);
		StringTemplateDataSource stds = new StringTemplateDataSource(emb);
		templatePanel.setTemplateDataSource(stds);
		System.err.println("Total result: "+emb);
	}

}
