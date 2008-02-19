package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.formatters.*;

public class TipiSwingColorButton extends JTextField {
	private ColorFormatter myFormatter = new ColorFormatter();
//	private ColorParser cp = new ColorParser();
	private Property myProperty;
	private TipiComponent myComponent;
	
	public TipiSwingColorButton(TipiComponent tc) {
		myComponent = tc;
		getDocument().addDocumentListener(new DocumentListener(){

			public void changedUpdate(DocumentEvent arg0) {
				updateMe(getText());
			}

			public void insertUpdate(DocumentEvent arg0) {
				updateMe(getText());
			}

			public void removeUpdate(DocumentEvent arg0) {
				updateMe(getText());
			}});
//		addPropertyChangeListener("text",new PropertyChangeListener(){
//
//			public void propertyChange(PropertyChangeEvent e) {
//			
//			}});
	}
	
	public void updateMe(String text) {
		if(myProperty==null) {
			return;
		}
		if(text==null || "".equals(text) || !text.startsWith("{color:/")) {
			myProperty.setAnyValue(null);
			return;
		}
//		public Object parse(TipiComponent source, String name, String expression, TipiEvent te) {
		System.err.println("Pars: "+text);
		Color c;
		try {
			c = (Color) myComponent.getContext().evaluateExpression(text,myComponent, null);
			System.err.println("CCC: "+c);
			setBackground(c);
			myProperty.setAnyValue(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setProperty(Property p) {
		myProperty = p;
		Object o = p.getTypedValue();
		if(o==null) {
			setText("unknown color");
			return;
		}
		Color cc = (Color)o;
		setBackground(cc);
		setText(myFormatter.format(cc));
	}

}
