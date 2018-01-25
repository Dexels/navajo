package com.dexels.navajo.tipi.swing.geo.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jxmapviewer.painter.Painter;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.swing.geo.TipiMapComponent;

public class TipiMapSoccerField implements Painter<JXMapViewer> {
    private final static Logger logger = LoggerFactory.getLogger(TipiMapSoccerField.class);
    
    private static final String FIELD_USE_MATCHES = "MATCHES";
    private static final String FIELD_USE_MATCHES_TRAINING = "MATCHES_TRAINING";
    private static final String FIELD_TYPE_PLAIN_GRASS = "PLAIN_GRASS";
    private static final String FIELD_TYPE_ARTIFICIAL_GRASS = "ARTIFICIAL_GRASS";
    private static final String FIELD_TYPE_WETERA = "WETERA";

    private static final String FIELD_USE_MATCH_ICON = "/icons/club-facility/fielduse_match_large.png";
    private static final String FIELD_USE_MATCH_TRAINING_ICON = "/icons/club-facility/fielduse_match_training_large.png";
    private static final String FIELD_TYPE_PLAIN_GRASS_ICON = "/icons/club-facility/fieldtype_naturalgrass_large.png";
    private static final String FIELD_TYPE_ARTIFICIAL_GRASS_ICON = "/icons/club-facility/fieldtype_artificialgrass_large.png";
    private static final String FIELD_TYPE_WETERA_ICON = "/icons/club-facility/fieldtype_wetra_large.png";
    
    private static final int FIELD_TRANSPARANCY = 230;
    
    private TipiContext context;

    private final double bearing;
    private boolean antiAlias = true;
    private final GeoPosition pos;
    private String label;
    private String infoLabel  = null;
    
    private String fieldUse;
    private String fieldType;
    private Boolean isHalfField;


    private Image fieldUseImage = null;
    private Image fieldTypeImage = null;
    private Font labelFont;
    private Font infoFont;


    
    public TipiMapSoccerField(TipiContext context, Message field, Message filter) {
        this.context = context;
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
        this.fieldType = field.getProperty("FieldType").getSelected().getValue();
        
        this.labelFont = new Font("SansSerif", Font.PLAIN, 14);
        this.infoFont = new Font("SansSerif", Font.PLAIN, 12);
        
        setFilterValues(field, filter);
        
    }

    private void setFilterValues(Message field, Message filter) {
        if (filter == null) return;
  
        // Get icons
        if (filter.getProperty("FieldUse").getSelectionByValue(FIELD_USE_MATCHES).isSelected() && fieldUse.equals(FIELD_USE_MATCHES)) {
            fieldUseImage = getImage(FIELD_USE_MATCHES);
        }
        if (filter.getProperty("FieldUse").getSelectionByValue(FIELD_USE_MATCHES_TRAINING).isSelected()
                && fieldUse.equals(FIELD_USE_MATCHES_TRAINING)) {
            fieldUseImage = getImage(FIELD_USE_MATCHES_TRAINING);
        }

        if (filter.getProperty("FieldType").getSelectionByValue(FIELD_TYPE_PLAIN_GRASS).isSelected()
                && fieldType.equals(FIELD_TYPE_PLAIN_GRASS)) {
            fieldTypeImage = getImage(FIELD_TYPE_PLAIN_GRASS);
        }
        if (filter.getProperty("FieldType").getSelectionByValue(FIELD_TYPE_ARTIFICIAL_GRASS).isSelected()
                && fieldType.equals(FIELD_TYPE_ARTIFICIAL_GRASS)) {
            fieldTypeImage = getImage(FIELD_TYPE_ARTIFICIAL_GRASS);
        }
        if (filter.getProperty("FieldType").getSelectionByValue(FIELD_TYPE_WETERA).isSelected() && fieldType.equals(FIELD_TYPE_WETERA)) {
            fieldTypeImage = getImage(FIELD_TYPE_WETERA);
        }
       
      
        
        StringBuilder sb = new StringBuilder();
        if (filter.getProperty("FieldDetails").getSelectionByValue("LUX").isSelected()) {
            sb.append(filter.getProperty("FieldDetails").getSelectionByValue("LUX").getName());
            sb.append(": ");
            sb.append(field.getProperty("LuxValueUsed").getTypedValue().toString());
            sb.append("\n");
        }
        if (filter.getProperty("FieldDetails").getSelectionByValue("DUGOUT").isSelected()) {
            sb.append(filter.getProperty("FieldDetails").getSelectionByValue("DUGOUT").getName());
            sb.append(": ");
            sb.append(field.getProperty("DugOut").getTypedValue().toString());
            sb.append("\n");
        }
        if (filter.getProperty("FieldDetails").getSelectionByValue("FENCE").isSelected()) {
            sb.append(filter.getProperty("FieldDetails").getSelectionByValue("FENCE").getName());
            sb.append(": ");
            sb.append(field.getProperty("Fence").getTypedValue().toString());
        }
        infoLabel= sb.toString();
        
        
    }
    
    private Image getImage(String type) {
        Image result = null;
        URL resourceURL = null;
        if (type.equals(FIELD_USE_MATCHES)) {
            resourceURL = context.getResourceURL(FIELD_USE_MATCH_ICON);
        } else if (type.equals(FIELD_USE_MATCHES_TRAINING)){
            resourceURL = context.getResourceURL(FIELD_USE_MATCH_TRAINING_ICON);
        } else if (type.equals(FIELD_TYPE_PLAIN_GRASS)) {
            resourceURL = context.getResourceURL(FIELD_TYPE_PLAIN_GRASS_ICON);
        } else if (type.equals(FIELD_TYPE_ARTIFICIAL_GRASS)) {
            resourceURL = context.getResourceURL(FIELD_TYPE_ARTIFICIAL_GRASS_ICON);
        } else if (type.equals(FIELD_TYPE_WETERA)) {
            resourceURL = context.getResourceURL(FIELD_TYPE_WETERA_ICON);
        }
        
        if (resourceURL != null) {
            try {
                result = ImageIO.read(resourceURL);
            } catch (IOException e) {
                logger.error("Error reading image!", e);
            }  
        }
        return result;
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
        g2d.dispose();

    }

    private void drawFieldOverlay(Graphics2D g2d, JXMapViewer map) {
        // do the drawing
        Point2D centerPoint = map.getTileFactory().geoToPixel(pos, map.getZoom());
        
        Polygon p = getFieldPolygon(map);
 
        Color myColour = getFieldColor();
        g2d.setColor(myColour);
        g2d.fillPolygon(p);
        g2d.setColor(Color.WHITE);
        g2d.drawPolygon(p);
        
        
        if (fieldUseImage != null) {
            Double xOffset = (map.getZoom() == 0) ? 30 : 30 / (map.getZoom()/1.5d);
            Double yOffset = (map.getZoom() == 0) ? 75 : 40 / (map.getZoom()/2d);
            Double x = centerPoint.getX() + xOffset;
            Double y = centerPoint.getY() - yOffset;
            g2d.drawImage(fieldUseImage, x.intValue(), y.intValue(), null);
        }
        if (fieldTypeImage != null) {
            Double xOffset = (map.getZoom() == 0) ? 30 : 30 / (map.getZoom()/1.5d);
            Double yOffset = (map.getZoom() == 0) ? 75 : 40 / (map.getZoom()/2d);
            Double x = centerPoint.getX() - xOffset;
            Double y = centerPoint.getY() - yOffset;
            g2d.drawImage(fieldTypeImage, x.intValue(), y.intValue(), null);
        }
        
        Font oldFont = g2d.getFont();
        g2d.setFont(labelFont);
        drawCenteredString(label, p, g2d, 0);
        if (infoLabel != null) {
            g2d.setFont(infoFont);
            Double offset = (map.getZoom() == 0) ? 30 : 30d / map.getZoom();
            drawCenteredString(infoLabel, p, g2d, offset.intValue() );
        }
        g2d.setFont(oldFont);
        return;
    }

    private Polygon getFieldPolygon(JXMapViewer map) {
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
        return p;
    }


    public static GeoPosition travel(GeoPosition start, double distance, double initialBearing) {
        double bR = Math.toRadians(initialBearing);
        double lat1R = Math.toRadians(start.getLatitude());
        double lon1R = Math.toRadians(start.getLongitude());
        double dR = distance / (6372797.6D); // earth radius in meters

        double a = Math.sin(dR) * Math.cos(lat1R);
        double lat2 = Math.asin(Math.sin(lat1R) * Math.cos(dR) + a * Math.cos(bR));
        double lon2 = lon1R + Math.atan2(Math.sin(bR) * a, Math.cos(dR) - Math.sin(lat1R) * Math.sin(lat2));
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

    private void drawCenteredString(String s, Polygon p, Graphics g, int yOffset) {
        FontMetrics fm = g.getFontMetrics();
        
        int y = (fm.getAscent() + (p.getBounds().height- (fm.getAscent() + fm.getDescent())) / 2);
        Color oldColor = g.getColor();
        g.setColor(Color.WHITE);
        for (String line : s.split("\n")) {
            int x = (p.getBounds().width - fm.stringWidth(line)) / 2;
            g.drawString(line, p.getBounds().x + x, p.getBounds().y+y + yOffset);
            y += g.getFontMetrics().getHeight();
        }

        g.setColor(oldColor);
      }
    
   
}
