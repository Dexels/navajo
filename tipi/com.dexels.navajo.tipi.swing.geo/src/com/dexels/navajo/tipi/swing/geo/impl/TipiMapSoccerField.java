package com.dexels.navajo.tipi.swing.geo.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.painter.Painter;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;

public class TipiMapSoccerField implements Painter<JXMapViewer> {
    private static final int FIELD_TRANSPARANCY = 235;
    
    private final double bearing;
    private boolean antiAlias = true;
    private final GeoPosition pos;
    private String label;
    private String fieldUse;
    private Boolean isHalfField;

    public TipiMapSoccerField(Message field) {
        double lonF;
        double latF;

        if (field.getProperty("Longitude").getType().equals(Property.STRING_PROPERTY)) {
            lonF = Double.parseDouble((String) field.getProperty("Longitude").getTypedValue());
        } else {
            lonF = (Double) field.getProperty("Longitude").getTypedValue();
        }
        if (field.getProperty("Latitude").getType().equals(Property.STRING_PROPERTY)) {
            latF = Double.parseDouble((String) field.getProperty("Latitude").getTypedValue());
        } else {
            latF = (Double) field.getProperty("Latitude").getTypedValue();
        }
        if (field.getProperty("Bearing").getType().equals(Property.STRING_PROPERTY)) {
            bearing = Double.parseDouble((String) field.getProperty("Bearing").getTypedValue());
        } else {
            bearing = (Double) field.getProperty("Bearing").getTypedValue();
        }

        pos = new GeoPosition(latF, lonF);

        this.label = (String) field.getProperty("Name").getTypedValue();
        this.isHalfField = (Boolean) field.getProperty("IsHalfField").getTypedValue();
        this.fieldUse = field.getProperty("FieldUse").getSelected().getValue();

    }

    @Override
    public void paint(Graphics2D g2d, JXMapViewer map, int w, int h) {

        g2d = (Graphics2D) g2d.create();
        // convert from viewport to world bitmap
        Rectangle rect = map.getViewportBounds();
        g2d.translate(-rect.x, -rect.y);
        if (antiAlias)
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        drawFieldOverlay(g2d, map);
                
        //centerString(g2d, rect2, label, n);
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
//        drawCenteredString(label, rect2, g2d);

        g2d.dispose();

    }

    private void drawFieldOverlay(Graphics2D g2d, JXMapViewer map) {
        // do the drawing
        GeoPosition lefTopPos = travel(pos, getFieldHeight()/1.65, bearing-35);
        GeoPosition rightTopPos = travel(pos, getFieldHeight()/1.65, bearing+35);
        GeoPosition rightBottomPos = travel(pos, getFieldHeight()/1.65, bearing+215);
        GeoPosition leftBottomPos = travel(pos, getFieldHeight()/1.65, bearing-215);
        Point2D leftTop = map.getTileFactory().geoToPixel(lefTopPos, map.getZoom());
        Point2D rightTop =map.getTileFactory().geoToPixel(rightTopPos, map.getZoom());
        Point2D rightBottom =map.getTileFactory().geoToPixel(rightBottomPos, map.getZoom());
        Point2D leftBottom = map.getTileFactory().geoToPixel(leftBottomPos, map.getZoom());

        
      
        int xpoints[] = {(int)leftTop.getX(), (int)rightTop.getX(), (int)leftBottom.getX(), (int)rightBottom.getX()};
        int ypoints[] = {(int)leftTop.getY(), (int)rightTop.getY(), (int)leftBottom.getY(), (int)rightBottom.getY()};
        Polygon p = new Polygon(xpoints, ypoints, 4);
 
        Color myColour = getFieldColor();
        g2d.setColor(myColour);
        g2d.fillPolygon(p);
        g2d.setColor(Color.WHITE);
        g2d.drawPolygon(p);

        Point2D centerPoint = map.getTileFactory().geoToPixel(pos, map.getZoom());
        
//        Color oldColor = g2d.getColor();
//        g2d.setColor(Color.WHITE);
//        g2d.drawString(label, (int) centerPoint.getX(),(int) centerPoint.getY());
//        g2d.setColor(oldColor);
          
            
        drawCenteredString(label, p, g2d);
        // Draw border
//        g2d.setColor(new Color(255, 255, 255, 200));
//        g2d.setStroke(new BasicStroke(4));
//        g2d.drawRect((int)rect2.getX(), (int) rect2.getY(), rect2.width, rect2.height);
        
//        g2d.setTransform(orig);
        return;
    }


    public static GeoPosition travel(GeoPosition start, double distance, double initialBearing) {
        double bR = Math.toRadians(initialBearing);
        double lat1R = Math.toRadians(start.getLatitude());
        double lon1R = Math.toRadians(start.getLongitude());
        double dR = distance / (6372797.6D); // earth radius in meters

        double a = Math.sin(dR) * Math.cos(lat1R);
        double lat2 = Math.asin(Math.sin(lat1R) * Math.cos(dR) + a * Math.cos(bR));
        double lon2 = lon1R
                + Math.atan2(Math.sin(bR) * a, Math.cos(dR) - Math.sin(lat1R) * Math.sin(lat2));
        return new GeoPosition(Math.toDegrees(lat2), Math.toDegrees(lon2));
    }

    private double getFieldHeight(){
        return 105D;
    }

    private Color getFieldColor() {
        if (fieldUse.equals("TRAINING")) {
            return new Color(177, 177, 99, FIELD_TRANSPARANCY);
        }
        return new Color(36, 170, 36, FIELD_TRANSPARANCY);
    }

    private void drawCenteredString(String s, Polygon p, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (p.getBounds().width - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (p.getBounds().height- (fm.getAscent() + fm.getDescent())) / 2);
        Color oldColor = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString(s, p.getBounds().x + x, p.getBounds().y+y);
        g.setColor(oldColor);
      }
    
   
    private int getX(int orig, int zoom) {
        if (zoom == 0) {
            return orig - 230;
        }
        if (zoom < 3) {
            return orig - (215 / (zoom * 2));
        }
       
        return orig - (160 / (zoom * 2));
    }

    private int getY(int orig, int zoom) {
        if (zoom == 0) {
            return orig - 260;
        }
        if (zoom < 3) {
            return  orig - (300 / (zoom * 2));
        }
        return orig - (220 / (zoom * 2));
    }

    private int getWidth(int zoom) {
//        System.out.println("zoom " + zoom);
        if (zoom == 0) {
            return 400;
        }
        if (zoom < 3) {
            return  (400 / (zoom * 2));
        }
        if (zoom == 3) {
            return (330 / (zoom * 2));
        }
        return (330 / (zoom * 3));

    }

    private int getLength(int zoom) {
        if (zoom == 0) {
            return 560;
        }
        if (zoom < 3) {
            return (600 / (zoom * 2));
        }
        if (zoom == 3) {
            return (450 / (zoom * 2));
        }
        return (450 / (zoom * 3));
    }
}
