package com.dexels.navajo.tipi.components.echoimpl;

import echopointng.ProgressBar;



public class TipiProgressBar extends TipiEchoDataComponentImpl {
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
