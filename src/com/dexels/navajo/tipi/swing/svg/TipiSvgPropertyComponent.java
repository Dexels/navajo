package com.dexels.navajo.tipi.swing.svg;

import java.beans.*;
import java.util.*;

import org.w3c.dom.svg.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.swing.svg.impl.*;

public class TipiSvgPropertyComponent extends TipiSvgComponent implements PropertyComponent, PropertyChangeListener {
	private String propertyName = null;
	private Property myProperty = null;
	
	@Override
	protected void setComponentValue(String name, Object object) {
		if(name.equals("propertyName")) {
			propertyName = (String) object;
		}
		super.setComponentValue(name, object);
	}
	
	public SvgBatikComponent getSvgComponent() {
		return (SvgBatikComponent) myComponent;
	}

	public void addTipiEventListener(TipiEventListener listener) {
		
	}

	
	public Property getProperty() {
		return myProperty;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setProperty(final Property p) {
		if(myProperty == p) {
			return;
		}
		if(myProperty!=null) {
			myProperty.removePropertyChangeListener(this);
		}
		
		myProperty = p;	
		List<Selection> selections;
		try {
			selections = p.getAllSelectedSelections();
			StringBuffer sb = new StringBuffer();
			Iterator<Selection> ss = selections.iterator();
			while(ss.hasNext()) {
				Selection selection = ss.next();
				sb.append(selection.getValue());
				if(ss.hasNext()) {
					sb.append(",");
				}
			}
			myComponent.setRegisteredIds(sb.toString());
			updateProperty(p.getAllSelections());
			p.addPropertyChangeListener(this);
			
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}

	public void propertyChange( PropertyChangeEvent evt) {
		getSvgComponent().runInUpdateQueue(new Runnable(){

			public void run() {
				try {
					updateProperty(myProperty.getAllSelections());
				} catch (NavajoException e) {
					e.printStackTrace();
				}
			}});
	}
	
	private void updateProperty(List<Selection> ll) {
		for (Selection selection : ll) {
			SVGElement s = (SVGElement) getSvgComponent().getDocument().getElementById(selection.getValue());
			if(s!=null) {
				s.setAttribute("class", selection.isSelected()?"selectedStyle":"unSelectedStyle");
			}
		}
	}
}
