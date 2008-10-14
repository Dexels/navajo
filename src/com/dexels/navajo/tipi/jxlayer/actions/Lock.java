package com.dexels.navajo.tipi.jxlayer.actions;

import java.awt.*;

import javax.swing.*;

import org.jdesktop.jxlayer.*;
import org.jdesktop.jxlayer.plaf.*;
import org.jdesktop.jxlayer.plaf.effect.*;
import org.jdesktop.jxlayer.plaf.ext.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.jxlayer.impl.*;
import com.jhlabs.image.*;

public class Lock extends TipiAction {

	@Override
	protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
		SwingTipiContext stc = (SwingTipiContext)myContext;
		final boolean value = (Boolean)getEvaluatedParameterValue("value", event);
		final String effect = (String)getEvaluatedParameterValue("effect", event);
	
		TipiComponent targetComponent = (TipiComponent)getEvaluatedParameterValue("target", event);
		if(targetComponent==null) {
			throw new TipiException("Unable to locate target: "+getParameter("target"));
		}
		final Object container = targetComponent.getContainer();
		if(container==null || !(container instanceof JComponent)) {
			throw new TipiException("Invalid tipi component for locking: "+getParameter("target"));
		}
		
		System.err.println("Effect: "+effect+" value: "+value);
		
		stc.runSyncInEventThread(new Runnable(){

			@SuppressWarnings("unchecked")
			public void run() {
				JComponent jc = (JComponent)container;
				Container parent = jc.getParent();
				// checked if this component has been locked before
				if(parent instanceof JXLayer) {
					System.err.println("Layer present");
					JXLayer<JComponent> jj = (JXLayer<JComponent>) parent;
					// only check for the effect when locking
					LayerUI<JComponent> luj = null;
					if(value) {
						luj = getLockableUI(effect);
				        jj.setUI(luj);
					} else {
						luj = jj.getUI();
					}
			        if(luj instanceof LockableUI) {
			        	System.err.println("Lockable ui detected for: "+value);
						LockableUI lu = (LockableUI)luj;
						lu.setLocked(value);
						jj.getParent().repaint();
					} else {
						System.err.println("No lockable ui detected. Odd."); 
					
					}
				} else {
					if(!value) {
						// request unlock, but never locked in the first place
						System.err.println("ODD unlock");
						return;
					}
					// first time: insert layer between components
					parent.remove(jc);
					JXLayer<JComponent> layer = new JXLayer<JComponent>(jc);
				    LockableUI blurUI = getLockableUI(effect);
			        layer.setUI(blurUI);
			        parent.add(layer);
			        blurUI.setLocked(true);
			        

				}
		        
			}});
			
	}

	private LockableUI getLockableUI(String effect) {
		if(effect==null) {
			effect="busy";
		}
		if(effect.equals("blur")) {
			return new LockableUI(new BufferedImageOpEffect(new BlurFilter()));
		}
		if(effect.equals("busy")) {
			System.err.println("Busy detected!");
			return new BusyPainterUI();
		}
		return getLockableUI("blur");
	}

}
