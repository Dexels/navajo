package com.dexels.navajo.tipi.swing.svg.impl;

//import java.awt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

import org.apache.batik.bridge.*;
import org.apache.batik.dom.anim.*;
import org.apache.batik.dom.svg.*;
import org.apache.batik.swing.*;
import org.apache.batik.swing.svg.*;
import org.w3c.dom.*;
import org.w3c.dom.events.*;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.svg.*;

import com.dexels.navajo.tipi.swing.svg.*;

public class SvgBatikComponent extends SvgBaseComponent {
	protected final JSVGCanvas svgCanvas;
	private final ArrayList<SvgAnimationListener> mySvgAnimationListeners = new ArrayList<SvgAnimationListener>();
	private final ArrayList<SvgMouseListener> mySvgMouseListeners = new ArrayList<SvgMouseListener>();
	private final ArrayList<SvgDocumentListener> mySvgDocumentListeners = new ArrayList<SvgDocumentListener>();

//	private BridgeContext bridgeContext;
	private String myRegisteredIdList;
	private SVGSVGElement myRootElement;
	private Dimension myPrefsize;

	public SvgBatikComponent() {
		setLayout(new BorderLayout());
		svgCanvas = new JSVGCanvas();
		svgCanvas.setOpaque(false);
		svgCanvas.setDoubleBufferedRendering(true);
		add(svgCanvas,BorderLayout.CENTER);
		addComponentListener(new ComponentListener(){

			public void componentHidden(ComponentEvent e) {
			}

			public void componentMoved(ComponentEvent e) {
			}

			public void componentResized(ComponentEvent e) {
				doLayout();
				repaint();
			}

			public void componentShown(ComponentEvent e) {
				repaint();
						
			}});
	}
	
	public void setPreferredSize(Dimension d) {
		myPrefsize = d;
		svgCanvas.setPreferredSize(d);
		doLayout();
	}

	
	

	public Dimension getPreferredSize() {
		Dimension preferredSize = super.getPreferredSize();
		return preferredSize;
	}

	public void setBounds(Rectangle r) {
		super.setBounds(r);
		if (svgCanvas != null) {
			svgCanvas.setBounds(r);
		}
		if (myRootElement != null) {
			UpdateManager um = svgCanvas.getUpdateManager();
			if (um == null) {
				return;
			}
			myRootElement.setAttribute("width", "" + r.width);
			myRootElement.setAttribute("height", "" + r.height);
		}
	}
	
	
	public void setSize(Dimension d) {
		super.setSize(d);
		System.err.println("SETTING SIZE: "+d);
	}

	public SVGDocument getDocument() {
		return svgCanvas.getSVGDocument();
	}

	public void fireAnimation(String animId) {
		SVGOMAnimationElement ee = (SVGOMAnimationElement) svgCanvas.getSVGDocument().getElementById(animId);
		UpdateManager um = svgCanvas.getUpdateManager();
		if (um == null) {
			return;
		}

		ee.beginElement();
		
	}

	public void init(URL u) {
		svgCanvas.setBackground(new Color(0x0, true));
		svgCanvas.setOpaque(false);
		svgCanvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		svgCanvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {
			public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
				fireDocumentLoadingStarted();
			}
			
						
			public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
				if (myRegisteredIdList != null && e.getSVGDocument()!=null) {
					StringTokenizer st = new StringTokenizer(myRegisteredIdList, ",");
					while (st.hasMoreElements()) {
						String elem = (String) st.nextElement();
						registerId(elem, e.getSVGDocument());
					}
				}

				myRootElement = e.getSVGDocument().getRootElement();
				fireDocumentLoadingFinished();
				if(myPrefsize!=null) {
					setPreferredSize(myPrefsize);
				}

			}

			@Override
			public void documentLoadingCancelled(SVGDocumentLoaderEvent e) {
				fireDocumentLoadingCancelled();

			}

			@Override
			public void documentLoadingFailed(SVGDocumentLoaderEvent e) {
				fireDocumentLoadingFailed();
			}

		});
		svgCanvas.setURI(u.toString());
	}

	protected void registerAnimationEvents(SVGElement ee) {
		((EventTarget) ee).addEventListener("endEvent", new EventListener() {

			public void handleEvent(Event oy) {
				String targetId = ((AnimationTarget) oy.getCurrentTarget()).getElement().getAttribute("id");
				String animationId = ((AnimationTarget) oy.getTarget()).getElement().getAttribute("id");
				fireOnAnimationEnded(animationId, targetId);

			}
		}, true);
		((EventTarget) ee).addEventListener("beginEvent", new EventListener() {
			public void handleEvent(Event oy) {
				String targetId = ((AnimationTarget) oy.getCurrentTarget()).getElement().getAttribute("id");
				String animationId = ((AnimationTarget) oy.getTarget()).getElement().getAttribute("id");
				fireOnAnimationStarted(animationId, targetId);
			}
		}, true);
	}

	protected void registerEvents(SVGElement ee) {
		((EventTarget) ee).addEventListener("click", new EventListener() {
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget()).getAttribute("id");
				fireOnMouseClick(targetId);
			}
		}, true);

		((EventTarget) ee).addEventListener("mouseup", new EventListener() {
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget()).getAttribute("id");
				fireOnMouseUp(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("mousedown", new EventListener() {
			public void handleEvent(Event oy) {
				String currentId = ((SVGElement) oy.getCurrentTarget()).getAttribute("id");
				String targetId = ((AnimationTarget) oy.getTarget()).getElement().getAttribute("id");
				System.err.println("Current: " + currentId + " target: " + targetId);
				fireOnMouseDown(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("mouseover", new EventListener() {
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget()).getAttribute("id");
				fireOnMouseOver(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("mouseout", new EventListener() {
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget()).getAttribute("id");
				fireOnMouseOut(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("mousemove", new EventListener() {
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget()).getAttribute("id");
				fireOnMouseMove(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("DOMActivate", new EventListener() {
			public void handleEvent(Event oy) {
				System.err.println("DOMActivate!");
				String targetId = ((SVGElement) oy.getCurrentTarget()).getAttribute("id");
				fireActivate(targetId);
			}
		}, false);
		((EventTarget) ee).addEventListener("onselect", new EventListener() {
			public void handleEvent(Event oy) {
				System.err.println("onselect!");

				String targetId = ((SVGElement) oy.getCurrentTarget()).getAttribute("id");
				fireActivate(targetId);
			}
		}, false);

	}

	// public void onClick(String targetId) {
	// System.err.println("Component clicked: "+targetId);
	// fireAnimation("animout");
	// }

	protected void fireActivate(String targetId) {
		System.err.println("FireActivate: "+targetId);
		for (Iterator<SvgMouseListener> iterator = mySvgMouseListeners.iterator(); iterator.hasNext();) {
			iterator.next().onActivate(targetId);
		}
	}

	protected void fireOnMouseMove(String targetId) {
		for (Iterator<SvgMouseListener> iterator = mySvgMouseListeners.iterator(); iterator.hasNext();) {
			iterator.next().onMouseMove(targetId);
		}
	}

	protected void fireOnMouseOver(String targetId) {
		for (Iterator<SvgMouseListener> iterator = mySvgMouseListeners.iterator(); iterator.hasNext();) {
			iterator.next().onMouseOver(targetId);
		}
	}

	protected void fireOnMouseOut(String targetId) {
		for (Iterator<SvgMouseListener> iterator = mySvgMouseListeners.iterator(); iterator.hasNext();) {
			iterator.next().onMouseOut(targetId);
		}
	}

	protected void fireOnMouseDown(String targetId) {
		for (Iterator<SvgMouseListener> iterator = mySvgMouseListeners.iterator(); iterator.hasNext();) {
			iterator.next().onMouseDown(targetId);
		}
	}

	protected void fireOnMouseUp(String targetId) {
		for (Iterator<SvgMouseListener> iterator = mySvgMouseListeners.iterator(); iterator.hasNext();) {
			iterator.next().onMouseUp(targetId);
		}
	}

	protected void fireOnMouseClick(String targetId) {
		for (Iterator<SvgMouseListener> iterator = mySvgMouseListeners.iterator(); iterator.hasNext();) {
			iterator.next().onClick(targetId);
		}
	}

	protected void fireOnAnimationStarted(String animationId, String targetId) {
		for (Iterator<SvgAnimationListener> iterator = mySvgAnimationListeners.iterator(); iterator.hasNext();) {
			iterator.next().onAnimationStarted(animationId, targetId);
		}
	}

	protected void fireOnAnimationEnded(String animationId, String targetId) {
		for (Iterator<SvgAnimationListener> iterator = mySvgAnimationListeners.iterator(); iterator.hasNext();) {
			iterator.next().onAnimationEnded(animationId, targetId);
		}
	}

	public void registerId(String id, SVGDocument doc) {
		if(svgCanvas==null || doc==null) {
			System.err.println("Can not register id: "+id);
			Thread.dumpStack();
			return;
		}
		SVGElement ee = (SVGElement) doc.getElementById(id);
		if(ee==null) {
			System.err.println("Unable to register events. Id not found: "+id);
			return;
		}
		if (ee instanceof SVGAnimationElement) {

		}
		registerAnimationEvents(ee);
		registerEvents(ee);

	}

	protected void fireDocumentLoadingStarted() {
		for (Iterator<SvgDocumentListener> iterator = mySvgDocumentListeners.iterator(); iterator.hasNext();) {
			iterator.next().onDocumentLoadingStarted();
		}
	}

	protected void fireDocumentLoadingFinished() {
		for (Iterator<SvgDocumentListener> iterator = mySvgDocumentListeners.iterator(); iterator.hasNext();) {
			iterator.next().onDocumentLoadingFinished();
		}
	}

	protected void fireDocumentLoadingFailed() {
		for (Iterator<SvgDocumentListener> iterator = mySvgDocumentListeners.iterator(); iterator.hasNext();) {
			iterator.next().onDocumentLoadingFailed();
		}
	}

	protected void fireDocumentLoadingCancelled() {
		for (Iterator<SvgDocumentListener> iterator = mySvgDocumentListeners.iterator(); iterator.hasNext();) {
			iterator.next().onDocumentLoadingCancelled();
		}
	}

	@Override
	public void addSvgAnimationListener(SvgAnimationListener sal) {
		mySvgAnimationListeners.add(sal);
	}

	@Override
	public void addSvgMouseListener(SvgMouseListener sal) {
		mySvgMouseListeners.add(sal);

	}

	@Override
	public void removeSvgAnimationListener(SvgAnimationListener sal) {
		mySvgAnimationListeners.remove(sal);

	}

	@Override
	public void removeSvgMouseListener(SvgMouseListener sal) {
		mySvgMouseListeners.remove(sal);

	}

	public void addSvgDocumentListener(SvgDocumentListener sal) {
		mySvgDocumentListeners.add(sal);

	}

	public void removeSvgDocumentListener(SvgDocumentListener sal) {
		mySvgDocumentListeners.remove(sal);

	}

	@Override
	public void setRegisteredIds(String object) {
		myRegisteredIdList = object;

	}

	public void setAttribute(final String xlinkNS, final String item, final String attributeName, final String value) {
		runInUpdateQueue(new Runnable() {
			public void run() {
				final SVGElement se = (SVGElement) svgCanvas.getSVGDocument().getElementById(item);
				if (xlinkNS == null) {
					se.setAttribute(attributeName, value);
				} else {
					se.setAttributeNS(xlinkNS, "xlink:href", value);
				}
			}
		});
	}
	
	public void setTextContent(final String id, final String value) {
		System.err.println("Getting component: "+id+" for setting text");
		runInUpdateQueue(new Runnable() {
			public void run() {
				final SVGElement se = (SVGElement) svgCanvas.getSVGDocument().getElementById(id);
				if(id==null || se==null) {
					System.err.println("Component not found: "+id);
					return;
				}
				se.setTextContent(value);
			}
		});
	}

	public void moveToFirst(final String id) {
		runInUpdateQueue(new Runnable() {
			public void run() {
				final SVGElement se = (SVGElement) svgCanvas.getSVGDocument().getElementById(id);
				SVGElement parent = (SVGElement) se.getParentNode();
				parent.removeChild(se);
				parent.appendChild(se);
			}
		});
	}

	protected Element createImage(String x, String y, String width, String hgt, String image, String uid, SVGOMDocument document) {
		String svgNS = "http://www.w3.org/2000/svg";
		String xlinkNS = "http://www.w3.org/1999/xlink";
		Element img = document.createElementNS(svgNS, "image");
		img.setAttributeNS(null, "x", "0");
		img.setAttributeNS(null, "y", "0");
		img.setAttributeNS(null, "width", "800");
		img.setAttributeNS(null, "height", "600");
		img.setAttributeNS(xlinkNS, "xlink:href", image);
		img.setAttribute("uid", uid);
		return img;
	}

	public void runInUpdateQueue(Runnable runnable) {
//		String xlinkNS = "http://www.w3.org/1999/xlink";
		if(svgCanvas==null ) {
			System.err.println("Whoops! no svg canvas. Ignoring.");
			Thread.dumpStack();
		} else {
			if(svgCanvas.getSVGDocument()==null) {
				System.err.println("Whoops! no svg document. Ignoring.");
			} else {
				UpdateManager updateManager = svgCanvas.getUpdateManager();
				if (updateManager != null) {
					updateManager.getUpdateRunnableQueue().invokeLater(runnable);
				} else {
					runnable.run();
				}
			}
		}
	}

	@Override
	public boolean isExisting(String id) {
		final SVGElement se = (SVGElement) svgCanvas.getSVGDocument().getElementById(id);
		return se!=null;
	}

	@Override
	public String getTagName(String id) {
		final SVGElement se = (SVGElement) svgCanvas.getSVGDocument().getElementById(id);
		if(se!=null) {
			return se.getTagName();
		}
		return null;
	}

	public void setScale(double d) {
//		if(svgCanvas!=null) {
//			AffineTransform arr = svgCanvas.getRenderingTransform();
//			if(arr!=null) {
//				arr.setToScale(d, d);
//			}
//			repaint();
//		}
	}
}

// Applet:
// batik-awt-util.jar,batik-bridge.jar,batik-css.jar,batik-dom.jar,batik-ext.jar,batik-gvt.jar,batik-parser.jar,
// batik-svg-dom.jar,batik-script.jar,batik-swing.jar,batik-util.jar,batik-xml.jar,xml-apis-dom3.jar

