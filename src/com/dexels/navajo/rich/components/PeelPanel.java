package com.dexels.navajo.rich.components;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

public class PeelPanel extends JPanel {
	private double peel = 0.0;

	public PeelPanel() {
		setBackground(Color.white);
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				peel();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
//				mouseLoc = e.getPoint();
				repaint();
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		// paintChildren(g2);
		Rectangle bounds = getBounds();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Polygon p = new Polygon(new int[] { 0, 0, (int) (2 * peel) - 2 }, new int[] { 0, (int) (2 * peel) - 1, 0 }, 3);
		GradientPaint gpb = new GradientPaint(0, 0, Color.white, (float) peel - 1, (float) peel - 1, Color.black);
		g2.setPaint(gpb);
		g2.fill(p);

		p = new Polygon(new int[] { 0, 0, (int) (2 * peel) }, new int[] { 0, (int) (2 * peel), 0 }, 3);
		QuadCurve2D c1 = new QuadCurve2D.Double(bounds.x, bounds.y + 2 * peel, bounds.x, bounds.y + peel, bounds.x + peel, bounds.y + peel);
		QuadCurve2D c2 = new QuadCurve2D.Double(bounds.x + peel, bounds.y + peel, bounds.x + peel, bounds.y, bounds.x + 2 * peel, bounds.y);
		Line2D l1 = new Line2D.Double(peel / 4.0, peel * 1.25, peel * 1.25, peel / 4.0);

		Area leaf = new Area(c1);
		Area ac2 = new Area(c2);
		Area line = new Area(l1);

		leaf.add(ac2);
		leaf.add(line);

		GradientPaint gp = new GradientPaint((float) (peel / 2), (float) (peel / 2), Color.darkGray, (float) peel, (float) peel,
				Color.white);
		g2.setPaint(gp);
		g2.fill(leaf);

		Area triaMax = new Area(p); // total
		Rectangle2D r1 = new Rectangle2D.Double(0, 0, 2 * peel, peel / 4);
		Rectangle2D r2 = new Rectangle2D.Double(0, 0, peel / 4, 2 * peel);

		triaMax.subtract(new Area(r1));
		triaMax.subtract(new Area(r2));

		Polygon toEdge = new Polygon(new int[] { 0, 0, (int) (1.5 * peel) }, new int[] { 0, (int) (1.5 * peel), 0 }, 3);
		Area edge = new Area(toEdge);
		triaMax.subtract(edge);
		triaMax.subtract(leaf);

		GradientPaint gpeel = new GradientPaint((float) (peel * 0.75), (float) (peel * 0.75), Color.lightGray, (float) peel, (float) peel,
				Color.white);
		g2.setPaint(gpeel);
		g2.fill(triaMax);

		g2.setStroke(new BasicStroke(1.0f));
		g2.setColor(Color.darkGray);
		g2.draw(c1);
		g2.draw(c2);
		g2.draw(l1);

		// GradientPaint gppaper = new
		// GradientPaint((float)(peel),(float)(peel),Color.lightGray,
		// bounds.width, bounds.height, Color.white);
		Area rect = new Area(bounds);
		Area tria = new Area(p);
		rect.subtract(tria);
		g2.setColor(Color.white);
		// g2.setPaint(gppaper);
		g2.fill(rect);

		// if(mouseLoc != null){
		// g2.setColor(Color.black);
		// g2.drawString("Mouse at " + mouseLoc.x + ", "+ mouseLoc.y +
		// ", peel: " + peel, 100, 100);
		// }
		g2.dispose();
	}

	private void peel() {
		peel += 20.0;
		repaint();
	}

//	private void setPeel(double d) {
//		repaint();
//	}

}
