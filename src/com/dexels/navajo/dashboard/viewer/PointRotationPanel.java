package com.dexels.navajo.dashboard.viewer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class PointRotationPanel extends JComponent {
	private int pointCount = 10;
	private double x_scale = 1.0;
	private double y_scale = 0.6;
	double rad_degree = Math.PI / 180.0;
	
	public PointRotationPanel(int pointCount){
		this.pointCount = pointCount;		
	}
	
	/*
	 *  Points are calculated on the unity circle r=1
	 *  
	 *               1/2 PI
	 *                 |
	 *                 |
	 *                 |    r=1
	 *                 |
	 *   PI -----------0----------- 0 or 2PI
	 *                 |
	 *                 |
	 *                 |
	 *                 |
	 *              1 1/2 PI
	 */
	
	public ArrayList<Point2D.Double> getPoints(double offset){
		
		// Calculate the difference in degrees and radians between the points
		double delta = 360.0 / pointCount;
		double delta_radians = delta * rad_degree;
		double alignment = 90 * rad_degree; // we center at 1 1/2 PI
		
		double x = Math.cos(delta_radians);
		double y = Math.sin(delta_radians);
		
		ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
		
		for(int i=0;i<pointCount;i++){
			Point2D.Double p = new Point2D.Double();
			p.x = x_scale * Math.cos(alignment + offset + (i * delta_radians));
			p.y = y_scale * Math.sin(alignment + offset + (i * delta_radians));
			points.add(p);
		}
		return points;
		
	}
	
	public void setPointCount(int count){
		this.pointCount = count;
	}
	
	public static void main(String[] args){
		new PointRotationPanel(5);
	}
	
	public void setScale(float scale_x, float scale_y){
		this.x_scale = scale_x;
		this.y_scale = scale_y;
	}
	
}
