package com.dexels.navajo.tipi.swing.geo;

import java.awt.*;

import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.*;
import java.beans.*;
import java.util.*;

import javax.swing.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.swing.geo.impl.*;
import com.dexels.navajo.tipi.swing.geo.impl.tilefactory.*;

public class TipiMapComponent extends TipiSwingDataComponentImpl {

	double lon,lat;
	String mapFactory;
	int zoom;
	private JLayeredPane jp = new JLayeredPane();
	private TipiSwingMapImpl myMapKit;
	private final Map<Component,GeoPosition> mapComponents = new HashMap<Component,GeoPosition>();
	private final Set<Waypoint> waypoints = new HashSet<Waypoint>();
		 
	private final JPanel overlayPanel = new JPanel();
	@Override
	public Object createContainer() {
		myMapKit = new TipiSwingMapImpl();

		overlayPanel.setLayout(null);
		overlayPanel.setOpaque(false);
		myMapKit.getMainMap().addPropertyChangeListener(new PropertyChangeListener(){

			public void propertyChange(PropertyChangeEvent p) {
				if(p.getPropertyName().equals("zoom")) {
					layoutChildren();
				}
				if(p.getPropertyName().equals("centerPosition")) {
					layoutChildren();
				}
			}});
		
	    //crate a WaypointPainter to draw the points
	    WaypointPainter painter = new WaypointPainter();
	    painter.setWaypoints(waypoints);
	    jp.addComponentListener(new ComponentListener(){
			public void componentHidden(ComponentEvent arg0) {}
			public void componentMoved(ComponentEvent arg0) {}
			public void componentResized(ComponentEvent arg0) {
				myMapKit.setBounds(new Rectangle(new Point(0,0),jp.getSize()));
				overlayPanel.setBounds(new Rectangle(new Point(0,0),jp.getSize()));
				layoutChildren();
			}

			public void componentShown(ComponentEvent arg0) {
				myMapKit.setBounds(new Rectangle(new Point(0,0),jp.getSize()));
				overlayPanel.setBounds(new Rectangle(new Point(0,0),jp.getSize()));
				layoutChildren();
			}});
	    myMapKit.getMainMap().setOverlayPainter(painter);
	    myMapKit.setBounds(new Rectangle(0,0, 100,100));
	    jp.add(myMapKit,new Integer(100));
	    jp.add(overlayPanel,new Integer(101));
	    
	    return jp;
	}
	
	

	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		super.loadData(n, method);

		final Message m = n.getMessage("Clubs");
//		List m = m.getAllMessages();
		runSyncInEventThread(new Runnable(){

			public void run() {
				ArrayList<Message> al = m.getAllMessages();
				waypoints.clear();
				try {
					performTipiEvent("onClear", null, true);
				} catch (TipiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (Message message : al) {
//					try {
//						message.write(System.err);
//					} catch (NavajoException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					Map<String, Object> eventParams = new HashMap<String, Object>();
					eventParams.put("message",message);
					
					try {
						performTipiEvent("onDataComponent", eventParams, true);
					} catch (TipiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					break;
					
//					DesktopButton a;
//					Double latl = (Double) message.getProperty("Latitude").getTypedValue();
//					Double lonl = (Double) message.getProperty("Longitude").getTypedValue();
//					//waypoints.add(new Waypoint(latl,lonl));
//					
//					MacLink macLink = new MacLink();
//					macLink.setToolTipText(""+message.getProperty("ClubName").getTypedValue());
//					macLink.setIcon(new ImageIcon( myContext.getResourceURL("") ""+message.getProperty("ClubName").getTypedValue()));
//
//					addToContainer(macLink, ""+latl+","+lonl);
				}				
				myMapKit.repaint();
			}});

	}
	



	protected void layoutChildren() {
		for (Component c : mapComponents.keySet()) {
			positionComponent(c, mapComponents.get(c));
		}
	}


	public void setMapFactory(String mapFactory) {
		this.mapFactory = mapFactory;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
		myMapKit.setZoom(zoom);
	}

	
	
	@Override
	protected void setComponentValue(String name, Object object) {
		if(name.equals("zoom")) {
			myMapKit.setZoomExternal((Integer)object);
		}
		if(name.equals("lat")) {
			myMapKit.setLat((Double)object);
		}
		if(name.equals("lon")) {
			myMapKit.setLon((Double)object);
		}
		if(name.equals("factory")) {
			myMapKit.setMapFactory((String)object);
		}
		super.setComponentValue(name, object);
	}

	@Override
	public void addToContainer(Object c, Object constraints) {
		String con = (String) constraints;
		StringTokenizer st = new StringTokenizer(con,",");
		String lon = st.nextToken();
		String lat = st.nextToken();
		double lonF = Double.parseDouble(lon);
		double latF = Double.parseDouble(lat);
		GeoPosition gp = new GeoPosition(lonF,latF);
		mapComponents.put((Component) c, gp);
		overlayPanel.add((Component) c);
        if(c instanceof JComponent) {
        	JComponent jc = (JComponent)c;
        	jc.setSize(jc.getPreferredSize());
        }
        positionComponent(c, gp);
        jp.repaint();
        //        layoutChildren();
        	
	}

	private void positionComponent(Object c, GeoPosition gp) {
		Point2D gp_pt = myMapKit.getMainMap().getTileFactory().geoToPixel(gp, myMapKit.getMainMap().getZoom());
        //convert to screen
        Rectangle rect = myMapKit.getMainMap().getViewportBounds();
        Point converted_gp_pt = new Point((int)gp_pt.getX()-rect.x,
                                          (int)gp_pt.getY()-rect.y);
        Component comp = (Component)c;
        Dimension d = comp.getSize();
        
        comp.setLocation(converted_gp_pt.x-(d.width/2),converted_gp_pt.y-(d.height/2));
	}

	@Override
	public void removeFromContainer(Object c) {
		super.removeFromContainer(c);
	}

}
