package com.dexels.navajo.tipi.swing.svg;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import org.w3c.dom.svg.*;
import org.xml.sax.*;

import com.aetrion.flickr.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.flickr.*;
import com.dexels.navajo.tipi.swing.svg.impl.*;

public class TipiSlideshowComponent extends TipiSvgComponent {

	 
	
	private SvgBatikComponent svgPanel;
	private int index = 0;
	private String myTag = null;
	@Override
	
	public Object createContainer() {
		svgPanel = new SvgBatikComponent();
		svgPanel.addSvgAnimationListener(this);
		svgPanel.addSvgMouseListener(this);
		svgPanel.init(getClass().getResource("picture.svg"));
		svgPanel.addSvgDocumentListener(new SvgDocumentAdapter(){
			@Override
			public void onDocumentLoadingFinished() {
				String loadUrl = loadUrl();
				if(loadUrl!=null) {
					updateDom(svgPanel.getDocument(),loadUrl);
					svgPanel.runInUpdateQueue(new Runnable(){

						@Override
						public void run() {
							svgPanel.fireAnimation("animin");
							
						}});
					
				}

			}});
		//myComponent.add
		

		
		return svgPanel;
	}



	@Override
	protected void setComponentValue(String name, final Object object) {
		// TODO Auto-generated method stub
		if (name.equals("selected")) {
		}
		if (name.equals("tag")) {
			myTag  = (String)object;
			svgPanel.fireAnimation("animout");
			Thread t = new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					final String uu = loadUrl();
					if(uu!=null) {
						svgPanel.runInUpdateQueue(new Runnable(){
								public void run() {
								updateDom(svgPanel.getDocument(),uu);
								
							}});
					}
			
				}});
			t.start();

		}

		if (name.equals("url")) {
			svgPanel.fireAnimation("animout");
			Thread t = new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					final String uu = (String)object;
					try {
						Thread.sleep(350);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					svgPanel.runInUpdateQueue(new Runnable(){

						@Override
						public void run() {
							updateDom(svgPanel.getDocument(),uu);
							
						}});
				}});
			t.start();

		}		
		
		super.setComponentValue(name, object);
	}

	private void updateDom(SVGDocument document, String loadUrl) {
		if(loadUrl!=null) {
			svgPanel.setAttribute("http://www.w3.org/1999/xlink","picture", "xlink:href", loadUrl);
			System.err.println("URL: "+loadUrl);
			if(loadUrl!=null) {
				svgPanel.moveToFirst("picture");
				svgPanel.runInUpdateQueue(new Runnable(){

					public void run() {
						svgPanel.fireAnimation("animin");
					}});
			}
				
		}
	
//		svgPanel.repaint();
//		SVGElement se = (SVGElement) document.getRootElement().getElementById("picture");
//
//		se.setAttributeNS("xlink", "href", loadUrl);
//		se.setAttribute("xlink:href", loadUrl);
	}



	private String loadUrl() {
		try {
			String url =  PhotoManager.getInstance().getUrl(new String[]{myTag}, index);
			index++;
			return url;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FlickrException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}



	@Override
	protected Object getComponentValue(String name) {
		// TODO Auto-generated method stub

		return super.getComponentValue(name);
	}

}
