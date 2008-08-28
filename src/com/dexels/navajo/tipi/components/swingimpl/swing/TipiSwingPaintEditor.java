package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.formatters.*;
import com.dexels.navajo.tipi.components.swingimpl.parsers.*;

public class TipiSwingPaintEditor extends JTextField {
	private PaintFormatter myFormatter = new PaintFormatter();
	private Property myProperty;
	private TipiComponent myComponent;
	private TipiGradientPaint myPaint;

	public TipiSwingPaintEditor(TipiComponent tc) {
		myComponent = tc;
		setOpaque(true);
		setBackground(null);
		getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent arg0) {
				updateMe(getText());
			}

			public void insertUpdate(DocumentEvent arg0) {
				updateMe(getText());
			}

			public void removeUpdate(DocumentEvent arg0) {
				updateMe(getText());
			}
		});
		// addPropertyChangeListener("text",new PropertyChangeListener(){
		//
		// public void propertyChange(PropertyChangeEvent e) {
		//			
		// }});
	}

	public void updateMe(String text) {
		if (myProperty == null) {
			return;
		}
		if (text == null || "".equals(text) || !text.startsWith("{paint:/")) {
			myProperty.setAnyValue(null);
			return;
		}
		TipiGradientPaint c;
		try {
			c = (TipiGradientPaint) myComponent.getContext().evaluateExpression(text, myComponent, null);
			setPaint(c);
			myProperty.setAnyValue(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setPaint(TipiGradientPaint c) {
		myPaint = c;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if(myPaint!=null) {
	if (myPaint.getPaint() != null) {
			myPaint.setBounds(this.getBounds());
			Paint p = myPaint.getPaint();
			Graphics2D g2 = (Graphics2D) g;
			Paint oldPaint = g2.getPaint();
			g2.setPaint(p);
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.setPaint(oldPaint);
		}
	}
		super.paintComponent(g);

	}

	public void setProperty(Property p) {
		myProperty = p;
		Object o = p.getTypedValue();
		if (o == null) {
			setText("unknown color");
			return;
		}
		TipiGradientPaint cc = (TipiGradientPaint) o;
		setPaint(cc);
		setText(myFormatter.format(cc));
	}

}
