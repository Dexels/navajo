package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import tipi.MainApplication;

public class TipiApplet extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2086609397645022166L;
	private SwingTipiContext myContext;

	public TipiApplet() throws Exception {
	}

	public void stop() {
		myContext.shutdown();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.stop();
	}

	public void destroy() {
		myContext.shutdown();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.destroy();
	}

	public void init() {
		super.init();
		List<String> arguments = new ArrayList<String>();
		getContentPane().setBackground(Color.orange);

//		String tipiFile = getParameter("tipiFile");
//		List<String> arrrgs = null;
//		String definition = null;
//		if (tipiFile != null) {
//			URL f;
//			try {
//				f = new URL(tipiFile);
//				arrrgs = MainApplication.parseBundleUrl(f);
//				definition = arrrgs.get(arrrgs.size() - 1);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

		String init = this.getParameter("init");
		// System.err.println("LocationOnScreen: "+getLocationOnScreen());
		String laf = this.getParameter("tipilaf");
		// System.err.println("Applet init laf: "+laf);
		String tipiCodeBase = this.getParameter("tipiCodeBase");
		String resourceCodeBase = this.getParameter("resourceCodeBase");
		// System.err.println("my codebase: "+getCodeBase());
		String switches = getParameter("args");
		if (switches != null) {
			StringTokenizer st = new StringTokenizer(switches, " ");
			while (st.hasMoreTokens()) {
				arguments.add(st.nextToken());
			}
		}

		if (tipiCodeBase != null) {
			try {
				String tipiCode = new URL(getCodeBase(), tipiCodeBase)
						.toString();
				arguments.add("tipiCodeBase=" + tipiCode);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		if (resourceCodeBase != null) {
			try {
				String resourceCode = new URL(getCodeBase(), resourceCodeBase)
						.toString();
				arguments.add("resourceCodeBase=" + resourceCode);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		System.err.println("Laf: " + laf);
		if (laf != null) {
			try {
				arguments.add("tipilaf=" + laf);
				UIManager.setLookAndFeel(laf);
				SwingUtilities.updateComponentTreeUI(this);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		if (init != null) {
			arguments.add(init);
		} else {
			init = "init.xml";
			arguments.add(init);
			// throw new
			// IllegalArgumentException("Missing argument: Add 'init' argument to applet.");
		}
		// TODO Add support for a bundleContext
		try {
			MainApplication
					.initializeSwingApplication(null, arguments, init, this,
							null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void reload() {
		myContext.shutdown();
	}

	public Point getCenteredPoint(Dimension dlgSize) {
		Point base = getLocationOnScreen();
		Dimension frmSize = new Dimension(getWidth(), getHeight());
		int x = Math.max(0, (frmSize.width - dlgSize.width) / 2 + base.x);
		int y = Math.max(0, (frmSize.height - dlgSize.height) / 2 + base.y);
		return new Point(x, y);

	}

}
