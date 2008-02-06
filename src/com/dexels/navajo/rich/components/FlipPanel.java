package com.dexels.navajo.rich.components;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.RootPaneContainer;
import javax.swing.Timer;

public class FlipPanel extends JPanel {
	private PerspectiveImagePanel animationPanel = new PerspectiveImagePanel(this);
	private ArrayList<JComponent> components = new ArrayList();
	private BorderLayout layout = new BorderLayout();
	JComponent visibleComponent = null;

	public FlipPanel() {
		setLayout(layout);
	}
	
	public void addComponent(JComponent c) {
		components.add(c);
		c.doLayout();
		visibleComponent = c;
		add(c, BorderLayout.CENTER);
		for (int i = 0; i < components.size(); i++) {
			components.get(i).setVisible(false);
		}
		c.setVisible(true);
	}

	public void flipForwards() {		
		((RootPaneContainer) getTopLevelAncestor()).getRootPane().setGlassPane(animationPanel);
		getRootPane().getLayeredPane().add(animationPanel, 0);
		int idx = components.indexOf(getVisibleComponent());
		if (idx > -1) {
			if (idx == components.size() - 1) {
				idx = -1;
			}
			
			JComponent next = components.get(idx + 1);

			next.setBounds(getVisibleComponent().getBounds());
			animationPanel.setComponents(getVisibleComponent(), next);
			animationPanel.setVisible(true);
			animationPanel.setDirection(false);			
			add(animationPanel, BorderLayout.CENTER);			
			animationPanel.flip();
			visibleComponent = next;
		}
	}
	
	public void setSpeed(int halftimeMillis){
		animationPanel.setSpeed(halftimeMillis);
	}

	public void flipBackwards() {		
		((RootPaneContainer) getTopLevelAncestor()).getRootPane().setGlassPane(animationPanel);
		int idx = components.indexOf(getVisibleComponent());
		if (idx > -1) {
			if (idx == 0) {
				idx = components.size();
			}
			JComponent next = components.get(idx - 1);
			next.setBounds(getVisibleComponent().getBounds());
			animationPanel.setComponents(getVisibleComponent(), next);
			animationPanel.setVisible(true);
			animationPanel.setDirection(true);
//			add(next, BorderLayout.CENTER);
			add(animationPanel, BorderLayout.CENTER);	
			animationPanel.flip();
			visibleComponent = next;
		}
	}

	public JComponent getVisibleComponent() {
		for (int i = 0; i < components.size(); i++) {
			JComponent current = components.get(i);			
			if (components.get(i).isVisible()) {
				return components.get(i);
			}
		}
		return null;
	}

}
