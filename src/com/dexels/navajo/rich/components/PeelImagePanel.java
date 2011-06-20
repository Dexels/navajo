package com.dexels.navajo.rich.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class PeelImagePanel extends JPanel {

	private static final long serialVersionUID = -3731847583252859896L;
	private Point peelLoc = new Point(1, 1);
	private Point animationOrigin;
	private Point flipOverTarget;
	private Point animTarget;
	private BufferedImage back, currentFront, currentBack;
	private Color backSideColor = null;
	BufferedImage backSideImage = null;
	private Rectangle prevBounds;
	private boolean isDragging = false;
	public boolean isAnimating = false;
	private boolean flipOver = false;
	private boolean isPeeling = false;
	private double mouseDistance = 0.0;
	private double max_mouseDistance = 100.0;
	private int dragDir = 0;
	private Point locFrom = new Point(0, 0);
	public double peelFactor = 0.0;
	private ArrayList<PeelListener> listeners = new ArrayList<PeelListener>();

	BufferedImage clippedImage;
//	private JComponent c2;

	// Start with right bottom
	public PeelImagePanel() {

		addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
				peelLoc = e.getPoint();
				repaint();
			}

			public void mouseMoved(MouseEvent e) {
				if (isAnimating) {
					return;
				}
				Point cornerLocation = new Point(0, 0);
				Rectangle bounds = getBounds();
				if (e.getPoint().y < bounds.height / 2) {
					cornerLocation.y = bounds.y;
				} else {
					cornerLocation.y = bounds.y + bounds.height;
				}
				if (e.getPoint().x < bounds.width / 2) {
					cornerLocation.x = bounds.x;
				} else {
					cornerLocation.x = bounds.x + bounds.width;
				}

				double dx = cornerLocation.x - e.getPoint().x;
				double dy = cornerLocation.y - e.getPoint().y;

				mouseDistance = Math.sqrt((Math.pow(dx, 2.0) + Math.pow(dy, 2.0)));
				if (mouseDistance < max_mouseDistance) {
					startPeel(e);
				} else if (peelLoc.x > 1 && peelLoc.y > 1) {
					animateBack();
				}
			}
		});

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				isDragging = true;
			}

			public void mouseReleased(MouseEvent e) {
				isDragging = false;
				if (!isAnimating) {
					animateBack();
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				if (isDragging) {
					isDragging = false;
					peelLoc.x = 1;
					peelLoc.y = 1;
					// repaint();
				}
			}
			
		});

	

		setBackground(Color.white);
	}
	
	public void addPeelListener(PeelListener l){
		if(!listeners.contains(l)){
			listeners.add(l);
		}
	}
	
	private void firePeelComplete(boolean full_peel){
		for(int i=0;i<listeners.size();i++){
			listeners.get(i).peelStopped(full_peel);
		}
	}
	
	public Point getPeelLoc(){
		return peelLoc;
	}
	
	public void setBackSideColor(Color c){
		this.backSideColor = c;
	}
	
	public void setBackSideImage(BufferedImage img){
		this.backSideImage = img;
	}		
	
	public void setComponents(JComponent c1, JComponent c2) {
		clippedImage = null;

		getGraphicsConfiguration().createCompatibleImage(c1.getWidth(), c1.getHeight());
		BufferedImage b1 = getGraphicsConfiguration().createCompatibleImage(c1.getWidth(), c1.getHeight(), Transparency.TRANSLUCENT);
		BufferedImage b2 = getGraphicsConfiguration().createCompatibleImage(c2.getWidth(), c2.getHeight(), Transparency.TRANSLUCENT);
		Graphics2D b1g = b1.createGraphics();
		Graphics2D b2g = b2.createGraphics();		
		
		
		back = getGraphicsConfiguration().createCompatibleImage(c2.getWidth(), c2.getHeight(), Transparency.TRANSLUCENT);
		Graphics2D backG = back.createGraphics();
		if(backSideColor != null){
			backG.setColor(backSideColor);
		}else{
			backG.setColor(Color.black);
		}
		backG.fillRect(0, 0, c2.getWidth(), c2.getHeight());
		if(backSideImage != null){
			int x = c2.getWidth()/2 - backSideImage.getWidth()/2;
			int y = c2.getHeight()/2 - backSideImage.getHeight()/2;
			backG.drawImage(backSideImage, x, y, backSideImage.getWidth(), backSideImage.getHeight(), null);
		}		
		
		c1.print(b1g);
		b1g.dispose();
		currentFront = b1;

		c1.setVisible(false);
		c2.setVisible(true);
		c2.print(b2g);
		b2g.dispose();
		currentBack = b2;

		c1.setVisible(true);
		c2.setVisible(false);
	}

	private void startPeel(MouseEvent e) {
		peelLoc = e.getPoint();
		isPeeling = true;
		Rectangle bounds = getBounds();
		if (!isDragging) {
			if (peelLoc.y < bounds.height / 2) {
				locFrom.y = bounds.y;
			} else {
				locFrom.y = bounds.y + bounds.height;
			}
			if (peelLoc.x < bounds.width / 2) {
				locFrom.x = bounds.x;
			} else {
				locFrom.x = bounds.x + bounds.width;
			}
			determineDragDir(locFrom);
			peelFactor = 0.0;
		}
		repaint();
	}

	private void determineDragDir(Point loc) {
		Rectangle bounds = getBounds();
		if (loc.x == 0 && loc.y == 0) {
			dragDir = 0;
		} else if (loc.x == bounds.width && loc.y == 0) {
			dragDir = 1;
		} else if (loc.x == bounds.width && loc.y == bounds.height) {
			dragDir = 2;
		} else if (loc.x == 0 && loc.y == bounds.height) {
			dragDir = 3;
		}
	}


	public void paint(Graphics g) {

		Rectangle bnds = getBounds();

		// Resize container
		if (!bnds.equals(prevBounds) || clippedImage == null) {
			prevBounds = bnds;
			clippedImage = new BufferedImage(bnds.width, bnds.height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D clippedG = clippedImage.createGraphics();
			clippedG.drawImage(currentFront, 0, 0, currentFront.getWidth(), currentFront.getHeight(), null);
			peelLoc.x = 1;
			peelLoc.y = 1;
			clippedG.dispose();
		}

		Graphics2D g2 = (Graphics2D) g.create();
		if (peelLoc != null) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			double dx = locFrom.x - peelLoc.x;
			double dy = locFrom.y - peelLoc.y;
			double rc = dy / dx;
			// Find B
//			double b = -rc * peelLoc.x + peelLoc.y;
			// y = rc*x + b

			double dpx = peelLoc.x + (locFrom.x - peelLoc.x) / 2.0;
			double dpy = peelLoc.y + (locFrom.y - peelLoc.y) / 2.0;

			double b_orth = (1.0 / rc) * dpx + dpy;

			// if we know Y what is x?
			int cx = (int) ((locFrom.y - b_orth) / (-1.0 / rc));
			int o2 = (int) ((-1.0 / rc) * locFrom.x + b_orth);

			Point p1 = new Point(cx, locFrom.y);
			Point p2 = new Point(locFrom.x, o2);

			// Now determine our shape(s)

			Polygon p = new Polygon(new int[] { peelLoc.x, p1.x, p2.x }, new int[] { peelLoc.y, p1.y, p2.y }, 3);
			Polygon q = new Polygon(new int[] { p2.x, p1.x, locFrom.x }, new int[] { p2.y, p1.y, locFrom.y }, 3);

			// Main Container
			g2.drawImage(clippedImage, 0, 0, bnds.width, bnds.height, null);

			if (((int) dpx != peelLoc.x) && ((int) dpy != peelLoc.y)) {
				LinearGradientPaint gpBounds = new LinearGradientPaint(peelLoc.x, peelLoc.y, (float) dpx, (float) dpy, new float[] { 0.0f, 1.0f }, new Color[] { new Color(1.0f, 1.0f, 1.0f, 0.0f), new Color(0.0f, 0.0f, 0.0f, 0.5f) });
				g2.setPaint(gpBounds);
				g2.fill(bnds);
			}

			// Peeled
			// Fill with rotated bgimage if available

			if (isPeeling) {

				double delta = dx / dy; // tan(theta) = delta
				double theta = Math.atan(delta);

				// Find out translation
				int tx = 0;
				int ty = 0;
				Point rotator = new Point(0, 0);

				switch (dragDir) {
				case 0:
					tx = -bnds.width + peelLoc.x;
					ty = peelLoc.y;
					rotator.x = bnds.width;
					break;
				case 1:
					tx = peelLoc.x;
					ty = peelLoc.y;
					break;
				case 2:
					tx = peelLoc.x;
					ty = peelLoc.y - bnds.height;
					rotator.x = 0;
					rotator.y = bnds.height;
					break;
				case 3:
					tx = -bnds.width + peelLoc.x;
					ty = peelLoc.y - bnds.height;
					rotator.x = bnds.width;
					rotator.y = bnds.height;
					break;
				}

				Shape clip = g2.getClip();
				g2.setClip(p);
				g2.translate(tx, ty);
				g2.rotate(-2 * theta + Math.PI, rotator.x, rotator.y);

				g2.drawImage(back, 0, 0, back.getWidth(), back.getHeight(), null);

				g2.rotate(2 * theta + Math.PI, rotator.x, rotator.y);
				g2.translate(-tx, -ty);
				g2.setClip(clip);

				if (((int) dpx != peelLoc.x) && ((int) dpy != peelLoc.y)) {
					LinearGradientPaint gpPeel = new LinearGradientPaint(peelLoc.x, peelLoc.y, (float) dpx, (float) dpy, new float[] { 0.0f, 0.7f, 0.9f, 1.0f }, new Color[] { new Color(0.0f, 0.0f, 0.0f, 0.1f), new Color(0.0f, 0.0f, 0.0f, 0.1f), new Color(0.0f, 0.0f, 0.0f, 0.2f),
							new Color(1.0f, 1.0f, 1.0f, 0.5f) });
					g2.setPaint(gpPeel);
					g2.fill(p);
				}

				// New page
				g2.setColor(Color.black);
				g2.setClip(q);

				g2.drawImage(currentBack, 0, 0, currentBack.getWidth(), currentBack.getHeight(), null);
			}
			g2.dispose();
		}
	}

	public void animateBack() {
		animationOrigin = new Point(peelLoc.x, peelLoc.y);
		determineFlipOver(animationOrigin);
		int duration = 250;
		if (flipOver) {
			determineFlipOverTarget(animationOrigin);
			animTarget = new Point(flipOverTarget.x, flipOverTarget.y);
			duration = 500;
		} else {
			animTarget = new Point(locFrom.x, locFrom.y);
		}
		isAnimating = true;
		Animator anim = PropertySetter.createAnimator(duration, PeelImagePanel.this, "peelFactor", 0.0, 1.0);
		anim.addTarget(new TimingTargetAdapter() {
			public void end() {
				isAnimating = false;
				isPeeling = false;
				if (flipOver) {
					try {
						repaint();
						firePeelComplete(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					firePeelComplete(false);
				}
			}
		});
		anim.setAcceleration(0.4f);
		anim.setDeceleration(0.2f);
		anim.start();
	}

	public void setPeelFactor(double d) {
		this.peelFactor = d;
		double x = (animTarget.x - animationOrigin.x) * d + animationOrigin.x;
		double y = (animTarget.y - animationOrigin.y) * d + animationOrigin.y;
		peelLoc.x = (int) x;
		peelLoc.y = (int) y;
		repaint();
	}

	private void determineFlipOver(Point origin) {
		Rectangle bounds = getBounds();
		flipOver = false;
		if (origin.x < bounds.width / 2 && (dragDir == 1 || dragDir == 2)) {
			flipOver = true;
		}
		if (origin.x > bounds.width / 2 && (dragDir == 0 || dragDir == 3)) {
			flipOver = true;
		}
		if (origin.y < bounds.height / 2 && (dragDir == 2 || dragDir == 3)) {
			flipOver = true;
		}
		if (origin.y > bounds.height / 2 && (dragDir == 0 || dragDir == 1)) {
			flipOver = true;
		}
	}

	private void determineFlipOverTarget(Point origin) {
		// determine quadrant of origin
		Rectangle bounds = getBounds();
		switch (dragDir) {
		case 0:
			if (origin.x > bounds.width / 2) { // FlipTarget is top-right
				flipOverTarget = new Point(2 * bounds.width, 0);
			} else { // FlipTarget is bottom-left
				flipOverTarget = new Point(0, 2 * bounds.height);
			}
			break;
		case 1:
			if (origin.x < bounds.width / 2) { // FlipTarget is top-left
				flipOverTarget = new Point(-bounds.width, 0);
			} else { // FlipTarget is bottom-right
				flipOverTarget = new Point(bounds.width, 2 * bounds.height);
			}
			break;
		case 2:
			if (origin.x < bounds.width / 2) { // FlipTarget is bottom-left
				flipOverTarget = new Point(-bounds.width, bounds.height);
			} else { // FlipTarget is top-right
				flipOverTarget = new Point(bounds.width, -bounds.height);
			}
			break;
		case 3:
			if (origin.x > bounds.width / 2) { // FlipTarget is bottom-right
				flipOverTarget = new Point(2 * bounds.width, bounds.height);
			} else { // FlipTarget is top-left
				flipOverTarget = new Point(0, -bounds.height);
			}
			break;
		}
	}
}
