/*
 * Created on Mar 21, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

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
		stc = new TipiStandaloneContainer();
		((SwingTipiContext)stc.getContext()).setOtherRoot(panel);
		
		panel.addInternalFrameListener(new InternalFrameAdapter(){
			public void internalFrameClosing(InternalFrameEvent arg0) {
				disposeComponent();
				stc.shutDownTipi();
				getContext().disposeTipiComponent(TipiWindowEmbedComponent.this);
			}

		});
		stc.getContext().addShutdownListener(new ShutdownListener(){

			public void contextShutdown() {
				panel.setVisible(false);
				panel.dispose();
			}});
		//stc.setRootComponent(panel);
		return panel;
	}
}
