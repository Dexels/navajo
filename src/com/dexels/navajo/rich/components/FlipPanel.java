package com.dexels.navajo.rich.components;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class FlipPanel extends JPanel {
	private boolean isDragging = false;
	private Point previousPoint = null;
	private PerspectiveImagePanel animationPanel = new PerspectiveImagePanel(this);
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

		c.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
				if (isDragging) {

					Point current = e.getPoint();
					int dx = current.x - previousPoint.x;
					int dy = current.y - previousPoint.y;
					setLocation(getX() + dx, getY() + dy);

				}
			}

			public void mouseMoved(MouseEvent e) {

			}

		});

		c.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				previousPoint = e.getPoint();
				isDragging = true;
			}

			public void mouseReleased(MouseEvent e) {
				previousPoint = null;
				isDragging = false;
			}

		});

	}

	public void flipToComponent(JComponent next, boolean direction) {
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
			animationPanel.setDirection(false);
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
			animationPanel.setDirection(true);
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
