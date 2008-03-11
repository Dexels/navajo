package com.dexels.navajo.tipi.components.rich;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.RootPaneContainer;

import com.dexels.navajo.rich.components.FlipPanel;
import com.dexels.navajo.rich.components.PerspectiveImagePanel;
import com.dexels.navajo.rich.components.PerspectiveTransform;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingPanel;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiFlipPanel extends TipiPanel {
	private FlipPanel myPanel;	
	private String direction;
	private int speed = 750;	
	
	public TipiFlipPanel() {
		
	}
	
	public Object createContainer() {
	  myPanel = new FlipPanel();
	  myPanel.setOpaque(false);
	  return myPanel;
  }

	@Override
	public void addToContainer(final Object c, Object constraints) {
		runSyncInEventThread(new Runnable(){

			public void run() {
				myPanel.addComponent((JComponent)c);
			}});
	}

	@Override
	public void removeFromContainer(Object c) {
		myPanel.removeComponent((JComponent)c);
	}

	@Override
	public void setContainerLayout(Object layout) {
		//nop
	}

	public void flip(){
		myPanel.flipForwards();
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public int getSpeed(){
		return this.speed;
	}
	
	@Override
	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
		if("flip".equals(name)){
			runSyncInEventThread(new Runnable(){
				public void run(){
					flip();
				}
			});			
		}

	}
	
	public static void main(String[] args){
		new TipiFlipPanel();
	}
	
	
	
}
