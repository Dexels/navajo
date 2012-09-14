package com.dexels.navajo.tipi.swing.svg;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.svg.SVGDocument;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.swing.svg.impl.SvgBaseComponent;
import com.dexels.navajo.tipi.swing.svg.impl.SvgBatikComponent;

public class TipiSvgComponent extends TipiSwingDataComponentImpl implements
		SvgMouseListener, SvgAnimationListener {

	private static final long serialVersionUID = -4356555280537206804L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSvgComponent.class);
	protected SvgBaseComponent myComponent = null;
	protected String registeredIds;

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
		if("setValue".equals(name)) {
			String id = (String) compMeth.getEvaluatedParameter("id", event).value;
			String attrName = (String) compMeth.getEvaluatedParameter("attributeName", event).value;
			String value = (String) compMeth.getEvaluatedParameter("value", event).value;
			setAttribute(null, id, attrName, value);
		}
		//        <method name="setValue">
//        <param name="id" type="string" />
//        <param name="name" type="string" />
//        <param name="value" type="string" />
//     </method>
	} 

	@Override
	protected void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
		if (name.equals("image")) {
			if (object instanceof URL) {
					URL u = (URL) object;
					myComponent.init(u);
			}
			
		}
		if(name.equals("ids")) {
			registeredIds = (String)object;
			myComponent.setRegisteredIds((String)object);
		}
	}
	
	public void setDocument(SVGDocument doc){		
		((SvgBatikComponent)myComponent).setDocument(doc);
	}

	@Override
	public Object createContainer() {
		myComponent = new SvgBatikComponent();
		myComponent.addSvgAnimationListener(this);
		myComponent.setDoubleBuffered(true);
		myComponent.addSvgMouseListener(this);
		myComponent.addSvgDocumentListener(new SvgDocumentAdapter(){

			@Override
			public void onDocumentLoadingFinished() {
				logger.info("Loading finished detected@");
				myComponent.setRegisteredIds(registeredIds);
			}
		});
		//myComponent.add
		return myComponent;
	}

	public void setAttribute(final String xlinkNS, final String id, final String attributeName, final String value) {
		myComponent.setAttribute(xlinkNS, id, attributeName, value);
	}
	
	@Override
	public void onClick(String targetId) {
		logger.info("On click detected!");
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", targetId);
		try {
			performTipiEvent("onClick", m, true);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
	}

	@Override
	public void onMouseDown(String targetId) {
		logger.info("mouse down!");
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", targetId);
		try {
			logger.info("Mouse down: "+targetId);
			performTipiEvent("onMouseDown", m, true);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
	}
	
	@Override
	public void onActivate(String targetId) {
		logger.info("Activate!!!!!!");
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", targetId);
		try {
			logger.info("onActivate "+targetId);
			performTipiEvent("onActivate", m, true);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
	}

	@Override
	public void onMouseMove(String targetId) {
		logger.info("Sauspan!");
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", targetId);
		try {
			performTipiEvent("onMouseMove", m, true);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
	}

	@Override
	public void onMouseOut(String targetId) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", targetId);
		try {
			performTipiEvent("onMouseOut", m, true);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
	}

	@Override
	public void onMouseOver(String targetId) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", targetId);
		try {
			performTipiEvent("onMouseOver", m, true);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
	}

	@Override
	public void onMouseUp(String targetId) {
		logger.info("mouse up!");
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", targetId);
		try {
			performTipiEvent("onMouseUp", m, true);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
	}

	@Override
	public void onAnimationEnded(String animationId, String targetId) {
		System.err.print("*");
		//Thread.dumpStack();
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", animationId);
		m.put("targetId", targetId);
		try {
			performTipiEvent("onAnimationEnded", m, true);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
	}
	


	

	@Override
	public void onAnimationStarted(String animationId, String targetId) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", animationId);
		m.put("targetId", targetId);
		try {
			performTipiEvent("onAnimationStarted", m, true);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}

	}

}
