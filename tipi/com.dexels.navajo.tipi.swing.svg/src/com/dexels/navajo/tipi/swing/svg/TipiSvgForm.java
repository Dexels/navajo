package com.dexels.navajo.tipi.swing.svg;

import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiSvgForm extends TipiSvgComponent implements
		SvgMouseListener, SvgAnimationListener {

	private static final long serialVersionUID = 5653906058740273745L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSvgForm.class);
	
	private int width=50;
	private int height=20;

	private String buttonText = null;
	
	private int currentIndex = 0;
	
	private String basePath = "Person";
	private Message currentMessage;
	private Message currentArrayMessage;
	
	@Override
	public Object createContainer() {
		Object o = super.createContainer();
		myComponent.addSvgDocumentListener(new SvgDocumentAdapter(){

			@Override
			public void onDocumentLoadingFinished() {
			}
		});
		myComponent.init(myContext.getResourceURL("com/dexels/navajo/tipi/swing/svg/form.svg"));
		myComponent.setPreferredSize(new Dimension(20,10));
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
		logger.info("SIZE: "+dimension);
		myComponent.setPreferredSize(dimension);
		
	}

	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		logger.info("Loading stuff");
		super.loadData(n, method);
		logger.info("Loading stuff");
		currentMessage = n.getMessage(basePath);
		updateForm();
	}



	private void updateForm() {
		if(currentMessage==null) {
			logger.info("huh?");
			return;
		}
		ArrayList<Property> al = currentMessage.getAllProperties();
		for (int i = 0; i < al.size(); i++) {
			Property p = al.get(i);
			if(myComponent.isExisting(p.getName())) {
				String name = myComponent.getTagName(p.getName());
				if(name.equals("text")) {
					logger.info("Setting text:");
					Object typedValue = p.getTypedValue();
					if(typedValue!=null) {
						myComponent.setTextContent(p.getName(), typedValue.toString());
					}
				}
				if(name.equals("image")) {
					logger.info("Image found!");
					Object typedValue = p.getTypedValue();
					
					if(typedValue!=null && typedValue instanceof Binary) {
						logger.info("Binary found!");
						
						Binary b = (Binary)typedValue;
						URL url;
						try {
							url = b.getURL();
							if(url!=null) {

								myComponent.setAttribute("http://www.w3.org/1999/xlink",p.getName(),"xlink:href",url.toString());
								logger.info("url: "+url.toString());
							} else {
								logger.info("Null url!");
							}
						} catch (MalformedURLException e) {
							logger.error("Error: ",e);
						}
						
					} else {
						myComponent.setAttribute("http://www.w3.org/1999/xlink",p.getName(),"xlink:href",getClass().getClassLoader().getResource("tipi/icons/navigate_cross_red.png").toString());
						
					}
					
				}
			}
		}
	}

	@Override
	public void onActivate(String targetId) {
		try {
			performTipiEvent("onActionPerformed", null, false);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
		super.onActivate(targetId);
	}

	@Override
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
