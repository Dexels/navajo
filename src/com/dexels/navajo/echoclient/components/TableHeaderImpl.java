/*
 * Created on Jul 11, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.echoclient.components;

import echopointng.*;
import nextapp.echo2.app.*;

public class TableHeaderImpl extends ButtonImpl {

    public TableHeaderImpl() {
        super();
        setup();
    }



    public TableHeaderImpl(String arg0) {
        super(arg0);
        setup();
    }

    public TableHeaderImpl(ImageReference arg0) {
        super(arg0);
        setup();
    }

    public TableHeaderImpl(String arg0, ImageReference arg1) {
        super(arg0, arg1);
        setup();
    }
    private void setup() {
//    	setStyleName("Default");
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(this.getClass(), "TableHeader");
		setLineWrap(false);
//		System.err.println("Background: "+PROPERTY_BACKGROUND+" value: "+ ss.getProperty(this.PROPERTY_BACKGROUND));
		setStyle(ss);
        
//		setTextAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
//        setAlignment(new Alignment(Alignment.CENTER,Alignment.DEFAULT));
//        setBorder(new Border(1,new Color(0x88,0x88,0x88),Border.STYLE_SOLID));
//        setRolloverBackground(new Color(0xff,0xff,0xff));
//        setRolloverForeground(new Color(0,0,0));
//        setRolloverBorder(new Border(1,new Color(0x88,0x88,0x88),Border.STYLE_SOLID));
    }
}
