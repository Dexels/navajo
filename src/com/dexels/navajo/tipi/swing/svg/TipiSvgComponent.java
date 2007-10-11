package com.dexels.navajo.tipi.swing.svg;

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

public class TipiSvgComponent extends TipiSwingDataComponentImpl implements
		SvgMouseListener, SvgAnimationListener {


	protected SvgBaseComponent myComponent = null;
	private String registeredIds;
//
//	private int preferredHeight = 20;
//	private int preferredWidth = 20;
//	
	@Override
	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
		if ("fireAnimation".equals(name)) {
			Operand o = compMeth.getEvaluatedParameter("id", event);
			if (o != null) {
				String id = (String) o.value;
				myComponent.fireAnimation(id);
			}
		}
	} 

	@Override
	protected void setComponentValue(String name, Object object) {
		// TODO Auto-generated method stub
		super.setComponentValue(name, object);
		if (name.equals("image")) {
			if (object instanceof URL) {
					URL u = (URL) object;
					myComponent.init(u);
			}
//			if (object instanceof Binary) {
//				try {
//					Binary u = (Binary) object;
//					InputStream is = u.getDataAsStream();
//					myComponent.init(is);
//					is.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
			
		}
		if(name.equals("ids")) {
			registeredIds = (String)object;
			myComponent.setRegisteredIds((String)object);
		}
//		if(name.equals("preferredHeight")) {
//			myComponent.setRegisteredIds((String)object);
//		}
//		if(name.equals("preferredWidth")) {
//			myComponent.setRegisteredIds((String)object);
//		}

	}

	public Object createContainer() {
		myComponent = new SvgBatikComponent();
		myComponent.addSvgAnimationListener(this);
		myComponent.addSvgMouseListener(this);
		myComponent.addSvgDocumentListener(new SvgDocumentAdapter(){

			public void onDocumentLoadingFinished() {
				myComponent.setRegisteredIds(registeredIds);
			}
		});
		//myComponent.add
		return myComponent;
	}

	public void onClick(String targetId) {
		System.err.println("On click detected!");
		Map m = new HashMap();
		m.put("id", targetId);
		try {
			performTipiEvent("onClick", m, true);
		} catch (TipiException e) {
			e.printStackTrace();
		}
	}

	public void onMouseDown(String targetId) {
		Map m = new HashMap();
		m.put("id", targetId);
		try {
			System.err.println("Mouse down: "+targetId);
			performTipiEvent("onMouseDown", m, true);
		} catch (TipiException e) {
			e.printStackTrace();
		}
	}
	
	public void onActivate(String targetId) {
		System.err.println("Activate!!!!!!");
		Map m = new HashMap();
		m.put("id", targetId);
		try {
			System.err.println("onActivate "+targetId);
			performTipiEvent("onActivate", m, true);
		} catch (TipiException e) {
			e.printStackTrace();
		}
	}

	public void onMouseMove(String targetId) {
		Map m = new HashMap();
		m.put("id", targetId);
		try {
			performTipiEvent("onMouseMove", m, true);
		} catch (TipiException e) {
			e.printStackTrace();
		}
	}

	public void onMouseOut(String targetId) {
		Map m = new HashMap();
		m.put("id", targetId);
		try {
			performTipiEvent("onMouseOut", m, true);
		} catch (TipiException e) {
			e.printStackTrace();
		}
	}

	public void onMouseOver(String targetId) {
		Map m = new HashMap();
		m.put("id", targetId);
		try {
			performTipiEvent("onMouseOver", m, true);
		} catch (TipiException e) {
			e.printStackTrace();
		}
	}

	public void onMouseUp(String targetId) {
		Map m = new HashMap();
		m.put("id", targetId);
		try {
			performTipiEvent("onMouseUp", m, true);
		} catch (TipiException e) {
			e.printStackTrace();
		}
	}

	public void onAnimationEnded(String animationId, String targetId) {
		System.err.print("*");
		//Thread.dumpStack();
		Map m = new HashMap();
		m.put("id", animationId);
		m.put("targetId", targetId);
		try {
			performTipiEvent("onAnimationEnded", m, true);
		} catch (TipiException e) {
			e.printStackTrace();
		}
	}

	public void onAnimationStarted(String animationId, String targetId) {
		Map m = new HashMap();
		m.put("id", animationId);
		m.put("targetId", targetId);
		try {
			performTipiEvent("onAnimationStarted", m, true);
		} catch (TipiException e) {
			e.printStackTrace();
		}

	}

}
