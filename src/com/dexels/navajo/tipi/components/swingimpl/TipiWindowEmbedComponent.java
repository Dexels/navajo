/*
 * Created on Mar 21, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.beans.*;
import java.lang.reflect.*;

import javax.swing.*;
import javax.swing.event.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.embed.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

public class TipiWindowEmbedComponent extends TipiEmbedComponent {

	public Object createContainer() {
	
		final JInternalFrame panel;
		panel = new EmbeddedTipiFrame();
		panel.setClosable(true);
		panel.setMaximizable(true);
		panel.setIconifiable(true);
		panel.setResizable(true);
		panel.setLayout(new BorderLayout());
		panel.setVisible(true);
		panel.setSize(500, 300);
		stc = new TipiSwingStandaloneContainer((SwingTipiContext) getContext());
		((SwingTipiContext)stc.getContext()).setOtherRoot(panel);
		stc.getContext().setDefaultTopLevel(this);
		panel.addInternalFrameListener(new InternalFrameAdapter(){
			public void internalFrameClosing(InternalFrameEvent arg0) {
				disposeComponent();
				stc.shutDownTipi();
				getContext().disposeTipiComponent(TipiWindowEmbedComponent.this);
			}

		});
		stc.getContext().addShutdownListener(new ShutdownListener(){

			public void contextShutdown() {
				runSyncInEventThread(new Runnable(){

					public void run() {
						panel.setVisible(false);
						panel.dispose();
					}});
				
			}});
		
	
		stc.getContext().addNavajoListener(new TipiNavajoListener(){

			public void navajoReceived(Navajo n, String service) {
				System.err.println("Nabacho: "+service);
				try {
					myContext.injectNavajo(service, n);
					Navajo navajoList = myContext.createNavajoListNavajo();
					myContext.injectNavajo("NavajoListNavajo", navajoList);
					
				} catch (TipiBreakException e) {
					e.printStackTrace();
				} catch (NavajoException e) {
					e.printStackTrace();
				}
				
			}

			public void navajoSent(Navajo n, String service) {
				
			}});
		//stc.setRootComponent(panel);
		return panel;
	}

	public void runAsyncInEventThread(Runnable runnable) {
		if(SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
				SwingUtilities.invokeLater(runnable);
		}
	}

	public void runSyncInEventThread(Runnable runnable) {
		if(SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(runnable);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	
}
