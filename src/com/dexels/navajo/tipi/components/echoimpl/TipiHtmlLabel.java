package com.dexels.navajo.tipi.components.echoimpl;

import java.net.*;

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
//		DirectHtml directHtml = new DirectHtml(markup);

		templatePanel = new TemplatePanel();
		return templatePanel;
    }

    
    protected void setComponentValue(String name, Object object) {
        if ("text".equals(name)) {
        	String bare = ""+object;
        	String emb = "<div xmlns='http://www.w3.org/1999/xhtml'>"+bare+"</div>";
        	System.err.println("Result: "+emb);
        	StringTemplateDataSource stds = new StringTemplateDataSource(emb);
    		templatePanel.setTemplateDataSource(stds);
    		//stds.setContentType("text/html");
            
        }
        super.setComponentValue(name, object);
    }

}
