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

public class TipiSvgForm extends TipiSvgComponent implements
		SvgMouseListener, SvgAnimationListener {

	private int width=50;
	private int height=20;

	private String buttonText = null;
	
	private int currentIndex = 0;
	
	private String basePath = "Persons";
	private Message currentMessage;
	private Message currentArrayMessage;
	
	public Object createContainer() {
		Object o = super.createContainer();
		myComponent.addSvgDocumentListener(new SvgDocumentAdapter(){

			public void onDocumentLoadingFinished() {
			}
		});
		
		
		myComponent.init(getClass().getClassLoader().getResource("form.svg"));

		
	//	myComponent.getPreferredSize();
		myComponent.setPreferredSize(new Dimension(20,10));
		//my
//		myComponent.init(getClass().getClassLoader());
		//myComponent.setBackground(Color.black);
		return o;
		
	}
	
	private void next() {
		if(currentArrayMessage==null) {
			return;
		}
		if(currentIndex>=currentArrayMessage.getArraySize()) {
			currentIndex = 0;
			updateForm();
			return;
		}
		currentIndex++;
		updateForm();
	}
	
	private void previous() {
		if(currentArrayMessage==null) {
			return;
		}
		if(currentIndex==0) {
			currentIndex = currentArrayMessage.getArraySize()-1;
			updateForm();
			return;
		}
		currentIndex++;
		updateForm();
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
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		System.err.println("Loading stuff");
		super.loadData(n, method);
		System.err.println("Loading stuff");
		currentArrayMessage = n.getMessage(basePath);
		currentMessage = currentArrayMessage.getMessage(currentIndex);
		updateForm();
		
		
		
	
	}



	private void updateForm() {
		// TODO Auto-generated method stub
		if(currentMessage==null) {
			System.err.println("huh?");
			return;
		}
		ArrayList al = currentMessage.getAllProperties();
		for (int i = 0; i < al.size(); i++) {
			Property p = (Property) al.get(i);
			if(myComponent.isExisting(p.getName())) {
				String name = myComponent.getTagName(p.getName());
				if(name.equals("text")) {
					System.err.println("Setting text:");
					myComponent.setTextContent(p.getName(), p.getTypedValue().toString());
				}
			}
		}
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

	protected void performComponentMethod(String name,TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
		if (name.equals("next")) {
			next();
			return;
		}
		if (name.equals("previous")) {
			previous();
			return;
		}
		
	}

}
