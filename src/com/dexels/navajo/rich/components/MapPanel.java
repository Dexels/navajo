package com.dexels.navajo.rich.components;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;

public class MapPanel extends JPanel {
	private ImageIcon mapImage;
	private float opacity = 0.6f;
	private HashMap locations = new HashMap();
	private Point mouseLocation = null;
	private ArrayList locationListeners = new ArrayList();
	private boolean loaded = false;
	private Message clubs;

	int pixConv = 179; // one pixel = 500 metres
	Point origin = new Point(23, 372); // Nieuwe Dijkstraat, Westkapelle, 4361BZ
	double LatO = 51.5292303370394;
	double LongO = 3.43553653901279;

	public MapPanel() {
		setOpaque(false);
		mapImage = new ImageIcon(getClass().getResource("images/holland-dashboard.png"));
		setLayout(null);
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				mouseLocation = e.getPoint();			
			}
		});
	}
	
	public void addLocationListener(LocationListener l) {
		this.locationListeners.add(l);
	}

	public void removeLocationListener(LocationListener l) {
		this.locationListeners.remove(l);
	}

	public void setMessage(Message clubs) {
		try {			
			if (clubs != null) {
				loaded = true;
				locations.clear();
				removeAll();
				for (int i = 0; i < clubs.getArraySize(); i++) {
					Message current = clubs.getMessage(i);
					String union = current.getProperty("Union").getValue();
					String clubId = current.getProperty("ClubId").getValue();
					String clubName = current.getProperty("ClubName").getValue();
					String slat = current.getProperty("Latitude").getValue();
					String slon = current.getProperty("Longitude").getValue();
					try{
						double lat = Double.parseDouble(slat);
						double lon = Double.parseDouble(slon);
						addClub(clubId, clubName, union, lat, lon);
					}catch(Exception e){
						System.err.println("Error adding club: " + clubName + ", lat/lon: " + slat +"/" + slon );
					}
				}
			}			
			repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Message getClubsMessage(){
		return clubs;
	}

	public void paint(Graphics g){
		
		Graphics2D targetGraphics = (Graphics2D)g.create();;
		Rectangle bounds = getBounds();
		
		BufferedImage buffer = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D bufferGraphics = buffer.createGraphics();
	  bufferGraphics.setComposite(AlphaComposite.SrcOver.derive(opacity));
	  bufferGraphics.drawImage(mapImage.getImage(), 0, 0, mapImage.getIconWidth(), mapImage.getIconHeight(), null);
	  bufferGraphics.setComposite(AlphaComposite.SrcOver.derive(1.0f));
	  
	  bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	  bufferGraphics.setColor(Color.white);
	  bufferGraphics.setFont(new Font("Dialog", Font.BOLD, 12));
	  
	  bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	  targetGraphics.drawImage(buffer, 0, 0, buffer.getWidth(), buffer.getHeight(),	null);
	  
	  targetGraphics.dispose();
	  paintChildren(g);
	}

	private boolean mouseOverLoc(Point location) {
		if (mouseLocation != null) {
			double distance = location.distance(mouseLocation.x, mouseLocation.y);
			if (distance < 10) {
				return true;
			}
		}
		return false;
	}

	public void addClub(String clubIdentifier, String description, String union, double dlat, double dlon) {

		Point xyLoc = getXYFromLatLon(dlat, dlon);
		ClubLocation loc = new ClubLocation(xyLoc, union);
		loc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLocationInfo(e);
			}
		});
		loc.setText(description);
		loc.setClubIdentifier(clubIdentifier);
		loc.setLatLonPosition(dlat, dlon);
		locations.put(clubIdentifier, loc);
		add(loc);
	}

	private Point getXYFromLatLon(double dlat, double dlon) {
		Point p = new Point();

		// Lattitude
		double diffLat = dlat - LatO; // difference in lattitude
		double distance = diffLat * pixConv; // Distance in metres between origin
																					// and point
		int pixelsLat = (int) (distance);
		p.y = origin.y - pixelsLat;

		// Longitude
		double diffLong = dlon - LongO;
		double factor = Math.cos(LatO / (180 / Math.PI));
		double distanceLong = diffLong * factor * pixConv;
		int pixelsLong = (int) (distanceLong);
		p.x = pixelsLong + origin.x;

		return p;
	}

	private void getLocationInfo(ActionEvent e) {
		if (e.getSource() instanceof ClubLocation) {
			ClubLocation cl = (ClubLocation) e.getSource();
			for (int i = 0; i < locationListeners.size(); i++) {
				LocationListener l = (LocationListener) locationListeners.get(i);
				l.locationRequested(cl.getClubIdentifier(), cl.getText(), cl.getUnion(), cl.getLat(), cl.getLon());
			}
		}
	}


}

