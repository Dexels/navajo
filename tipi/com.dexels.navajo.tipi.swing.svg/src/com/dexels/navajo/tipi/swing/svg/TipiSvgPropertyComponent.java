package com.dexels.navajo.tipi.swing.svg;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.svg.SVGElement;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.tipi.TipiEventListener;
import com.dexels.navajo.tipi.internal.PropertyComponent;
import com.dexels.navajo.tipi.swing.svg.impl.SvgBatikComponent;

public class TipiSvgPropertyComponent extends TipiSvgComponent implements PropertyComponent, PropertyChangeListener {
	private static final long serialVersionUID = 711068173062950509L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSvgPropertyComponent.class);
	private String propertyName = null;
	private Property myProperty = null;
	
	@Override
	public Object createContainer() {
		super.createContainer();
		getSvgComponent().addSvgDocumentListener(new SvgDocumentAdapter(){


			@Override
			public void onDocumentLoadingFinished() {
				if(myProperty!=null) {
					try {
						updateProperty(myProperty);
					} catch (NavajoException e) {
						logger.error("Error: ",e);
					}
//					myComponent.setRegisteredIds(getSvgComponent());

				}
			}
		});
		getSvgComponent().addSvgMouseListener(new SvgMouseListener(){

			@Override
			public void onActivate(String targetId) {
				if(myProperty!=null) {
					try {
						Selection ss = myProperty.getSelectionByValue(targetId);
						if(ss!=null) {
							myProperty.setSelected(ss, !ss.isSelected());
						}
					} catch (NavajoException e) {
						logger.error("Error: ",e);
					}
				}
			}

			@Override
			public void onClick(String targetId) {
			}

			@Override
			public void onMouseDown(String targetId) {
				
			}

			@Override
			public void onMouseMove(String targetId) {
			}

			@Override
			public void onMouseOut(String targetId) {
			}

			@Override
			public void onMouseOver(String targetId) {
				logger.info("Over: "+targetId);
				
			}

			@Override
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

	@Override
	public void addTipiEventListener(TipiEventListener listener) {
//		
	}

	
	@Override
	public Property getProperty() {
		return myProperty;
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public void setProperty(final Property p) {
		if(myProperty == p) {
			logger.info("Set property.... ");
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
			logger.info("ENTERING LOOP");
			while(ss.hasNext()) {
				Selection selection = ss.next();
				sb.append(selection.getValue());
				logger.info("Adding registration: "+selection.getValue());
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
			logger.error("Error: ",e);
		}
	}

	@Override
	public void propertyChange( PropertyChangeEvent evt) {
		getSvgComponent().runInUpdateQueue(new Runnable(){

			@Override
			public void run() {
				try {
					updateProperty(myProperty);
				} catch (NavajoException e) {
					logger.error("Error: ",e);
				}
			}});
	}
	
	private void updateProperty(Property p) throws NavajoException {
		logger.info("Setting property: "+p.getFullPropertyName());
		logger.info("SVG NULL? "+(getSvgComponent()==null));
		List<Selection> ll = p.getAllSelections();
		for (Selection selection : ll) {
			logger.info("Setting selection: "+selection.getName()+" value: "+selection.getValue()+" selected: "+selection.isSelected());
			SVGElement s = (SVGElement) getSvgComponent().getDocument().getElementById(selection.getValue());
			if(s!=null) {
				s.setAttribute("class", selection.isSelected()?"selectedStyle":"unSelectedStyle");
			}
		}
	}
}
