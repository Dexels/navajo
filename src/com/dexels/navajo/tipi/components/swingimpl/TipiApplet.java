package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import tipi.*;

public class TipiApplet extends JApplet {
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

		String tipiFile=getParameter("tipiFile");
		List<String> arrrgs = null;
		String definition = null;
		if(tipiFile!=null) {
			URL f;
			try {
				f = new URL(tipiFile);
				arrrgs = MainApplication.parseBundleUrl(f);
				definition = arrrgs.get(arrrgs.size()-1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				}
		
		String init = this.getParameter("init");
	//		System.err.println("LocationOnScreen: "+getLocationOnScreen());
		String laf = this.getParameter("tipilaf");
		System.err.println("Applet init laf: "+laf);
		String tipiCodeBase = this.getParameter("tipiCodeBase");
		String resourceCodeBase = this.getParameter("resourceCodeBase");
		System.err.println("my codebase: "+getCodeBase());
		String switches = getParameter("args");
		if(switches!=null) {
			StringTokenizer st = new StringTokenizer(switches," ");
			while (st.hasMoreTokens()) {
				arguments.add(st.nextToken());
			}
		}
	
		if(tipiCodeBase!=null) {
			try {
				String tipiCode = new URL(getCodeBase(),tipiCodeBase).toString();
				arguments.add("tipiCodeBase="+tipiCode);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		if(resourceCodeBase!=null) {
			try {
				String resourceCode = new URL(getCodeBase(),resourceCodeBase).toString();
				arguments.add("resourceCodeBase="+resourceCode);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		System.err.println("Laf: "+laf);
		if(laf!=null) {
			try {
				arguments.add("tipilaf="+laf);
				UIManager.setLookAndFeel(laf);
				SwingUtilities.updateComponentTreeUI(this);
			} catch (Throwable e) {
				e.printStackTrace();
			} 
		}
		if (init!=null) {
			arguments.add(init);
		} else {
			init="init.xml";
			arguments.add(init);
//			throw new IllegalArgumentException("Missing argument: Add 'init' argument to applet.");
		}
		try {
			myContext = MainApplication.initialize(init, null,arguments,this,null);
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
		    int x =  Math.max(0, (frmSize.width - dlgSize.width) / 2 + base.x);
		    int y = Math.max(0, (frmSize.height - dlgSize.height) / 2+ base.y);
		    return new Point(x, y);

		  }
	
}
