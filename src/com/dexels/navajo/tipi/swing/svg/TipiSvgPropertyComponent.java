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
	
	public Object createContainer() {
		super.createContainer();
		getSvgComponent().addSvgDocumentListener(new SvgDocumentAdapter(){


			public void onDocumentLoadingFinished() {
				if(myProperty!=null) {
					try {
						updateProperty(myProperty);
					} catch (NavajoException e) {
						e.printStackTrace();
					}
//					myComponent.setRegisteredIds(getSvgComponent());

				}
			}
		});
		getSvgComponent().addSvgMouseListener(new SvgMouseListener(){

			public void onActivate(String targetId) {
				if(myProperty!=null) {
					try {
						Selection ss = myProperty.getSelectionByValue(targetId);
						if(ss!=null) {
							myProperty.setSelected(ss, !ss.isSelected());
						}
					} catch (NavajoException e) {
						e.printStackTrace();
					}
				}
			}

			public void onClick(String targetId) {
			}

			public void onMouseDown(String targetId) {
				
			}

			public void onMouseMove(String targetId) {
			}

			public void onMouseOut(String targetId) {
			}

			public void onMouseOver(String targetId) {
				System.err.println("Over: "+targetId);
				
			}

			public void onMouseUp(String targetId) {
				
			}});
		return myComponent;
	}
	
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
//		
	}

	
	public Property getProperty() {
		return myProperty;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setProperty(final Property p) {
		if(myProperty == p) {
			System.err.println("Set property.... ");
			Thread.dumpStack();
			return;
		}
		if(myProperty!=null) {
			myProperty.removePropertyChangeListener(this);
		}
		
		myProperty = p;	
		List<Selection> selections;
		try {
			selections = p.getAllSelections();
			StringBuffer sb = new StringBuffer();
			Iterator<Selection> ss = selections.iterator();
			System.err.println("ENTERING LOOP");
			while(ss.hasNext()) {
				Selection selection = ss.next();
				sb.append(selection.getValue());
				System.err.println("Adding registration: "+selection.getValue());
				getSvgComponent().registerId(selection.getValue(),getSvgComponent().getDocument());
				if(ss.hasNext()) {
					sb.append(",");
				}
			}
//			registerId(elem, e.getSVGDocument());

	//		myComponent.setRegisteredIds(sb.toString());
			updateProperty(p);
			p.addPropertyChangeListener(this);
			
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}

	public void propertyChange( PropertyChangeEvent evt) {
		getSvgComponent().runInUpdateQueue(new Runnable(){

			public void run() {
				try {
					updateProperty(myProperty);
				} catch (NavajoException e) {
					e.printStackTrace();
				}
			}});
	}
	
	private void updateProperty(Property p) throws NavajoException {
		System.err.println("Setting property: "+p.getFullPropertyName());
		System.err.println("SVG NULL? "+(getSvgComponent()==null));
		System.err.println("Document null? "+getSvgComponent().getDocument()==null);
		List<Selection> ll = p.getAllSelections();
		for (Selection selection : ll) {
			System.err.println("Setting selection: "+selection.getName()+" value: "+selection.getValue()+" selected: "+selection.isSelected());
			SVGElement s = (SVGElement) getSvgComponent().getDocument().getElementById(selection.getValue());
			if(s!=null) {
				s.setAttribute("class", selection.isSelected()?"selectedStyle":"unSelectedStyle");
			}
		}
	}
}
