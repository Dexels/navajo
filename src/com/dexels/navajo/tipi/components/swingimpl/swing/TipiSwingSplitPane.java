package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jdesktop.animation.transitions.ComponentState;

public class TipiSwingSplitPane extends JSplitPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5314045642526479631L;
	public static String STRING_ORIENTATION = "stringOrientation";

	public TipiSwingSplitPane(int orientation, JPanel left, JPanel right) {
		super(orientation, left, right);
		addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				System.err.println("click!");
			}
		});
		ComponentState end = new ComponentState(this);

		// Move effect = new Move();
		// EffectsManager.setEffect(this, effect, TransitionType.DISAPPEARING);
	}

	public void setStringOrientation(String orientation) {
		String old = getStringOrientation();
		if ("vertical".equals(orientation)) {
			setOrientation(VERTICAL_SPLIT);
		}
		if ("horizontal".equals(orientation)) {
			setOrientation(HORIZONTAL_SPLIT);
		}
		firePropertyChange(STRING_ORIENTATION, old, orientation);
	}

	public String getStringOrientation() {
		if (getOrientation() == VERTICAL_SPLIT) {
			return "vertical";
		} else {
			return "horizontal";
		}
	}
}
