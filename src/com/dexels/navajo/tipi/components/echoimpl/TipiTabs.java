package com.dexels.navajo.tipi.components.echoimpl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nextapp.echo2.app.Component;
import nextapp.echo2.extras.app.TabPane;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiTabs extends TipiEchoDataComponentImpl {
    private ArrayList tipiList = new ArrayList();

    private ArrayList methodList = new ArrayList();

    private Map tipiMap = new HashMap();

    private TipiComponent selectedComponent = null;

    private Component lastSelectedTab = null;


    private TabPane myTabbedPane;

    public Object createContainer() {
        final TipiComponent me = this;
        myTabbedPane = new TabPane();

        myTabbedPane.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
            	System.err.println("Hoei: "+evt.getPropertyName());
            	
            }
            });
        
        
        return myTabbedPane;
    }

    
    protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
    }
	public void removeFromContainer(Object c) {
		myTabbedPane.remove((Component)c);

	}
    public void addToContainer(Object c, Object constraints) {
    	Component cc = (Component)c;
		myTabbedPane.add(cc);
    }


    public Object getComponentValue(String name) {
        /** @todo Override this com.dexels.navajo.tipi.TipiComponent method */
        if (name.equals("selected")) {

        }
        if (name.equals("lastselected")) {
            TipiComponent tc = getChildByContainer(lastSelectedTab);
            return tc;
        }
        if (name.equals("selectedindex")) {
        }
        if (name.equals("lastselectedindex")) {
            TipiComponent tc = getChildByContainer(lastSelectedTab);
        }

        return super.getComponentValue(name);
    }
}
