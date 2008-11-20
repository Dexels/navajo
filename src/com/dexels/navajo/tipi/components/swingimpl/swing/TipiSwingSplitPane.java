package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.event.*;
import java.beans.*;

import javax.swing.*;

import org.jdesktop.animation.transitions.*;
import org.jdesktop.animation.transitions.EffectsManager.*;
import org.jdesktop.animation.transitions.effects.*;

public class TipiSwingSplitPane extends JSplitPane {
	public static String STRING_ORIENTATION = "stringOrientation";
	
	public TipiSwingSplitPane(int orientation, JPanel left, JPanel right) {
		super(orientation,left,right);
		addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e) {
				System.err.println("click!");
			}
		});
		ComponentState end = new ComponentState(this);
		
//		Move effect = new Move();
//		EffectsManager.setEffect(this, effect, TransitionType.DISAPPEARING);
	}

	
	public void setStringOrientation(String orientation) {
		String old = getStringOrientation();
		if("vertical".equals(orientation)) {
			setOrientation(VERTICAL_SPLIT);
		}
		if("horizontal".equals(orientation)) {
			setOrientation(HORIZONTAL_SPLIT);
		}
		firePropertyChange(STRING_ORIENTATION, old, orientation);
	}
	
	public String getStringOrientation() {
		if (getOrientation()==VERTICAL_SPLIT) {
			return "vertical";
		} else {
			return "horizontal";
		}
	}
}
