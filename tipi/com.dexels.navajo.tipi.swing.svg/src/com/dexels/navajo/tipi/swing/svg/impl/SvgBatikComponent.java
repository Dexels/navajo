package com.dexels.navajo.tipi.swing.svg.impl;

//import java.awt.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.net.*;
import java.util.*;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

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

	private static final long serialVersionUID = 8507026263039278186L;
	public final JSVGCanvas svgCanvas;
	private final ArrayList<SvgAnimationListener> mySvgAnimationListeners = new ArrayList<SvgAnimationListener>();
	private final ArrayList<SvgMouseListener> mySvgMouseListeners = new ArrayList<SvgMouseListener>();
	private final ArrayList<SvgDocumentListener> mySvgDocumentListeners = new ArrayList<SvgDocumentListener>();
	private final String SCROLL_RIGHT_ACTION = "sr";
	private final String SCROLL_LEFT_ACTION = "sl";
	private final String SCROLL_UP_ACTION = "su";
	private final String SCROLL_DOWN_ACTION = "sd";

	private final String FAST_SCROLL_RIGHT_ACTION = "fsr";
	private final String FAST_SCROLL_LEFT_ACTION = "fsl";
	private final String FAST_SCROLL_UP_ACTION = "fsu";
	private final String FAST_SCROLL_DOWN_ACTION = "fsd";

	// private BridgeContext bridgeContext;
	private String myRegisteredIdList;
	private SVGSVGElement myRootElement;
	private Dimension myPrefsize;
	private double current_x_translate = 0;
	private double current_y_translate = 0;
	private double current_zoom_factor = 1.0;
	private double theta = 0.0;

	public SvgBatikComponent() {
		setLayout(new BorderLayout());
		svgCanvas = new JSVGCanvas();

		// svgCanvas.getInteractors().clear();
		svgCanvas.setEnableZoomInteractor(false);
		// TipiSvgZoomInteractor zi = new TipiSvgZoomInteractor();
		// svgCanvas.getInteractors().add(zi);

		svgCanvas.setOpaque(false);
		svgCanvas.setDoubleBufferedRendering(true);
		add(svgCanvas, BorderLayout.CENTER);
		addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent e) {
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
				doLayout();
				repaint();
			}

			@Override
			public void componentShown(ComponentEvent e) {
				repaint();
			}
		});

		setupActionMap();
		setupInputMap();
	}

	private void setupInputMap() {
		InputMap inputMap = svgCanvas.getInputMap();
		KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
		inputMap.put(key, SCROLL_RIGHT_ACTION);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
		inputMap.put(key, SCROLL_LEFT_ACTION);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
		inputMap.put(key, SCROLL_UP_ACTION);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
		inputMap.put(key, SCROLL_DOWN_ACTION);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.SHIFT_MASK);
		inputMap.put(key, FAST_SCROLL_RIGHT_ACTION);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.SHIFT_MASK);
		inputMap.put(key, FAST_SCROLL_LEFT_ACTION);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.SHIFT_MASK);
		inputMap.put(key, FAST_SCROLL_UP_ACTION);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.SHIFT_MASK);
		inputMap.put(key, FAST_SCROLL_DOWN_ACTION);

	}

	private void setupActionMap() {
		ActionMap actionMap = svgCanvas.getActionMap();
		actionMap.put(SCROLL_RIGHT_ACTION, new ScrollEvent(SCROLL_RIGHT_ACTION, 10));
		actionMap.put(SCROLL_LEFT_ACTION, new ScrollEvent(SCROLL_LEFT_ACTION, 10));
		actionMap.put(SCROLL_DOWN_ACTION, new ScrollEvent(SCROLL_DOWN_ACTION, 10));
		actionMap.put(SCROLL_UP_ACTION, new ScrollEvent(SCROLL_UP_ACTION, 10));
		actionMap.put(FAST_SCROLL_RIGHT_ACTION, new ScrollEvent(FAST_SCROLL_RIGHT_ACTION, 30));
		actionMap.put(FAST_SCROLL_LEFT_ACTION, new ScrollEvent(FAST_SCROLL_LEFT_ACTION, 30));
		actionMap.put(FAST_SCROLL_UP_ACTION, new ScrollEvent(FAST_SCROLL_UP_ACTION, 30));
		actionMap.put(FAST_SCROLL_DOWN_ACTION, new ScrollEvent(FAST_SCROLL_DOWN_ACTION, 30));
	}

	public class ScrollEvent extends AbstractAction {
		
		private static final long serialVersionUID = 1007950559250199520L;
		String direction = "";
		int amount = 0;

		public ScrollEvent(String direction, int amount) {
			this.direction = direction;
			this.amount = amount;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if (svgCanvas == null) {
				return;
			}
			
			AffineTransform at = null;
			if(SCROLL_DOWN_ACTION.equals(direction) || FAST_SCROLL_DOWN_ACTION.equals(direction) ){
				at =  AffineTransform.getTranslateInstance(0.0, -amount);
				current_y_translate += -(amount/current_zoom_factor);
			}
			if(SCROLL_LEFT_ACTION.equals(direction) || FAST_SCROLL_LEFT_ACTION.equals(direction)){
				at =  AffineTransform.getTranslateInstance(amount, 0.0);
				current_x_translate += (amount/current_zoom_factor);
			}
			if(SCROLL_UP_ACTION.equals(direction) || FAST_SCROLL_UP_ACTION.equals(direction)){
				at =  AffineTransform.getTranslateInstance(0.0, amount);
				current_y_translate += (amount/current_zoom_factor);
			}
			if(SCROLL_RIGHT_ACTION.equals(direction) || FAST_SCROLL_RIGHT_ACTION.equals(direction)){
				at =  AffineTransform.getTranslateInstance(-amount, 0.0);
				current_x_translate += -(amount/current_zoom_factor);
			}
//			System.err.println("Current zoom_factor: " + current_zoom_factor + ", amount: " + (int)(amount/current_zoom_factor) + ", current x: " + current_x_translate + ", current_y: " + current_y_translate);

			AffineTransform rat = svgCanvas.getRenderingTransform();
			if (at != null) {
				Dimension dim = getSize();
				int x = dim.width / 2;
				int y = dim.height / 2;
				AffineTransform t = AffineTransform.getTranslateInstance(x, y);
				t.concatenate(at);
				t.translate(-x, -y);
				t.concatenate(rat);
				svgCanvas.setRenderingTransform(t);
			}
		}
	}

	@Override
	public void setPreferredSize(Dimension d) {
		myPrefsize = d;
		svgCanvas.setPreferredSize(d);
		doLayout();
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension preferredSize = super.getPreferredSize();
		return preferredSize;
	}

	@Override
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

	@Override
	public void setSize(Dimension d) {
		super.setSize(d);
		System.err.println("SETTING SIZE: " + d);
	}

	public SVGDocument getDocument() {
		return svgCanvas.getSVGDocument();
	}

	public void setDocument(SVGDocument doc) {
		init();
		svgCanvas.setDocument(doc);
	}

	@Override
	public void fireAnimation(String animId) {
		SVGOMAnimationElement ee = (SVGOMAnimationElement) svgCanvas.getSVGDocument().getElementById(animId);
		UpdateManager um = svgCanvas.getUpdateManager();
		if (um == null) {
			return;
		}
		ee.beginElement();
	}

	public void init() {
		svgCanvas.setBackground(new Color(0x0, true));
		svgCanvas.setOpaque(false);
		svgCanvas.setDocumentState(AbstractJSVGComponent.ALWAYS_DYNAMIC);
		svgCanvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {
			@Override
			public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
				fireDocumentLoadingStarted();
			}

			@Override
			public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
				if (myRegisteredIdList != null && e.getSVGDocument() != null) {
					StringTokenizer st = new StringTokenizer(myRegisteredIdList, ",");
					while (st.hasMoreElements()) {
						String elem = (String) st.nextElement();
						System.err.println("Registering: " + elem);
						registerId(elem, e.getSVGDocument());
					}
				}

				myRootElement = e.getSVGDocument().getRootElement();
				fireDocumentLoadingFinished();
				if (myPrefsize != null) {
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

	}

	@Override
	public void init(URL u) {
		init();
		svgCanvas.setURI(u.toString());
	}

	protected void registerAnimationEvents(SVGElement ee) {
		((EventTarget) ee).addEventListener("endEvent", new EventListener() {

			@Override
			public void handleEvent(Event oy) {
				String targetId = ((AnimationTarget) oy.getCurrentTarget()).getElement().getAttribute("id");
				String animationId = ((AnimationTarget) oy.getTarget()).getElement().getAttribute("id");
				System.err.println("endEvent");
				fireOnAnimationEnded(animationId, targetId);

			}
		}, true);
		((EventTarget) ee).addEventListener("beginEvent", new EventListener() {
			@Override
			public void handleEvent(Event oy) {
				String targetId = ((AnimationTarget) oy.getCurrentTarget()).getElement().getAttribute("id");
				String animationId = ((AnimationTarget) oy.getTarget()).getElement().getAttribute("id");
				fireOnAnimationStarted(animationId, targetId);
			}
		}, true);
	}

	protected void registerEvents(SVGElement ee) {
		((EventTarget) ee).addEventListener("click", new EventListener() {
			@Override
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget()).getAttribute("id");
				fireOnMouseClick(targetId);
			}
		}, true);

		((EventTarget) ee).addEventListener("mouseup", new EventListener() {
			@Override
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget()).getAttribute("id");
				fireOnMouseUp(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("mousedown", new EventListener() {
			@Override
			public void handleEvent(Event oy) {
				String currentId = ((SVGElement) oy.getCurrentTarget()).getAttribute("id");
				String targetId = ((AnimationTarget) oy.getTarget()).getElement().getAttribute("id");
				System.err.println("Current: " + currentId + " target: " + targetId);
				fireOnMouseDown(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("mouseover", new EventListener() {
			@Override
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget()).getAttribute("id");
				fireOnMouseOver(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("mouseout", new EventListener() {
			@Override
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget()).getAttribute("id");
				fireOnMouseOut(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("mousemove", new EventListener() {
			@Override
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget()).getAttribute("id");
				fireOnMouseMove(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("DOMActivate", new EventListener() {
			@Override
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
		System.err.println("FireActivate: " + targetId);
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

		if (svgCanvas == null || doc == null) {
			System.err.println("Can not register id: " + id);
			Thread.dumpStack();
			return;
		}
		SVGElement ee = (SVGElement) doc.getElementById(id);
		if (ee == null) {
			System.err.println("Unable to register events. Id not found: " + id);
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
		System.err.println("Registered IDS SET: " + object);
		myRegisteredIdList = object;

	}

	public void setAttribute(final String xlinkNS, final String item, final String attributeName, final String value) {
		runInUpdateQueue(new Runnable() {
			public void run() {
				final SVGElement se = (SVGElement) svgCanvas.getSVGDocument().getElementById(item);
				if (xlinkNS == null) {
					System.err.println("No namespace. Setting: " + attributeName + " to: " + value);
					se.setAttribute(attributeName, value);
				} else {
					se.setAttributeNS(xlinkNS, "xlink:href", value);
				}
			}
		});
	}

	public void setTextContent(final String id, final String value) {
		System.err.println("Getting component: " + id + " for setting text");
		runInUpdateQueue(new Runnable() {
			public void run() {
				final SVGElement se = (SVGElement) svgCanvas.getSVGDocument().getElementById(id);
				if (id == null || se == null) {
					System.err.println("Component not found: " + id);
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
		// String xlinkNS = "http://www.w3.org/1999/xlink";
		if (svgCanvas == null) {
			System.err.println("Whoops! no svg canvas. Ignoring.");
			Thread.dumpStack();
		} else {
			if (svgCanvas.getSVGDocument() == null) {
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
		return se != null;
	}

	@Override
	public String getTagName(String id) {
		final SVGElement se = (SVGElement) svgCanvas.getSVGDocument().getElementById(id);
		if (se != null) {
			return se.getTagName();
		}
		return null;
	}

	
	// Keep track of rotation and add here also.
	// Keep track of mouse infliced translations as well.
	public void zoom(double factor) {
		String vb = svgCanvas.getSVGDocument().getDocumentElement().getAttribute("viewBox");
		System.err.println("Viewbox: " + vb);
		current_zoom_factor = factor;
		AffineTransform at = AffineTransform.getScaleInstance(factor, factor);
		System.err.println("Factor: " + factor);
		if (at != null) {
			Dimension dim = getSize();
			int x = (dim.width / 2);
			int y = (dim.height / 2);
			
			AffineTransform t = AffineTransform.getTranslateInstance(x, y);

			t.concatenate(at);			
			t.translate(-x, -y);
			t.translate(current_x_translate, current_y_translate);
			t.rotate(theta);
			// t.concatenate(rat);
			svgCanvas.setRenderingTransform(t);
			
		}
	}
	
	public void setRotateTheta(double theta){
		this.theta = theta;
	}
	
	public Point2D.Double getCurrentTranslation(){
		return new Point2D.Double(current_x_translate, current_y_translate);
	}
	
	public void setCurrentTranslation(Point2D.Double trans){
		current_x_translate = trans.x;
		current_y_translate = trans.y;
		zoom(current_zoom_factor);
	}

}

// Applet:
// batik-awt-util.jar,batik-bridge.jar,batik-css.jar,batik-dom.jar,batik-ext.jar,batik-gvt.jar,batik-parser.jar,
// batik-svg-dom.jar,batik-script.jar,batik-swing.jar,batik-util.jar,batik-xml.jar,xml-apis-dom3.jar

