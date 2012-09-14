package com.dexels.navajo.tipi.components.echoimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.functions.StripBody;

import echopointng.TemplatePanel;
import echopointng.template.StringTemplateDataSource;


public class TipiHtmlLabel extends TipiEchoComponentImpl {
	private static final long serialVersionUID = -8653909694723257928L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiHtmlLabel.class);
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
				logger.info("WARNING Tidy disabled!");
				text = sb.bodyStrip(text);
				
				
				setText(text);
				
//	        	logger.info("HTMLLABEL TEXT:\n\n"+text+"\n\n");
//			} catch (UnsupportedEncodingException e) {
//				logger.error("Error: ",e);
//			}
        }
        super.setComponentValue(name, object);
    }


	private void setText(String bare) {
		String emb = "<div xmlns='http://www.w3.org/1999/xhtml'>" + bare + "</div>";
		logger.info("Result: " + emb);
		StringTemplateDataSource stds = new StringTemplateDataSource(emb);
		templatePanel.setTemplateDataSource(stds);
		logger.info("Total result: "+emb);
	}

}
