package com.dexels.navajo.tipi.vaadin.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class TipiButton extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = -6229336672215273524L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiButton.class);
	
	@Override
	public Object createContainer() {
		Button button = new Button();
		button.addListener(new ClickListener() {
			
			private static final long serialVersionUID = 6451608629231086440L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					performTipiEvent("onActionPerformed", null, true);
				} catch (TipiBreakException e) {
					logger.debug("Break in button event.",e);
					e.printStackTrace();
				} catch (TipiException e) {
					logger.error("Error in button event.",e);
				}
			}
		});
		return button;
	}

	  public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
		    Button v = (Button) getVaadinContainer();
		        if (name.equals("text")) {
		          v.setCaption( (String) object);
		        }
		        if ("icon".equals(name)) {
	                v.setIcon( getResource(object));
		        }
		        if ("style".equals(name)) {
//		        	BaseTheme.BUTTON_LINK;
	                v.setStyleName((String)object);
		        }
		        if ("defaultButton".equals(name)) {
//		        	BaseTheme.BUTTON_LINK;
	                Boolean b = (Boolean)object;
	                if (b) {
		                v.setClickShortcut(KeyCode.ENTER);
					} else {
		                v.removeClickShortcut();
					}

	                
		        }
	  }

	@Override
	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
		if(name.equals("fireAction")) {
			try {
				performTipiEvent("onActionPerformed", null, true);
			} catch (TipiException e) {
				e.printStackTrace();
			}

		}
	}
	  
	  
}
