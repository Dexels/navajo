package com.dexels.navajo.tipi.components.echoimpl;

import echopointng.ProgressBar;



public class TipiProgressBar extends TipiEchoDataComponentImpl {
	private static final long serialVersionUID = -39973562290542420L;
	private ProgressBar myBar;

    public Object createContainer() {
    	myBar = new ProgressBar();
 
        return myBar;
    }

   

    // public Object getActualComponent() {
    // return myColumn;
    // }

    protected void setComponentValue(String name, Object object) {
        if ("text".equals(name)) {
        	// ignored
        }
        if ("value".equals(name)) {
        	// ignored
        	Integer i = (Integer)object;
        	if(i!=null) {
            	myBar.setValue(i.intValue());
        	}
        }
        super.setComponentValue(name, object);
    }

}
