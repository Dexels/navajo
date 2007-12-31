package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.internal.HttpResourceLoader;

import tipi.MainApplication;

public class TipiApplet extends JApplet {
	private SwingTipiContext myContext;
	public TipiApplet() throws Exception {
		try {
			UIManager.setLookAndFeel("org.jvnet.substance.SubstanceLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} }

	public void destroy() {
		myContext.shutdown();
		super.destroy();
	}

	public void init() {
		super.init();
		ArrayList arguments = new ArrayList();
		String init = this.getParameter("init");
	//		System.err.println("LocationOnScreen: "+getLocationOnScreen());
		String laf = this.getParameter("tipilaf");
		String tipiCodeBase = this.getParameter("tipiCodeBase");
		String resourceCodeBase = this.getParameter("resourceCodeBase");
		String switches = getParameter("switch");
		StringTokenizer st = new StringTokenizer(switches," ");
		while (st.hasMoreTokens()) {
			arguments.add(st.nextToken());
		}

		if(tipiCodeBase!=null) {
			try {
				String tipiCode = new URL(getCodeBase(),tipiCodeBase).toString();
				arguments.add("-DtipiCodeBase="+tipiCode);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		if(resourceCodeBase!=null) {
			try {
				String resourceCode = new URL(getCodeBase(),resourceCodeBase).toString();
				arguments.add("-DresourceCodeBase="+resourceCode);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		if(laf!=null) {
			try {
				UIManager.setLookAndFeel(laf);
				SwingUtilities.updateComponentTreeUI(this);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

		arguments.add(init);
		try {
			myContext = MainApplication.initialize(init, arguments.toArray(),this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void reload() {
//		TipiComponent tc = myContext.getDefaultTopLevel();
//		if(tc!=null) {
//			tc.disposeComponent();
//		}
//		removeAll();
		myContext.shutdown();
		try {
		// TODO: Setup in applet parameter
			getAppletContext().showDocument(new URL("http://penelope1.dexels.com/sportlink/club"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
//		init();
//		repaint();
	}
	
	 public Point getCenteredPoint(Dimension dlgSize) {
		 	Point base = getLocationOnScreen();
//		 Rectangle r = getRootPaneContainer().getRootPane().getBounds();
		    Dimension frmSize = new Dimension(getWidth(), getHeight());
//		    Point loc = getRootPaneContainer().getRootPane().getLocation();
		    int x =  Math.max(0, (frmSize.width - dlgSize.width) / 2 + base.x);
		    int y = Math.max(0, (frmSize.height - dlgSize.height) / 2+ base.y);
		    return new Point(x, y);

//
//		    if (dlgSize.height>(Toolkit.getDefaultToolkit().getScreenSize().height)) {
//		      dlgSize.height = Toolkit.getDefaultToolkit().getScreenSize().height;
//		      dlg.setSize(dlgSize);
//		    }
//
//		    if (dlgSize.width>Toolkit.getDefaultToolkit().getScreenSize().width) {
//		      dlgSize.width = Toolkit.getDefaultToolkit().getScreenSize().width;
//		      dlg.setSize(dlgSize);
//		   }

		  }
	
}
