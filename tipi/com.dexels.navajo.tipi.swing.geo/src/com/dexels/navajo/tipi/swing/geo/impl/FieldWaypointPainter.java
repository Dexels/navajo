package com.dexels.navajo.tipi.swing.geo.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

public class FieldWaypointPainter implements Painter<JXMapViewer> {
	private GeoPosition pos;
	private double bearing;
	private boolean antiAlias = true;

	public FieldWaypointPainter(GeoPosition pos, double bearing) {
		this.pos = pos;
		this.bearing = bearing;
	}

	@Override
	public void paint(Graphics2D g2d, JXMapViewer map, int w, int h) {

		g2d = (Graphics2D) g2d.create();
		// convert from viewport to world bitmap
		Rectangle rect = map.getViewportBounds();
		g2d.translate(-rect.x, -rect.y);
		if (antiAlias)
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// do the drawing
		int alpha = 80; // 50% transparent
		Color myColour = new Color(36, 208, 36, alpha);
		g2d.setColor(myColour);

		Point2D pt = map.getTileFactory().geoToPixel(pos, map.getZoom());
		AffineTransform orig = g2d.getTransform();
		
		int zoom = map.getZoom();
		Rectangle rect2 = new Rectangle(getX((int) pt.getX(), zoom), 
										getY((int)pt.getY(), zoom), 
										getWidth(zoom), 
										getLength(zoom));
		g2d.rotate(Math.toRadians(bearing),(int) pt.getX(), (int) pt.getY());
		
		g2d.draw(rect2);
		g2d.fill(rect2);

		g2d.setTransform(orig);
		g2d.dispose();

	}
	
	private int getX(int orig, int zoom) {
		if (zoom == 0) {
			return orig - 50;
		}
			return orig - (50 / (zoom*2));
		
//		if (zoom == 0) {
//			return orig - 60;
//		} else if (zoom==1) {
//			return orig -30;
//		} else if (zoom==2) {
//			return orig - 15;
//		}
//		return orig;
	}
	
	private int getY(int orig, int zoom) {
		if (zoom == 0) {
			return orig - 70;
		} 
		return orig - (70 / (zoom * 2));
		
		
		
	}
	
	private int getWidth(int zoom) {
		if (zoom == 0) {
			return 90;
		} 
		return (90 / (zoom * 2));
		
		
	}
	private int getLength(int zoom) {
		if (zoom == 0) {
			return 140;
		} 
		return (140 / (zoom * 2));
	}

}
