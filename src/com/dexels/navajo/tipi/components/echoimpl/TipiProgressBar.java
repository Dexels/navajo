package com.dexels.navajo.tipi.components.echoimpl;

import java.net.URL;

import nextapp.echo2.app.*;
import echopointng.ContainerEx;
import echopointng.ProgressBar;
import echopointng.image.URLImageReference;

//
//<tipiclass class="TipiProgressBar" name="progressbar" package="com.dexels.navajo.tipi.components.echoimpl" propertycomponent="false" type="component" addtocontainer="true">
//    <events>
//        <event name="onInstantiate"/>
//    </events>
//    <values>
//        <value direction="inout" name="text" type="string"/>
//        <value direction="in" name="value" type="integer"/>
//        <value direction="in" name="background" type="color"/>
//        <value direction="in" name="foreground" type="color"/>
//                 <value direction="inout" name="tooltip" type="string"/>
//        <value direction="in" name="enabled" type="boolean"/>
//        <value direction="in" name="visible" type="boolean"/>
//        <value direction="in" name="orientation" type="selection">
//            <option description="Horizontal" value="horizontal"/>
//            <option description="Vertical" value="vertical"/>
//        </value>
//        <value direction="in" name="indeterminate" type="boolean"/>
//    </values>
//    <methods/>
//</tipiclass>


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
