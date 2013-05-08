package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiModalInternalFrame;
import com.dexels.navajo.tipi.tipixml.XMLElement;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

// VERY IMPORTANT -- do not add this component to any classdefinition xml. It is not intended for normal usage, only to be able to apply CSS to showInfo/showWarning/showError
public class TipiMessageDialog extends TipiSwingComponentImpl{

	private static final long serialVersionUID = 8645510749158311198L;
	private String title = "";
	private String text = "";
	private String cssClass = "";
	private String cssStyle = "";
	private int messageType = JOptionPane.INFORMATION_MESSAGE;
	
	private static final XMLElement acceptedValues;
	
	// initialize the acceptedValues
	// should reflect:
	/*
	 * <values>
         <value direction="inout" name="title" type="string" />
         <value direction="inout" name="text" type="string" />
         <value direction="inout" name="cssClass" type="string" />
         <value direction="inout" name="cssStyle" type="string" />
         <value direction="inout" name="messageType" type="string" />
       </values>
	 */
	static {
		XMLElement title = new XMLElement();
		title.setAttribute("direction", "inout");
		title.setAttribute("name", "title");
		title.setAttribute("type", "string");
		title.setName("value");
		XMLElement text = new XMLElement();
		text.setAttribute("direction", "inout");
		text.setAttribute("name", "text");
		text.setAttribute("type", "string");
		text.setName("value");
		XMLElement messageType = new XMLElement();
		messageType.setAttribute("direction", "inout");
		messageType.setAttribute("name", "messageType");
		messageType.setAttribute("type", "string");
		messageType.setName("value");
		XMLElement cssClass = new XMLElement();
		cssClass.setAttribute("direction", "inout");
		cssClass.setAttribute("name", "cssClass");
		cssClass.setAttribute("type", "string");
		cssClass.setName("value");
		XMLElement cssStyle = new XMLElement();
		cssStyle.setAttribute("direction", "inout");
		cssStyle.setAttribute("name", "cssStyle");
		cssStyle.setAttribute("type", "string");
		cssStyle.setName("value");
		acceptedValues = new XMLElement();
		acceptedValues.setAttribute("name", "values");
		acceptedValues.addChild(title);
		acceptedValues.addChild(text);
		acceptedValues.addChild(messageType);
		acceptedValues.addChild(cssClass);
		acceptedValues.addChild(cssStyle);
	}

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiMessageDialog.class);
	
	public TipiMessageDialog(String name) {
		myId = name;
		myName = name;
	}
	
	public void initialize(TipiContext tc)
	{
		setContext(tc);
		setClassName(myName);
		setPropertyComponent(false);
		setComponentType("messagedialog");
		try
		{
			loadValues(acceptedValues, null);
		}
		catch(TipiException te)
		{
			logger.warn("Caught TipiException at initializing, failing silently", te);
		}
	}
	
	@Override
	public void setContainer(Object o)
	{
		// shouldn't do anything
	}
	public Object createContainer() {
		runSyncInEventThread(new Runnable() {

			public void run() {

				if (mySwingTipiContext.getOtherRoot() != null) {
					TipiModalInternalFrame.showInternalMessage(mySwingTipiContext.getOtherRoot()
							.getRootPane(), mySwingTipiContext.getOtherRoot().getContentPane(),
							title, text, mySwingTipiContext.getPoolSize(), messageType);
				} else 
					JOptionPane.showMessageDialog((Component) mySwingTipiContext.getTopDialog(),
							text, title, messageType);

			}
		});
		return null;
	}

	public void instantiateComponent(XMLElement instance, XMLElement classdef)
			throws TipiException {
		super.instantiateComponent(instance, classdef);
	}

	public void setComponentValue(final String name, final Object object) {
		runSyncInEventThread(new Runnable() {

			public void run() {
				if (name.equals("title")) {
					title = object.toString();
				}
				if (name.equals("messageType")) {
					messageType = Integer.valueOf(object.toString());
				}
				if (name.equals("text")) {
					text = object.toString();
				}
				if (name.equals("cssClass")) {
					cssClass = object.toString();
				}
				if (name.equals("cssStyle")) {
					cssStyle = object.toString();
				}
			}
		});
		super.setComponentValue(name, object);
	}

	public Object getComponentValue(String name) {
		if ("title".equals(name)) {
			return title;
		}
		if ("text".equals(name)) {
			return text;
		}
		if ("cssClass".equals(name)) {
			return cssClass;
		}
		if ("cssStyle".equals(name)) {
			return cssStyle;
		}
		if ("messageType".equals(name)) {
			return messageType;
		}
		return super.getComponentValue(name);
	}

	public void disposeComponent() {
		// nothing to do

	}

	public void reUse() {
	}
}
