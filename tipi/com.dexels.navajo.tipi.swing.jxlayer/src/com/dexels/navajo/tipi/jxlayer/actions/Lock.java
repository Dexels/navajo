package com.dexels.navajo.tipi.jxlayer.actions;

import java.awt.Container;

import javax.swing.JComponent;

import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.LayerUI;
import org.jdesktop.jxlayer.plaf.effect.BufferedImageOpEffect;
import org.jdesktop.jxlayer.plaf.ext.LockableUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.jxlayer.impl.BusyPainterUI;
import com.jhlabs.image.BlurFilter;

public class Lock extends TipiAction {

	private static final long serialVersionUID = 2449939873779364275L;
	
	private final static Logger logger = LoggerFactory.getLogger(Lock.class);
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
		
		logger.info("Effect: "+effect+" value: "+value);
		
		stc.runSyncInEventThread(new Runnable(){

			@Override
			@SuppressWarnings("unchecked")
			public void run() {
				JComponent jc = (JComponent)container;
				Container parent = jc.getParent();
				// checked if this component has been locked before
				if(parent instanceof JXLayer) {
					logger.info("Layer present");
					JXLayer<JComponent> jj = (JXLayer<JComponent>) parent;
					LayerUI<JComponent> luj = null;
					if(value) {
						luj = getLockableUI(effect);
				        jj.setUI(luj);
					} else {
						luj = jj.getUI();
					}
			        if(luj instanceof LockableUI) {
			        	logger.info("Lockable ui detected for: "+value);
						LockableUI lu = (LockableUI)luj;
						lu.setLocked(value);
						jj.getParent().repaint();
						jj.getParent().invalidate();
						
					} else {
						logger.info("No lockable ui detected. Odd."); 
					
					}
				} else {
					if(!value) {
						// request unlock, but never locked in the first place
						logger.info("ODD unlock");
						return;
					}
					// first time: insert layer between components
					parent.remove(jc);
					JXLayer<JComponent> layer = new JXLayer<JComponent>(jc);
				    LockableUI blurUI = getLockableUI(effect);
			        layer.setUI(blurUI);
			        parent.add(layer);
			        blurUI.setLocked(true);
			        parent.doLayout();

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
			logger.info("Busy detected!");
			return new BusyPainterUI();
		}
		return getLockableUI("blur");
	}

}
