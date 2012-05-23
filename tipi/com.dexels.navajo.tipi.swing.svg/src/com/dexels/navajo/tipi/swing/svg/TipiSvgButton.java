package com.dexels.navajo.tipi.swing.svg;

import java.awt.Dimension;
import java.net.URL;

import com.dexels.navajo.tipi.TipiException;

public class TipiSvgButton extends TipiSvgComponent implements
		SvgMouseListener, SvgAnimationListener {

	private static final long serialVersionUID = 9092120010940830449L;
	private int width=50;
	private int height=20;

	private String buttonText = null;
	
	@Override
	public Object createContainer() {
		Object o = super.createContainer();
		myComponent.addSvgDocumentListener(new SvgDocumentAdapter(){

			@Override
			public void onDocumentLoadingFinished() {
				myComponent.setTextContent("buttontext",buttonText);
	}
		});
		myComponent.setPreferredSize(new Dimension(20,10));
		return o;
		
	}
	
	
	
	@Override
	protected void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
		if (name.equals("image")) {
			if (object instanceof URL) {
					URL u = (URL) object;
					myComponent.init(u);
					myComponent.setRegisteredIds("background");
					myComponent.setTextContent("buttontext",buttonText);

			}
		}
		if(name.equals("text")) {
			buttonText = (String)object;
			myComponent.setTextContent("buttontext",(String)object);
		}
		
		if(name.equals("ids")) {
			myComponent.setRegisteredIds((String)object);
		}
	
		if(name.equals("width")) {
			width = ((Integer)object).intValue();
			updateSize();
		}
		if(name.equals("height")) {
			height = ((Integer)object).intValue();
			updateSize();
		}

	}

	private void updateSize() {
		Dimension dimension = new Dimension(width,height);
		System.err.println("SIZE: "+dimension);
		myComponent.setPreferredSize(dimension);
		
	}



	@Override
	public void onActivate(String targetId) {
		try {
			performTipiEvent("onActionPerformed", null, false);
		} catch (TipiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onActivate(targetId);
	}
	

}
