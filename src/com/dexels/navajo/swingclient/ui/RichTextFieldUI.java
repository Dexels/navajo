package com.dexels.navajo.swingclient.ui;

import java.awt.*;
import java.beans.*;

import javax.swing.*;
import javax.swing.plaf.basic.*;

import com.dexels.navajo.tipi.swingclient.components.*;

public class RichTextFieldUI extends BasicTextFieldUI {
	private final JComponent me;
	int borderWidth = 1;
	int text_inset = 6;

	public RichTextFieldUI(JComponent c){
		this.me = c;
		me.addPropertyChangeListener("editable", new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt) {
//				System.err.println("prop: " + evt.getPropertyName());
				if(((Boolean)evt.getNewValue()).booleanValue()){					
					me.setFont(new Font("Dialog", Font.PLAIN, 10));
				}else{					
					me.setFont(new Font("Dialog", Font.BOLD, 10));
				}
			}			
		});
	}
	
	@Override
	protected Rectangle getVisibleEditorRect() {
		Rectangle parent = super.getVisibleEditorRect();
		if (parent != null) {
			return new Rectangle(parent.x + text_inset, parent.y, parent.width - 2 * text_inset, parent.height);
		} else {
			return super.getVisibleEditorRect();
		}
	}

	@Override
	protected void paintSafely(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		

		final PropertyField pf;
		boolean enabled = true;
		if (me instanceof PropertyField) {
			pf = (PropertyField) me;
			if (pf != null) {
				enabled = pf.isEditable();
			}
			if(!enabled){
				me.setForeground(Color.black);
			}
		}		

//		System.err.println("Stuck in loop?");
		
		Rectangle bounds = me.getBounds();
		int arc = (int) (bounds.height / 1.5);

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(me.getBackground());

		if (enabled) {
			g2.fillRoundRect(0, 0, bounds.width, bounds.height, arc, arc);
		}

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

		if (!me.hasFocus() && enabled) {
			g2.setStroke(new BasicStroke(borderWidth));
			g2.setPaint(new GradientPaint(0, 0, Color.black, 0, bounds.height, Color.white));
			g2.drawRoundRect(0, 0, bounds.width - borderWidth, bounds.height - borderWidth, arc, arc);
		} else if (enabled) {
			g2.setStroke(new BasicStroke(borderWidth * 3));
			g2.setColor(new Color(73, 154, 255));
			// g2.setPaint(new GradientPaint(0,0,new Color(73,154,255),
			// 0,bounds.height, Color.white));
			g2.drawRoundRect(0, 0, bounds.width - borderWidth, bounds.height - borderWidth, 0, 0);
		}
		g2.dispose();
		

		super.paintSafely(g);

	}

}
