package com.dexels.navajo.tipi.swing.geo;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;
import com.dexels.navajo.tipi.swing.geo.impl.TipiSwingMapImpl;

public class TipiMapComponent extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = 3180381518976209467L;
	// double lon,lat;
	String mapFactory;
	int zoom;
	private JLayeredPane jp = new JLayeredPane();
	private TipiSwingMapImpl myMapKit;
	private final Map<Component, GeoPosition> mapComponents = new HashMap<Component, GeoPosition>();
	private Map<Component, GeoPosition> mapComponentSizes = new HashMap<Component, GeoPosition>();

	private String messagePath = null;

	private JPanel overlayPanel = null;

	@Override
	public Object createContainer() {
		runSyncInEventThread(new Runnable(){

			public void run() {
				myMapKit = new TipiSwingMapImpl();
				overlayPanel = new JPanel();
				overlayPanel.setLayout(null);
				overlayPanel.setOpaque(false);
				myMapKit.getMainMap().addPropertyChangeListener(new PropertyChangeListener() {

					public void propertyChange(PropertyChangeEvent p) {
						if (p.getPropertyName().equals("zoom")) {
							layoutChildren();
						}
						if (p.getPropertyName().equals("centerPosition")) {
							layoutChildren();
						}
					}
				});

				// crate a WaypointPainter to draw the points
				// WaypointPainter painter = new WaypointPainter();
				jp.addComponentListener(new ComponentListener() {
					public void componentHidden(ComponentEvent arg0) {
					}

					public void componentMoved(ComponentEvent arg0) {
					}

					public void componentResized(ComponentEvent arg0) {
						myMapKit.setBounds(new Rectangle(new Point(0, 0), jp.getSize()));
						overlayPanel.setBounds(new Rectangle(new Point(0, 0), jp.getSize()));
						layoutChildren();
					}

					public void componentShown(ComponentEvent arg0) {
						myMapKit.setBounds(new Rectangle(new Point(0, 0), jp.getSize()));
						overlayPanel.setBounds(new Rectangle(new Point(0, 0), jp.getSize()));
						layoutChildren();
					}
				});
				// myMapKit.getMainMap().setOverlayPainter(painter);
				// myMapKit.setBounds(new Rectangle(0,0, 100,100));
				jp.add(myMapKit, new Integer(100));
				jp.add(overlayPanel, new Integer(101));

				SwingUtilities.invokeLater(new Runnable(){

					public void run() {
						myMapKit.repaint();
					}});
			}});
		
	
		return jp;
	}

	@Override
	public Object getActualComponent() {
		return myMapKit;
	}

	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		super.loadData(n, method);

		if (messagePath == null) {
			return;
		}
		final Message m = n.getMessage(messagePath);
		runSyncInEventThread(new Runnable() {

			public void run() {
				ArrayList<Message> al = m.getAllMessages();
				try {
					performTipiEvent("onClear", null, true);
				} catch (TipiException e) {
					e.printStackTrace();
				}

				for (Message message : al) {
					Map<String, Object> eventParams = new HashMap<String, Object>();
					eventParams.put("message", message);

					try {
						performTipiEvent("onDataComponent", eventParams, true);
					} catch (TipiException e) {
						e.printStackTrace();
					}
				}
				myMapKit.repaint();
			}
		});

	}

	protected void layoutChildren() {
		for (Component c : mapComponents.keySet()) {
			positionComponent(c, mapComponents.get(c), mapComponentSizes.get(c));
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
	protected void setComponentValue(final String name, final Object object) {
		runSyncInEventThread(new Runnable() {

			public void run() {

				if (name.equals("zoom")) {
					myMapKit.setZoomExternal((Integer) object);
				}
				// if(name.equals("lat")) {
				// Number n = (Number)object;
				// myMapKit.setLat(n.doubleValue());
				// }
				// if(name.equals("lon")) {
				// Number n = (Number)object;
				// myMapKit.setLon(n.doubleValue());
				// }
				if (name.equals("factory")) {
					myMapKit.setMapFactory((String) object);
				}
				if (name.equals("messagePath")) {
					messagePath = (String) object;
				}
			}
		});

		super.setComponentValue(name, object);
	}

	@Override
	public void addToContainer(final Object c, Object constraints) {
		System.err.println("entering add");
		myMapKit.setBounds(new Rectangle(new Point(0, 0), jp.getSize()));
		overlayPanel.setBounds(new Rectangle(new Point(0, 0), jp.getSize()));

		String con = (String) constraints;
		StringTokenizer st = new StringTokenizer(con, ",");
		String lat = st.nextToken();
		String lon = st.nextToken();
		double lonF = Double.parseDouble(lon);
		double latF = Double.parseDouble(lat);

		final GeoPosition gp = new GeoPosition(latF, lonF);
		GeoPosition rightB = null;
		mapComponents.put((Component) c, gp);
		overlayPanel.add((Component) c);
		if (st.hasMoreTokens()) {
			String latRightBottom = null;
			String lonRightBottom = null;
			double latRB;
			double lonRB;
			latRightBottom = st.nextToken();
			lonRightBottom = st.nextToken();

			if (latRightBottom.startsWith("+")) {
				System.err.println("REL LAT:" + latRightBottom);
				double rel = Double.parseDouble(latRightBottom.substring(1));
				latRB = latF + rel;
				System.err.println("REsults: " + latRB);
			} else {
				latRB = Double.parseDouble(latRightBottom);
			}

			if (lonRightBottom.startsWith("+")) {
				System.err.println("REL LON:" + lonRightBottom);
				double rel = Double.parseDouble(lonRightBottom.substring(1));
				lonRB = lonF + rel;
				System.err.println("REsults: " + lonRB);
			} else {
				lonRB = Double.parseDouble(lonRightBottom);
			}

			// latRB = Double.parseDouble(latRightBottom);
			rightB = new GeoPosition(latRB, lonRB);
		}
		if (rightB != null) {
			Dimension calcDimension = calcDimension(gp, rightB);
			((JComponent) c).setPreferredSize(calcDimension);
			mapComponentSizes.put((Component) c, rightB);

		} else {
			if (c instanceof JComponent) {
				JComponent jc = (JComponent) c;
				System.err.println("Adding with default size: " + jc.getPreferredSize());
				jc.setSize(jc.getPreferredSize());
			} else {
				((Component) c).setSize(100, 100);
			}
		}
		positionComponent(c, gp, rightB);
		final GeoPosition gg = rightB;
//		myMapKit.addPropertyChangeListener(new PropertyChangeListener() {
//
//			public void propertyChange(PropertyChangeEvent evt) {
//				System.err.println("aaaaprop: "+evt.getPropertyName());
//				if (evt.getPropertyName().equals("ancestor")) {
//					positionComponent(c, gp, gg);
//				}
//			}
//		});

		SwingUtilities.invokeLater(new Runnable(){

			public void run() {
				positionComponent(c, gp, gg);

			}});
		jp.repaint();
		// layoutChildren();
		System.err.println("leaving add");

	}

	private void positionComponent(Object c, GeoPosition gp, GeoPosition rightB) {
		Component comp = (Component) c;
		Point2D gp_pt = myMapKit.getMainMap().getTileFactory().geoToPixel(gp, myMapKit.getMainMap().getZoom());
		Point2D gp_pt_rb = null;
		Dimension d = null;
		Rectangle rect = myMapKit.getMainMap().getViewportBounds();
		Point converted_gp_pt = new Point((int) gp_pt.getX() - rect.x, (int) gp_pt.getY() - rect.y);
		if (rightB != null) {
			gp_pt_rb = myMapKit.getMainMap().getTileFactory().geoToPixel(rightB, myMapKit.getMainMap().getZoom());
			Point converted_gp_pt_rb = new Point((int) gp_pt_rb.getX() - rect.x, (int) gp_pt_rb.getY() - rect.y);
			d = new Dimension(Math.abs(converted_gp_pt_rb.x - converted_gp_pt.x), Math.abs(converted_gp_pt_rb.y - converted_gp_pt.y));
			System.err.println("Pixeldim: " + d);
		} else {
			d = comp.getPreferredSize();
		}
//		comp.doLayout();
		if (rightB == null) {
			// use center, if no offset is given
			comp.setBounds(converted_gp_pt.x - (d.width / 2), converted_gp_pt.y - (d.height / 2), d.width, d.height);
		} else {
			comp.setBounds(converted_gp_pt.x, converted_gp_pt.y, d.width, d.height);

		}
		
	}

	private Dimension calcDimension(GeoPosition lt, GeoPosition rb) {
		Point2D topLeft = myMapKit.getMainMap().getTileFactory().geoToPixel(lt, myMapKit.getMainMap().getZoom());
		Point2D bottomRight = myMapKit.getMainMap().getTileFactory().geoToPixel(rb, myMapKit.getMainMap().getZoom());
		return new Dimension((int) Math.abs(topLeft.getX() - bottomRight.getX()), (int) Math.abs(topLeft.getY() - bottomRight.getY()));
	}

	@Override
	public void removeFromContainer(final Object c) {
		// super.removeFromContainer(c);
		runSyncInEventThread(new Runnable() {

			public void run() {
				mapComponents.remove(c);
				overlayPanel.remove((Component) c);
				overlayPanel.repaint();
			}
		});

	}

}
