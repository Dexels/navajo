package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Scrollable;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.components.swingimpl.parsers.TipiGradientPaint;
import com.dexels.navajo.tipi.swingimpl.dnd.TipiDndCapable;
import com.dexels.navajo.tipi.swingimpl.dnd.TipiDndManager;

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
public class TipiSwingPanel extends JPanel implements Scrollable, TipiDndCapable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7367347934095843177L;
	private static final String ALIGNMENT = "alignment";
	private static final String PAINT = "paint";
	private TipiGradientPaint paint = null;

	private ImageIcon myImage = null;
	private final String NORTH = "north";
	private final String EAST = "east";
	private final String SOUTH = "south";
	private final String WEST = "west";
	private final String CENTER = "center";
	private final String NORTHEAST = "northeast";
	private final String SOUTHEAST = "southeast";
	private final String NORTHWEST = "northwest";
	private final String SOUTHWEST = "southwest";

	private String alignment = CENTER;
	private TipiDndManager myDndManager;

	// private String title = null;

	public TipiSwingPanel(TipiComponent source) {
		// setBackground(new Color(0.0f,0.8f,0.0f,0.2f));
		myDndManager = new TipiDndManager(this, source);
	}

	public void setAlignment(String al) {
		String old = this.alignment;
		alignment = al;
		repaint();
		firePropertyChange(ALIGNMENT, old, al);
	}

	public TipiGradientPaint getPaint() {
		return paint;
	}

	public void setPaint(TipiGradientPaint paint) {
		TipiGradientPaint old = this.paint;
		this.paint = paint;
		firePropertyChange(PAINT, old, paint);
	}

	private Dimension checkMax(Dimension preferredSize) {
		Dimension maximumSize = getMaximumSize();
		if (maximumSize == null) {
			return preferredSize;
		}
		return new Dimension(Math.min(preferredSize.width, maximumSize.width),
				Math.min(preferredSize.height, maximumSize.height));
	}

	private Dimension checkMin(Dimension preferredSize) {
		Dimension minimumSize = getMinimumSize();
		if (minimumSize == null) {
			return preferredSize;
		}
		return new Dimension(Math.max(preferredSize.width, minimumSize.width),
				Math.max(preferredSize.height, minimumSize.height));
	}

	public Dimension checkMaxMin(Dimension d) {
		return checkMin(checkMax(d));
	}

	@Override
	public Dimension getPreferredSize() {
		return checkMaxMin(super.getPreferredSize());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (paint != null) {
			paint.setBounds(this.getBounds());
			Paint p = paint.getPaint();
			Graphics2D g2 = (Graphics2D) g;
			Paint oldPaint = g2.getPaint();
			g2.setPaint(p);
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.setPaint(oldPaint);
		}

		if (myImage != null) {
			if (NORTHWEST.equals(alignment)) {
				g.drawImage(myImage.getImage(), 0, 0, this);
			}
			if (NORTH.equals(alignment)) {
				g.drawImage(myImage.getImage(),
						((getWidth() / 2) - (myImage.getIconWidth() / 2)), 0,
						this);
			}
			if (NORTHEAST.equals(alignment)) {
				g.drawImage(myImage.getImage(),
						getWidth() - myImage.getIconWidth(), 0, this);
			}
			if (WEST.equals(alignment)) {
				g.drawImage(myImage.getImage(), 0,
						((getHeight() / 2) - (myImage.getIconHeight() / 2)),
						this);
			}
			if (CENTER.equals(alignment)) {
				g.drawImage(myImage.getImage(),
						((getWidth() / 2) - (myImage.getIconWidth() / 2)),
						((getHeight() / 2) - (myImage.getIconHeight() / 2)),
						this);
			}
			if (EAST.equals(alignment)) {
				g.drawImage(myImage.getImage(),
						getWidth() - myImage.getIconWidth(),
						((getHeight() / 2) - (myImage.getIconHeight() / 2)),
						this);
			}
			if (SOUTHWEST.equals(alignment)) {
				g.drawImage(myImage.getImage(), 0,
						getHeight() - myImage.getIconHeight(), this);
			}
			if (SOUTH.equals(alignment)) {
				g.drawImage(myImage.getImage(),
						((getWidth() / 2) - (myImage.getIconWidth() / 2)),
						getHeight() - myImage.getIconHeight(), this);
			}
			if (SOUTHEAST.equals(alignment)) {
				g.drawImage(myImage.getImage(),
						getWidth() - myImage.getIconWidth(), getHeight()
								- myImage.getIconHeight(), this);
			}
		}
	}

	public void setImageUrl(URL u) {

		myImage = new ImageIcon(u);
	}

	public String getAlignment() {
		return alignment;
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
		return 40;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
		return 20;
	}

	
	@Override
	public TipiDndManager getDndManager() {
		return myDndManager;
	}


}
