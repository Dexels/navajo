package com.dexels.navajo.tipi.vaadin.components;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;


public class TipiCheckbox extends TipiVaadinComponentImpl {
	private static final long serialVersionUID = 6085520508083912004L;

	private CheckBox myButton;

	  private boolean iAmEnabled = true;

	public Object createContainer() {
	    myButton = new CheckBox();
	    myButton.addListener(new ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 2756679049468073536L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					Map<String,Object> m = new HashMap<String, Object>();
					m.put("value", myButton.getValue());
					performTipiEvent("onSelectionChanged", m, false);
					getAttributeProperty("selected").setAnyValue( myButton.getValue());
				} catch (TipiException e) {
					e.printStackTrace();
				}
			}
		});
	    return myButton;
	  }

	  public void setComponentValue(final String name, final Object object) {
	    super.setComponentValue(name, object);
	        if (name.equals("text")) {
	          myButton.setCaption( (String) object);
	        }
	        if ("icon".equals(name)) {
                myButton.setIcon( getResource(object));
	        }
	        if (name.equals("enabled")) {
	          // Just for the record.
	          iAmEnabled = ((Boolean)object).booleanValue();
	          myButton.setEnabled(iAmEnabled);
	        }
	  }

	 
	  public Object getComponentValue(String name) {
	    if (name.equals("text")) {
	      return myButton.getCaption();
	    }
//	    if (name.equals("selected")) {
//	      return new Boolean(myButton.getValue());
//	    }

	    return super.getComponentValue(name);
	  }


}
