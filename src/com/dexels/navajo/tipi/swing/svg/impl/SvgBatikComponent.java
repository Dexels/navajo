package com.dexels.navajo.tipi.swing.svg.impl;

//import java.awt.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.beans.*;
import java.net.*;
import java.util.*;

import org.apache.batik.anim.timing.*;
import org.apache.batik.bridge.*;
import org.apache.batik.bridge.SVGAnimationEngine.*;
import org.apache.batik.dom.anim.*;
import org.apache.batik.dom.svg.*;
import org.apache.batik.swing.*;
import org.apache.batik.swing.gvt.*;
import org.apache.batik.swing.svg.*;
import org.apache.batik.util.*; //import org.w3c.dom.events.*;
import org.w3c.dom.*;
import org.w3c.dom.events.*;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.svg.*;

import com.dexels.navajo.tipi.swing.svg.*;

public class SvgBatikComponent extends SvgBaseComponent {
	protected JSVGCanvas svgCanvas = null;
	private final ArrayList<SvgAnimationListener> mySvgAnimationListeners = new ArrayList<SvgAnimationListener>();
	private final ArrayList<SvgMouseListener> mySvgMouseListeners = new ArrayList<SvgMouseListener>();
	private BridgeContext bridgeContext;
	private String myRegisteredIdList;
	private SVGSVGElement myRootElement;

	public SvgBatikComponent() {
		setLayout(new BorderLayout());
	}

	public void setBounds(Rectangle r) {
		// TODO Auto-generated method stub
		super.setBounds(r);
		if (svgCanvas != null) {
			svgCanvas.setBounds(r);
		}
		if (myRootElement != null) {
			System.err.println("Setting bounds to: " + r.getSize());
			UpdateManager um = svgCanvas.getUpdateManager();
			if (um == null) {
				return;
			}
			myRootElement.setAttribute("width", "" + r.width);
			myRootElement.setAttribute("height", "" + r.height);
		}
	}

	public void fireAnimation(String animId) {
		// TODO Auto-generated method stub
		SVGOMAnimationElement ee = (SVGOMAnimationElement) svgCanvas
				.getSVGDocument().getElementById(animId);
		// svgCanvas.getSVGDocument().get
		UpdateManager um = svgCanvas.getUpdateManager();
		if (um == null) {
			return;
		}

		// SVGContext s = ee.getSVGContext();
		// System.err.println("S: "+s+" "+s.getClass());
		bridgeContext = um.getBridgeContext();
		// SVGAnimationElementBridge saeb = (SVGAnimationElementBridge)s;
		// saeb.initializeAnimation();
		// saeb.initializeTimedEvent();
		// System.err.println("CONTEXT:
		// "+bridgeContext.getAnimationEngine().hasStarted());
		// boolean b3= ee.endElement();
		// bridgeContext.getAnimationEngine().
		// bridgeContext.getAnimationEngine().setCurrentTime(bridgeContext.getAnimationEngine().getCurrentTime());
		// System.err.println("getCurrentTime: "+ee.getCurrentTime());
		// System.err.println("getStartTime: "+ee.getStartTime());
		// ee.setAttribute("begin", "0");
		// System.err.println("stop "+b3);
		System.err.println("IS paused: "
				+ bridgeContext.getAnimationEngine().isPaused());
		boolean b = ee.beginElement();
		// TimedElement te = AnimationSupport.getTimedElementById(animId, ee);
		// System.err.println("TE: "+te);
		// System.err.println("boing? "+b);
		bridgeContext.getAnimationEngine().startElement(animId);
		// te.beginElement();

		// if (um != null) {
		// RunnableQueue q = um.getUpdateRunnableQueue();
		// AnimationTickRunnable animationTickRunnable = new
		// AnimationTickRunnable(q, this);
		// q.setIdleRunnable(animationTickRunnable);
		// }
		// svgCanvas.getUpdateManager().

		// ee.beginElement();
		// bridgeContext.getAnimationEngine().addAnimation(arg0, arg1, arg2,
		// arg3, arg4)
	}

	public void init(URL u) {
		if (svgCanvas != null) {
			remove(svgCanvas);
		}
		svgCanvas = new JSVGCanvas();

		add(svgCanvas);
		// svgCanvas.setEnableImageZoomInteractor(true);
		// svgCanvas.getInteractors().add(new AbstractZoomInteractor() {
		// public boolean startInteraction(InputEvent ie) {
		// int mods = ie.getModifiers();
		// return ie.getID() == MouseEvent.MOUSE_PRESSED && (mods &
		// InputEvent.BUTTON1_MASK) != 0;
		// }
		// } );

		svgCanvas.setRecenterOnResize(true);
		svgCanvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		System.err.println("Loading: " + u.toString());

		svgCanvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {
			public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
			}

			public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
				System.err.println("Document Loaded.");
				if (myRegisteredIdList != null) {
					StringTokenizer st = new StringTokenizer(myRegisteredIdList, ",");
					while (st.hasMoreElements()) {
						String elem = (String) st.nextElement();
						registerId(elem);
						System.err.println("Registered: " + elem);
					}
				}
				myRootElement = e.getSVGDocument().getRootElement();
			}

		});

		svgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {
			public void gvtBuildStarted(GVTTreeBuilderEvent e) {
				System.err.println("Build Started...");
			}

			public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
				System.err.println("Build Done.");
			}
		});

		svgCanvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
			public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
				System.err.println("Rendering Started...");
			}

			public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
				System.err.println("");
			}
		});
		// svgCanvas.addPropertyChangeListener(arg0, arg1)
		svgCanvas.setURI(u.toString());
		// svgCanvas.setURI(BatikTest.class.getClassLoader().getResource("Orc.svg").toString());
	}

	protected void registerAnimationEvents(SVGElement ee) {
		((EventTarget) ee).addEventListener("endEvent", new EventListener() {

			public void handleEvent(Event oy) {
				String targetId = ((AnimationTarget) oy.getCurrentTarget())
						.getElement().getAttribute("id");
				String animationId = ((AnimationTarget) oy.getTarget())
						.getElement().getAttribute("id");
				fireOnAnimationEnded(animationId, targetId);

			}
		}, true);
		((EventTarget) ee).addEventListener("beginEvent", new EventListener() {
			public void handleEvent(Event oy) {
				String targetId = ((AnimationTarget) oy.getCurrentTarget())
						.getElement().getAttribute("id");
				String animationId = ((AnimationTarget) oy.getTarget())
						.getElement().getAttribute("id");
				fireOnAnimationStarted(animationId, targetId);
			}
		}, true);
	}

	protected void registerEvents(SVGElement ee) {
		((EventTarget) ee).addEventListener("click", new EventListener() {
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget())
						.getAttribute("id");
				fireOnMouseClick(targetId);
			}
		}, true);

		((EventTarget) ee).addEventListener("mouseup", new EventListener() {
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget())
						.getAttribute("id");
				fireOnMouseUp(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("mousedown", new EventListener() {
			public void handleEvent(Event oy) {
				String currentId = ((SVGElement) oy.getCurrentTarget())
						.getAttribute("id");
				String targetId = ((AnimationTarget) oy.getTarget())
						.getElement().getAttribute("id");
				System.err.println("Current: " + currentId + " target: "
						+ targetId);
				fireOnMouseDown(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("mouseover", new EventListener() {
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget())
						.getAttribute("id");
				fireOnMouseOver(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("mouseout", new EventListener() {
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget())
						.getAttribute("id");
				fireOnMouseOut(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("mousemove", new EventListener() {
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget())
						.getAttribute("id");
				fireOnMouseMove(targetId);
			}
		}, true);
		((EventTarget) ee).addEventListener("activate", new EventListener() {
			public void handleEvent(Event oy) {
				String targetId = ((SVGElement) oy.getCurrentTarget())
						.getAttribute("id");
				fireActivate(targetId);
			}
		}, false);

	}

	// public void onClick(String targetId) {
	// System.err.println("Component clicked: "+targetId);
	// fireAnimation("animout");
	// }
	
	
	protected void fireActivate(String targetId) {
		for (Iterator<SvgMouseListener> iterator = mySvgMouseListeners
				.iterator(); iterator.hasNext();) {
			iterator.next().onActivate(targetId);
		}
	}
	
	protected void fireOnMouseMove(String targetId) {
		for (Iterator<SvgMouseListener> iterator = mySvgMouseListeners
				.iterator(); iterator.hasNext();) {
			iterator.next().onMouseMove(targetId);
		}
	}

	protected void fireOnMouseOver(String targetId) {
		for (Iterator<SvgMouseListener> iterator = mySvgMouseListeners
				.iterator(); iterator.hasNext();) {
			iterator.next().onMouseOver(targetId);
		}
	}

	protected void fireOnMouseOut(String targetId) {
		for (Iterator<SvgMouseListener> iterator = mySvgMouseListeners
				.iterator(); iterator.hasNext();) {
			iterator.next().onMouseOut(targetId);
		}
	}

	protected void fireOnMouseDown(String targetId) {
		for (Iterator<SvgMouseListener> iterator = mySvgMouseListeners
				.iterator(); iterator.hasNext();) {
			iterator.next().onMouseDown(targetId);
		}
	}

	protected void fireOnMouseUp(String targetId) {
		for (Iterator<SvgMouseListener> iterator = mySvgMouseListeners
				.iterator(); iterator.hasNext();) {
			iterator.next().onMouseUp(targetId);
		}
	}

	protected void fireOnMouseClick(String targetId) {
		for (Iterator<SvgMouseListener> iterator = mySvgMouseListeners
				.iterator(); iterator.hasNext();) {
			iterator.next().onClick(targetId);
		}
	}

	protected void fireOnAnimationStarted(String animationId, String targetId) {
		for (Iterator<SvgAnimationListener> iterator = mySvgAnimationListeners
				.iterator(); iterator.hasNext();) {
			iterator.next().onAnimationStarted(animationId, targetId);
		}
	}

	protected void fireOnAnimationEnded(String animationId, String targetId) {
		for (Iterator<SvgAnimationListener> iterator = mySvgAnimationListeners
				.iterator(); iterator.hasNext();) {
			iterator.next().onAnimationEnded(animationId, targetId);
		}
	}

	public void registerId(String id) {
		SVGElement ee = (SVGElement) svgCanvas.getSVGDocument().getElementById(
				id);
		if (ee instanceof SVGAnimationElement) {

		}
		registerEvents(ee);
		registerAnimationEvents(ee);

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

	@Override
	public void setRegisteredIds(String object) {
		// TODO Auto-generated method stub
		myRegisteredIdList = object;

	}

}
// Applet:
// batik-awt-util.jar,batik-bridge.jar,batik-css.jar,batik-dom.jar,batik-ext.jar,batik-gvt.jar,batik-parser.jar,
// batik-svg-dom.jar,batik-script.jar,batik-swing.jar,batik-util.jar,batik-xml.jar,xml-apis-dom3.jar

