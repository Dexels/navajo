/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on Mar 21, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiEmbedComponent;
import com.dexels.navajo.tipi.components.core.ShutdownListener;
import com.dexels.navajo.tipi.components.swingimpl.embed.TipiSwingStandaloneContainer;
import com.dexels.navajo.tipi.components.swingimpl.swing.EmbeddedTipiFrame;

import tipiswing.SwingTipiApplicationInstance;

@Deprecated
public class TipiWindowEmbedComponent extends TipiEmbedComponent {

	private static final long serialVersionUID = -7354561201460963130L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiWindowEmbedComponent.class);
	private JInternalFrame panel = null;

	@Override
	public Object createContainer() {
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				panel = new EmbeddedTipiFrame();
				panel.setClosable(true);
				panel.setMaximizable(true);
				panel.setIconifiable(true);
				panel.setResizable(true);
				panel.setLayout(new BorderLayout());
				panel.setVisible(true);
				panel.setSize(500, 300);
				stc = new TipiSwingStandaloneContainer(
						(SwingTipiApplicationInstance) getContext()
								.getApplicationInstance(),
						(SwingTipiContext) getContext());
				((SwingTipiContext) stc.getContext()).setOtherRoot(panel);
				// stc.getContext().setDefaultTopLevel(TipiWindowEmbedComponent.this);
				panel.addInternalFrameListener(new InternalFrameAdapter() {
					@Override
					public void internalFrameClosing(InternalFrameEvent arg0) {
						disposeComponent();
						stc.shutDownTipi();
						getContext().disposeTipiComponent(
								TipiWindowEmbedComponent.this);
					}

				});
				stc.getContext().addShutdownListener(new ShutdownListener() {

					@Override
					public void contextShutdown() {
						runSyncInEventThread(new Runnable() {

							@Override
							public void run() {
								panel.setVisible(false);
								panel.dispose();
							}
						});

					}
				});

				// stc.getContext().addNavajoListener(new TipiNavajoListener(){
				//
				// public void navajoReceived(Navajo n, String service) {
				// logger.debug("Nabacho: "+service);
				// try {
				// myContext.injectNavajo(service, n);
				// Navajo navajoList = myContext.createNavajoListNavajo();
				// myContext.injectNavajo("NavajoListNavajo", navajoList);
				//
				// } catch (TipiBreakException e) {
				// logger.error("Error detected",e);
				// } catch (NavajoException e) {
				// logger.error("Error detected",e);
				// }
				//
				// }
				//
				// public void navajoSent(Navajo n, String service) {
				//
				// }});
				// stc.setRootComponent(panel);
			}
		});

		return panel;
	}

	@Override
	public void runAsyncInEventThread(Runnable runnable) {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
			SwingUtilities.invokeLater(runnable);
		}
	}

	@Override
	public void runSyncInEventThread(Runnable runnable) {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(runnable);
			} catch (InterruptedException e) {
				logger.error("Error detected",e);
			} catch (InvocationTargetException e) {
				logger.error("Error detected",e);
			}
		}
	}

}
