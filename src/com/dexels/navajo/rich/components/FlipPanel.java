package com.dexels.navajo.rich.components;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class FlipPanel extends JPanel {
	private int direction = PerspectiveTransform.FLIP_LEFT;
	private PerspectiveImagePanel animationPanel = new PerspectiveImagePanel();
	private ArrayList<JComponent> components = new ArrayList();
	private BorderLayout layout = new BorderLayout();
	JComponent visibleComponent = null;

	public FlipPanel() {
		setLayout(layout);
	}

	public void removeComponent(JComponent c) {
		components.remove(c);
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
	
	public void setBounds(int x, int y, int width, int height){
		super.setBounds(x, y, width, height);
		for (int i = 0; i < components.size(); i++) {
			components.get(i).setSize(width, height);
//			components.get(i).setBounds(x, y, width, height);
		}		
	}
	
	public void setDirection(String direction){
		
		if("up".equals(direction)){
			setDirection(PerspectiveTransform.FLIP_UP);
			System.err.println("Direction set: " + direction);
		}
		if("down".equals(direction)){
			setDirection(PerspectiveTransform.FLIP_DOWN);
			System.err.println("Direction set: " + direction);
		}
		if("left".equals(direction)){
			setDirection(PerspectiveTransform.FLIP_LEFT);
			System.err.println("Direction set: " + direction);
		}
		if("right".equals(direction)){
			setDirection(PerspectiveTransform.FLIP_RIGHT);
			System.err.println("Direction set: " + direction);
		}
	}
	
	public void setDirection(int direction){
		this.direction = direction;
	}

	public void flipToComponent(JComponent next, int direction) {
		((RootPaneContainer) getTopLevelAncestor()).getRootPane().setGlassPane(animationPanel);
		getRootPane().getLayeredPane().add(animationPanel, 0);

		next.setBounds(getVisibleComponent().getBounds());
		animationPanel.setComponents(getVisibleComponent(), next);
		animationPanel.setVisible(true);
		animationPanel.setDirection(direction);
		add(animationPanel, BorderLayout.CENTER);
		animationPanel.flip();
		visibleComponent = next;
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
			animationPanel.setDirection(direction);
			add(animationPanel, BorderLayout.CENTER);
			animationPanel.flip();
			visibleComponent = next;
		}
	}

	public void setSpeed(int halftimeMillis) {
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
			animationPanel.setDirection(direction);
			// add(next, BorderLayout.CENTER);
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
		return visibleComponent;
	}

}
