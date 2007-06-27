package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

/**
 * An extension of JDesktopPane that supports often used functionality. This
 * class also handles setting scroll bars for when windows move too far to the
 * left or bottom, providing the JExtendedDesktopPane is in a ScrollPane.
 */
public class JExtendedDesktopPane extends JDesktopPane {

	private static int FRAME_OFFSET = 20;
	private JExtendedDesktopManager manager;

	public JExtendedDesktopPane() {
		super();
		manager = new JExtendedDesktopManager(this);
		setDesktopManager(manager);
	}

	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
		checkDesktopSize();
	}

	private boolean smallerThen(Point val, Point then) {
		return ((val.x + 25) * (val.y + 25)) < ((then.x + 25) * (then.y + 25));
	}

	private Point getMinimumLocation() {
		JInternalFrame[] allFrames = getAllFrames();

		Point minimum;

		if (allFrames.length == 0) {
			minimum = new Point(0, 0);
		} else {
			minimum = allFrames[0].getLocation();

			for (int i = allFrames.length - 1; i >= 1; i--) {
				if (allFrames[i].getClientProperty("JInternalFrame.isPalette") != Boolean.TRUE) {
					Point loc = allFrames[i].getLocation();

					if (loc.x > 0 && loc.y > 0 && smallerThen(loc, minimum)) {
						minimum = loc;
					}
				}
			}
		}

		if (minimum.y > (getBounds().height / 2)) {
			minimum.y = 0;
		}

		if (minimum.x > (getBounds().width / 2)) {
			minimum.x = 0;
		}

		return minimum;
	}

	private void setLocation(JInternalFrame frame) {
		Point loc = frame.getLocation();

		while (conflict(frame, loc)) {
			loc.translate(FRAME_OFFSET, FRAME_OFFSET);
		}

		frame.setLocation(loc);
	}

	private boolean conflict(JInternalFrame frame, Point newloc) {
		JInternalFrame[] allFrames = getAllFrames();

		for (int i = allFrames.length - 1; i >= 0; i--) {
			if (allFrames[i] != frame) {
				Point loc = allFrames[i].getLocation();

				if (loc.x == newloc.x && loc.y == newloc.y) {
					return true;
				}
			}
		}

		return false;
	}

	public Component add(JInternalFrame frame) {

		JInternalFrame[] array = getAllFrames();
		Point p;
		int w;
		int h;

		Component retval = super.add(frame);
		checkDesktopSize();

		if (frame.getX() == 0 && frame.getY() == 0) {
			p = getMinimumLocation();
			p.translate(FRAME_OFFSET, FRAME_OFFSET);
			frame.setLocation(p.x, p.y);
		}

		setLocation(frame);

		moveToFront(frame);
		frame.setVisible(true);

		try {
			frame.setSelected(true);
		} catch (PropertyVetoException e) {
			frame.toBack();
		}

		return retval;
	}

	public void remove(Component c) {
		super.remove(c);
		checkDesktopSize();
	}

	/**
	 * Cascade all internal frames
	 */
	public void cascade() {
		int x = 0;
		int y = 0;
		JInternalFrame allFrames[] = getAllFrames();

		manager.setNormalSize();

		int frameHeight = (getBounds().height - 5) - allFrames.length * FRAME_OFFSET;
		int frameWidth = (getBounds().width - 5) - allFrames.length * FRAME_OFFSET;

		for (int i = allFrames.length - 1; i >= 0; i--) {
			allFrames[i].setSize(frameWidth, frameHeight);
			allFrames[i].setLocation(x, y);
			x = x + FRAME_OFFSET;
			y = y + FRAME_OFFSET;
		}
	}

	/**
	 * Tile all internal frames
	 */
	public void tile() {
		java.awt.Component allFrames[] = getAllFrames();
		manager.setNormalSize();
		int frameHeight = getBounds().height / allFrames.length;
		int y = 0;

		for (int i = 0; i < allFrames.length; i++) {
			allFrames[i].setSize(getBounds().width, frameHeight);
			allFrames[i].setLocation(0, y);
			y = y + frameHeight;
		}
	}

	/**
	 * Sets all component size properties ( maximum, minimum, preferred) to the
	 * given dimension.
	 */
	public void setAllSize(Dimension d) {
		setMinimumSize(d);
		setMaximumSize(d);
		setPreferredSize(d);
	}

	/**
	 * Sets all component size properties ( maximum, minimum, preferred) to the
	 * given width and height.
	 */
	public void setAllSize(int width, int height) {
		setAllSize(new Dimension(width, height));
	}

	private void checkDesktopSize() {
		if (getParent() != null && isVisible()) {
			manager.resizeDesktop();
		}
	}
}

/**
 * Private class used to replace the standard DesktopManager for JDesktopPane.
 * Used to provide scrollbar functionality.
 */
class JExtendedDesktopManager extends DefaultDesktopManager {

	private JExtendedDesktopPane desktop;

	public JExtendedDesktopManager(JExtendedDesktopPane desktop) {
		this.desktop = desktop;
	}

	public void endResizingFrame(JComponent f) {
		super.endResizingFrame(f);
		resizeDesktop();
	}

	public void endDraggingFrame(JComponent f) {
		super.endDraggingFrame(f);
		resizeDesktop();
	}

	public void setNormalSize() {
		JScrollPane scrollPane = getScrollPane();
		int x = 0;
		int y = 0;
		Insets scrollInsets = getScrollPaneInsets();

		if (scrollPane != null) {
			Dimension d = scrollPane.getVisibleRect().getSize();

			if (scrollPane.getBorder() != null) {
				d.setSize(d.getWidth() - scrollInsets.left - scrollInsets.right, d.getHeight() - scrollInsets.top - scrollInsets.bottom);
			}

			d.setSize(d.getWidth() - 20, d.getHeight() - 20);
			desktop.setAllSize(x, y);
			scrollPane.invalidate();
			scrollPane.validate();
		}
	}

	private Insets getScrollPaneInsets() {
		JScrollPane scrollPane = getScrollPane();

		if (scrollPane == null) {
			return new Insets(0, 0, 0, 0);
		} else {
			return getScrollPane().getBorder().getBorderInsets(scrollPane);
		}
	}

	private JScrollPane getScrollPane() {
		if (desktop.getParent() instanceof JViewport) {
			JViewport viewPort = (JViewport) desktop.getParent();

			if (viewPort.getParent() instanceof JScrollPane) {
				return (JScrollPane) viewPort.getParent();
			}
		}

		return null;
	}

	protected void resizeDesktop() {
		int x = 0;
		int y = 0;
		JScrollPane scrollPane = getScrollPane();
		Insets scrollInsets = getScrollPaneInsets();

		if (scrollPane != null) {
			JInternalFrame allFrames[] = desktop.getAllFrames();

			for (int i = 0; i < allFrames.length; i++) {
				if (allFrames[i].getX() + allFrames[i].getWidth() > x) {
					x = allFrames[i].getX() + allFrames[i].getWidth();
				}

				if (allFrames[i].getY() + allFrames[i].getHeight() > y) {
					y = allFrames[i].getY() + allFrames[i].getHeight();
				}
			}

			Dimension d = scrollPane.getVisibleRect().getSize();

			if (scrollPane.getBorder() != null) {
				d.setSize(d.getWidth() - scrollInsets.left - scrollInsets.right, d.getHeight() - scrollInsets.top - scrollInsets.bottom);
			}

			if (x <= d.getWidth()) {
				x = ((int) d.getWidth()) - 20;
			}

			if (y <= d.getHeight()) {
				y = ((int) d.getHeight()) - 20;
			}

			desktop.setAllSize(x, y);
			scrollPane.invalidate();
			scrollPane.validate();
		}
	}
}
