package com.dexels.navajo.tipi.swing.svg;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.batik.bridge.UpdateManager;
import org.apache.batik.bridge.UpdateManagerEvent;
import org.apache.batik.bridge.UpdateManagerListener;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.gvt.GVTTreeRendererListener;
import org.apache.batik.swing.svg.AbstractJSVGComponent;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderListener;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderListener;
import org.apache.batik.swing.svg.SVGLoadEventDispatcherEvent;
import org.apache.batik.swing.svg.SVGLoadEventDispatcherListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGElement;

public class ViewBoxTest extends WindowAdapter implements
  SVGDocumentLoaderListener, GVTTreeBuilderListener,
  SVGLoadEventDispatcherListener, GVTTreeRendererListener,
  UpdateManagerListener {

    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(getClass());
    
	private final static Logger logger = LoggerFactory
			.getLogger(ViewBoxTest.class);
    private final String url;
    private final JFrame frame;
    private final JSVGCanvas canvas;

    private SVGDocument dom;

    public ViewBoxTest(String url) {
        this.url = url;
        frame = new JFrame("ViewBoxTest: "
          + url.substring(url.lastIndexOf('/') + 1));
        canvas = new JSVGCanvas(null, true, true);
        canvas.setDocumentState(AbstractJSVGComponent.ALWAYS_DYNAMIC);
        canvas.addSVGDocumentLoaderListener(this);
        canvas.addGVTTreeBuilderListener(this);
        canvas.addGVTTreeRendererListener(this);
        canvas.addSVGLoadEventDispatcherListener(this);
        canvas.addUpdateManagerListener(this);
        canvas.setFocusable(true);
        frame.addWindowListener(this);
    }

    public void go() {
        canvas.setURI(this.url);
    }

    public void cancel() {
        logger.info("Something went wrong");
    }

    protected void updateSVG(final Runnable rable) {
        UpdateManager updateManager = canvas.getUpdateManager();
        updateManager.getUpdateRunnableQueue().invokeLater(
          new Runnable() {
            @Override
			public void run() {
                try {
                    rable.run();
                } catch (Throwable t) {
                    t.printStackTrace(System.err);
                    System.exit(1);
                }
            }
        });
    }

    protected void ready() {

        // Get SVG DOM
        this.dom = canvas.getSVGDocument();

        // Create mutator
        final Runnable mutator = new Runnable() {
            @Override
			public void run() {
                SVGElement svg = (SVGElement)dom.getDocumentElement();
                String oldViewBox = svg.getAttribute("viewBox");
                String newViewBox = oldViewBox + " ";
                logger.info("CHANGING VIEWBOX from \""
                    + oldViewBox + "\" to \"" + newViewBox + "\"");
                svg.setAttribute("viewBox", newViewBox);
            }
        };

        // Start thread to pause and then mutate
        new Thread() {
            @Override
			public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                updateSVG(mutator);
            }
        }.start();
    }

    public void showFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
			public void run() {
                frame.getContentPane().add(canvas);
                // NOTE: change size this to see different effects
                frame.setPreferredSize(new java.awt.Dimension(800, 800));
                frame.pack();
                Dimension size = frame.getSize();
                Point center = GraphicsEnvironment
                  .getLocalGraphicsEnvironment().getCenterPoint();
                int x = (int)(center.getX() - size.getWidth() / 2);
                int y = (int)(center.getY() - size.getHeight() / 2);
                frame.setLocation(new Point(x, y));
                frame.setVisible(true);
                frame.toFront();
            }
        });
    }

    // SVGDocumentLoaderListener methods

    @Override
	public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
        log.debug("Document loading started");
    }

    @Override
	public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
        log.debug("Document loading completed");
    }

    @Override
	public void documentLoadingCancelled(SVGDocumentLoaderEvent e) {
        log.debug("Document loading canceled");
    }

    @Override
	public void documentLoadingFailed(SVGDocumentLoaderEvent e) {
        log.debug("Document loading failed: " + e);
        cancel();
    }

    // GVTTreeBuilderListener methods

    @Override
	public void gvtBuildStarted(GVTTreeBuilderEvent e) {
        log.debug("GVT build started");
    }

    @Override
	public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
        log.debug("GVT build completed");
        showFrame();
    }

    @Override
	public void gvtBuildCancelled(GVTTreeBuilderEvent e) {
        log.debug("GVT build canceled");
    }

    @Override
	public void gvtBuildFailed(GVTTreeBuilderEvent e) {
        log.debug("GVT build failed: " + e);
        cancel();
    }

    // GVTTreeRendererListener methods

    @Override
	public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
        log.debug("GVT rendering preparing");
    }

    @Override
	public void gvtRenderingStarted(GVTTreeRendererEvent e) {
        log.debug("GVT rendering started");
    }

    @Override
	public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
        log.debug("GVT rendering complete");
        ready();
    }

    @Override
	public void gvtRenderingCancelled(GVTTreeRendererEvent e) {
        log.debug("GVT rendering canceled");
    }

    @Override
	public void gvtRenderingFailed(GVTTreeRendererEvent e) {
        log.debug("GVT rendering failed: " + e);
        cancel();
    }

    // SVGLoadEventDispatcherListener methods

    @Override
	public void svgLoadEventDispatchCancelled(SVGLoadEventDispatcherEvent e) {
        log.debug("Load event dispatch cancelled");
    }

    @Override
	public void svgLoadEventDispatchCompleted(SVGLoadEventDispatcherEvent e) {
        log.debug("Load event dispatch completed");
    }

    @Override
	public void svgLoadEventDispatchFailed(SVGLoadEventDispatcherEvent e) {
        log.debug("Load event dispatch failed: " + e);
    }

    @Override
	public void svgLoadEventDispatchStarted(SVGLoadEventDispatcherEvent e) {
        log.debug("Load event dispatch started");
    }

    // UpdateManagerListener

    @Override
	public void managerStarted(UpdateManagerEvent e) {
        log.debug("Update manager started");
    }

    @Override
	public void managerSuspended(UpdateManagerEvent e) {
        log.debug("Update manager suspended");
    }

    @Override
	public void managerResumed(UpdateManagerEvent e) {
        log.debug("Update manager resumed");
    }

    @Override
	public void managerStopped(UpdateManagerEvent e) {
        log.debug("Update manager stopped");
    }

    @Override
	public void updateStarted(UpdateManagerEvent e) {
        //log.debug("Update manager started");
    }

    @Override
	public void updateCompleted(UpdateManagerEvent e) {
        //log.debug("Update manager completed");
    }

    @Override
	public void updateFailed(UpdateManagerEvent e) {
        log.debug("Update manager failed: " + e);
    }

    // WindowListener methods

    @Override
	public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

}