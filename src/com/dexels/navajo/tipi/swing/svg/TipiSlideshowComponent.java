package com.dexels.navajo.tipi.swing.svg;

import org.w3c.dom.svg.*;

import com.dexels.navajo.tipi.swing.svg.impl.*;

public class TipiSlideshowComponent extends TipiSvgComponent {

	 
	
	private SvgBatikComponent svgPanel;
	private int index = 0;
	private String myTag = null;
	@Override
	
	public Object createContainer() {
//		svgCanvas.getSVGDocument().getRootElement().getViewBox();

		svgPanel = new SvgBatikComponent();
		svgPanel.addSvgAnimationListener(this);
		svgPanel.addSvgMouseListener(this);
		svgPanel.init(myContext.getResourceURL("com/dexels/navajo/tipi/swing/svg/picture.svg"));
		svgPanel.addSvgDocumentListener(new SvgDocumentAdapter(){
			@Override
			public void onDocumentLoadingFinished() {
				String loadUrl = null;
				if(loadUrl!=null) {
					updateDom(svgPanel.getDocument(),loadUrl);
					svgPanel.runInUpdateQueue(new Runnable(){

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
		if (name.equals("selected")) {
		}
		if (name.equals("tag")) {
			myTag  = (String)object;
			svgPanel.fireAnimation("animout");
			Thread t = new Thread(new Runnable(){

				public void run() {
//					final String uu = loadUrl(myTag);
					if(myTag!=null) {
						svgPanel.runInUpdateQueue(new Runnable(){
								public void run() {
								updateDom(svgPanel.getDocument(),myTag);
								
							}});
					}
			
				}});
			t.start();

		}

		if (name.equals("url")) {
			svgPanel.fireAnimation("animout");
			Thread t = new Thread(new Runnable(){

				public void run() {
					final String uu = (String)object;
					try {
						Thread.sleep(350);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					svgPanel.runInUpdateQueue(new Runnable(){

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
			svgPanel.moveToFirst("picture");
			svgPanel.runInUpdateQueue(new Runnable() {

				public void run() {
					svgPanel.fireAnimation("animin");
				}
			});
		}
	}

}
