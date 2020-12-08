/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class TipiDialog extends TipiVaadinComponentImpl{

	private static final Logger logger = LoggerFactory.getLogger(TipiDialog.class);
	private static final long serialVersionUID = 5476906786366044908L;
	private Window dialog;

	@Override
	public Object createContainer() {
		dialog = new Window();
		dialog.setModal(true);
		dialog.addListener(new CloseListener() {
			private static final long serialVersionUID = 938584507552292618L;

			@Override
            public void windowClose(CloseEvent e) {

            	try	{
            		try {
            			performTipiEvent("onWindowClosed", null, true);
            		} catch (TipiException e1) {
            			logger.error("Exception at onWindowClosed, ignoring...", e1);
            		}
            		myContext.disposeTipiComponent(TipiDialog.this);
        		} catch (TipiBreakException e1) {
        			logger.debug("Break: ", e1);
					if (e1.getType() == TipiBreakException.COMPONENT_DISPOSED) {
	            		myContext.disposeTipiComponent(TipiDialog.this);
					}
        		}
            }
        });
		getVaadinApplication().getMainWindow().addWindow(dialog);
		return dialog;
	}

	  @Override
	public void disposeComponent() {
		super.disposeComponent();
		getVaadinApplication().getMainWindow().removeWindow(dialog);
	}
	
	
	  @Override
	public void addToContainer(Object c, Object constraints) {
		  
		  super.addToContainer(c, constraints);
		  Component cc = (Component)c;
		  cc.setWidth("100%");
	  }



	@Override
	public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
		        if (name.equals("title")) {
		          dialog.setCaption( (String) object);
		        }
		        if ("icon".equals(name)) {
		        	dialog.setIcon( getResource(object));
		        }
		        if ("h".equals(name)) {
		        	dialog.setHeight(""+object+"px");
		        }
		        if ("w".equals(name)) {
		        	dialog.setWidth(""+object+"px");
		        }
		        if ("x".equals(name)) {
		        	dialog.setPositionX((Integer) object);
		        }
		        if ("y".equals(name)) {
		        	dialog.setPositionY((Integer) object);
		        }
		        if ("closable".equals(name)) {
		        	dialog.setClosable((Boolean) object);
		        }
		        if ("resizable".equals(name)) {
		        	dialog.setResizable((Boolean) object);
		        }
		  }
	
	    @Override
		protected synchronized void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
	        super.performComponentMethod(name, compMeth, event);
	        if (name.equals("dispose")) {
				getVaadinApplication().getMainWindow().removeWindow(dialog);
	        }
	    }
}
