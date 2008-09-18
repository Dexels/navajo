package com.dexels.navajo.tipi.swing.geo;

import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.*;
import java.beans.*;
import java.util.*;

import javax.swing.*;

import org.jdesktop.swingx.mapviewer.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.swing.geo.impl.*;

public class TipiMapComponent extends TipiSwingDataComponentImpl {

	double lon,lat;
	String mapFactory;
	int zoom;
	private JLayeredPane jp = new JLayeredPane();
	private TipiSwingMapImpl myMapKit;
	private final Map<Component,GeoPosition> mapComponents = new HashMap<Component,GeoPosition>();
	private Map<Component, GeoPosition> mapComponentSizes = new HashMap<Component,GeoPosition>();
		
	private String messagePath = null; 
	
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
//	    WaypointPainter painter = new WaypointPainter();
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
//	    myMapKit.getMainMap().setOverlayPainter(painter);
//	    myMapKit.setBounds(new Rectangle(0,0, 100,100));
	    jp.add(myMapKit,new Integer(100));
	    jp.add(overlayPanel,new Integer(101));
	    
	    return jp;
	}
	
	

	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		super.loadData(n, method);

		if(messagePath==null) {
			return;
		}
		final Message m = n.getMessage(messagePath);
//		List m = m.getAllMessages();
		runSyncInEventThread(new Runnable(){

			public void run() {
				ArrayList<Message> al = m.getAllMessages();
//				waypoints.clear();
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
			positionComponent(c, mapComponents.get(c),mapComponentSizes.get(c));
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
		if(name.equals("messagePath")) {
			messagePath = (String)object;
		}
		super.setComponentValue(name, object);
	}

	@Override
	public void addToContainer(Object c, Object constraints) {

		myMapKit.setBounds(new Rectangle(new Point(0,0),jp.getSize()));
		overlayPanel.setBounds(new Rectangle(new Point(0,0),jp.getSize()));

		String con = (String) constraints;
		StringTokenizer st = new StringTokenizer(con,",");
		String lat = st.nextToken();
		String lon = st.nextToken();
		double lonF = Double.parseDouble(lon);
		double latF = Double.parseDouble(lat);
		
		GeoPosition gp = new GeoPosition(latF,lonF);
		GeoPosition rightB = null;
		mapComponents.put((Component) c, gp);
		overlayPanel.add((Component) c);
		if(st.hasMoreTokens()) {
			String latRightBottom = null;
			String lonRightBottom = null;
			double latRB;
			double lonRB;
			latRightBottom = st.nextToken();
			lonRightBottom = st.nextToken();

			if(latRightBottom.startsWith("+")) {
				System.err.println("REL LAT:"+latRightBottom);
				double rel = Double.parseDouble(latRightBottom.substring(1));
				latRB = latF + rel;
				System.err.println("REsults: "+latRB);
			} else {
				latRB = Double.parseDouble(latRightBottom);
			}

			if(lonRightBottom.startsWith("+")) {
				System.err.println("REL LON:"+lonRightBottom);
				double rel = Double.parseDouble(lonRightBottom.substring(1));
				lonRB = lonF + rel;
				System.err.println("REsults: "+lonRB);
			} else {
				lonRB = Double.parseDouble(lonRightBottom);
			}

			//latRB = Double.parseDouble(latRightBottom);
			rightB = new GeoPosition(latRB,lonRB);
		}
		if (rightB!=null) {
			Dimension calcDimension = calcDimension(gp, rightB);
			System.err.println("DIMENSION:" +calcDimension);
			((JComponent)c).setPreferredSize(calcDimension);
			mapComponentSizes.put((Component) c, rightB);
			
		} else {
			if(c instanceof JComponent) {
	        	JComponent jc = (JComponent)c;
	        	System.err.println("Adding with default size: "+jc.getPreferredSize());
	        	jc.setSize(jc.getPreferredSize());
	        } else {
	        	((Component)c).setSize(100,100);
	        }
		}
        positionComponent(c, gp,rightB);
        jp.repaint();
        //        layoutChildren();
        	
	}

	private void positionComponent(Object c, GeoPosition gp, GeoPosition rightB) {
		Component comp = (Component)c;
        Point2D gp_pt = myMapKit.getMainMap().getTileFactory().geoToPixel(gp, myMapKit.getMainMap().getZoom());
		System.err.println("TOPLEFT: "+gp+" == "+rightB);
		Point2D gp_pt_rb = null;
        Dimension d = null;
        Rectangle rect = myMapKit.getMainMap().getViewportBounds();
        Point converted_gp_pt = new Point((int)gp_pt.getX()-rect.x,
                (int)gp_pt.getY()-rect.y);
        if(rightB!=null) {
			gp_pt_rb = myMapKit.getMainMap().getTileFactory().geoToPixel(rightB, myMapKit.getMainMap().getZoom());
			Point converted_gp_pt_rb = new Point((int)gp_pt_rb.getX()-rect.x,
                    (int)gp_pt_rb.getY()-rect.y);
			d = new Dimension(Math.abs(converted_gp_pt_rb.x - converted_gp_pt.x),Math.abs(converted_gp_pt_rb.y - converted_gp_pt.y));
			System.err.println("Pixeldim: "+d);
        } else {
			d = comp.getPreferredSize();
		}

        if(rightB==null) {
        	// use center, if no offset is given
        	comp.setBounds(converted_gp_pt.x-(d.width/2),converted_gp_pt.y-(d.height/2),d.width,d.height);
        } else {
            comp.setBounds(converted_gp_pt.x,converted_gp_pt.y,d.width,d.height);
        	
        }
	}

	private Dimension calcDimension(GeoPosition lt, GeoPosition rb) {
		Point2D topLeft = myMapKit.getMainMap().getTileFactory().geoToPixel(lt, myMapKit.getMainMap().getZoom());
		Point2D bottomRight = myMapKit.getMainMap().getTileFactory().geoToPixel(rb, myMapKit.getMainMap().getZoom());
		return new Dimension((int)Math.abs(topLeft.getX()-bottomRight.getX()),(int)Math.abs(topLeft.getY()-bottomRight.getY()));
	}
	
	@Override
	public void removeFromContainer(final Object c) {
//		super.removeFromContainer(c);
		runSyncInEventThread(new Runnable(){

			public void run() {
				mapComponents.remove(c);
				overlayPanel.remove((Component) c);
				overlayPanel.repaint();
			}});
		
	}

}
