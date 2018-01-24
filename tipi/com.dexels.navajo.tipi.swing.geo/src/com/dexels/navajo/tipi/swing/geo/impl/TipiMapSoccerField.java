package com.dexels.navajo.tipi.swing.geo.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.painter.Painter;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;

public class TipiMapSoccerField implements Painter<JXMapViewer> {
    private static final int FIELD_TRANSPARANCY = 50;
    
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
        Point2D leftBottom =map.getTileFactory().geoToPixel(leftBottomPos, map.getZoom());

        int xpoints[] = {(int)leftTop.getX(), (int)rightTop.getX(), (int)leftBottom.getX(), (int)rightBottom.getX()};
        int ypoints[] = {(int)leftTop.getY(), (int)rightTop.getY(), (int)leftBottom.getY(), (int)rightBottom.getY()};
        

       
        Color myColour = getFieldColor();
        g2d.setColor(myColour);
        g2d.fillPolygon(xpoints, ypoints, 4);
        g2d.setColor(Color.WHITE);
        g2d.drawPolygon(xpoints, ypoints, 4);

        // Draw border
//        g2d.setColor(new Color(255, 255, 255, 200));
//        g2d.setStroke(new BasicStroke(4));
//        g2d.drawRect((int)rect2.getX(), (int) rect2.getY(), rect2.width, rect2.height);
        
//        g2d.setTransform(orig);
        return;
    }

    private Rectangle getFieldRectangle(Point2D pt, JXMapViewer map) {
        // https://gis.stackexchange.com/questions/2951/algorithm-for-offsetting-a-latitude-longitude-by-some-amount-of-meters

//        // Earth’s radius, sphere
//        double R = 6378137D;
//        
//        // We start in the middle of the field. The rectangle starts with coordinates
//        // of the upper-left corner. Thus we first calculate where that is
//        double dn = getFieldHeight() ;
//        double de = -getFieldWidth();
//
//        // Coordinate offsets in radians
//        double dLat = dn/R;
//        double dLon = de/(R*Math.cos(Math.PI * pos.getLatitude()/180));
//
//        // OffsetPosition, decimal degrees
//        double latO = pos.getLatitude() + dLat * 180/Math.PI - 0.00095;
//        double lonO = pos.getLongitude() + dLon * 180/Math.PI + 0.0001;
//        GeoPosition startPos = new GeoPosition(latO, lonO);
//        Point2D start = map.getTileFactory().geoToPixel(startPos, map.getZoom());
//
//        // Next, add the field length to see where we end up
//        dn = getFieldHeight();
//        de = 0;
//
//        // Coordinate offsets in radians
//        dLat = dn/R;
//        dLon = de/(R*Math.cos(Math.PI*startPos.getLatitude()/180));
//
//        // OffsetPosition, decimal degrees
//        double lat1 = startPos.getLatitude() + dLat * 180/Math.PI;
//        double lon1 = startPos.getLongitude() + dLon * 180/Math.PI ;
//
//        Point2D endHeight = map.getTileFactory().geoToPixel(new GeoPosition(lat1, lon1), map.getZoom());
//        
//        // Add width to start pos
//        dn = 0;
//        de = getFieldWidth();
//
//        // Coordinate offsets in radians
//        dLat = dn/R;
//        dLon = de/(R*Math.cos(Math.PI*startPos.getLatitude()/180));
//
//        // OffsetPosition, decimal degrees
//        double lat2 = startPos.getLatitude() + dLat * 180/Math.PI;
//        double lon2 = startPos.getLongitude() + dLon * 180/Math.PI ;
//
//        Point2D endWidth = map.getTileFactory().geoToPixel(new GeoPosition(lat2, lon2), map.getZoom());
////        
//        Double width = Math.abs(endWidth.getX() - start.getX());
//        Double height = Math.abs(endHeight.getY() - start.getY());
//        
//        return new Rectangle((int)start.getX(), (int) start.getY(),  width.intValue(), height.intValue());
        
        
        
        return new Rectangle();
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
    private double getFieldWidth(){
        return 68D;
    }

    private Color getFieldColor() {
        if (fieldUse.equals("TRAINING")) {
            return new Color(177, 177, 99, 125);
        }
        return new Color(36, 170, 36, 150);
    }

    private void drawCenteredString(String s, Rectangle r, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (r.width - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (r.height- (fm.getAscent() + fm.getDescent())) / 2);
        Color oldColor = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString(s, r.x + x, r.y+y);
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
