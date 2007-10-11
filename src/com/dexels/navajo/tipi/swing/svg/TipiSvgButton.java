package com.dexels.navajo.tipi.swing.svg;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.swing.svg.*;
import com.dexels.navajo.tipi.swing.svg.impl.*;

public class TipiSvgButton extends TipiSvgComponent implements
		SvgMouseListener, SvgAnimationListener {

	private int width=50;
	private int height=20;

	private String buttonText = null;
	
	public Object createContainer() {
		Object o = super.createContainer();
		myComponent.addSvgDocumentListener(new SvgDocumentAdapter(){

			public void onDocumentLoadingFinished() {
				myComponent.setTextContent("buttontext",buttonText);
	}
		});
	//	myComponent.getPreferredSize();
		myComponent.setPreferredSize(new Dimension(20,10));
		//my
//		myComponent.init(getClass().getClassLoader());
		//myComponent.setBackground(Color.black);
		return o;
		
	}
	
	
	
	protected void setComponentValue(String name, Object object) {
		// TODO Auto-generated method stub
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
