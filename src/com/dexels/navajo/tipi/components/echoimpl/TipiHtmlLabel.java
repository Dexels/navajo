package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.functions.StripBody;

import echopointng.TemplatePanel;
import echopointng.template.StringTemplateDataSource;

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
	private static final long serialVersionUID = -8653909694723257928L;
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
//				text = sb.tidyString(text);
				System.err.println("WARNING Tidy disabled!");
				text = sb.bodyStrip(text);
				
				
				setText(text);
				
//	        	System.err.println("HTMLLABEL TEXT:\n\n"+text+"\n\n");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
        }
        super.setComponentValue(name, object);
    }


	private void setText(String bare) {
		String emb = "<div xmlns='http://www.w3.org/1999/xhtml'>" + bare + "</div>";
		System.err.println("Result: " + emb);
		StringTemplateDataSource stds = new StringTemplateDataSource(emb);
		templatePanel.setTemplateDataSource(stds);
		System.err.println("Total result: "+emb);
	}

}
