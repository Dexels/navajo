/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JMenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingMenuItem;


public class TipiMenuItem extends TipiSwingComponentImpl {
	private static final long serialVersionUID = -6655528844459688735L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiMenuItem.class);
	private TipiSwingMenuItem myItem;

	private boolean iAmEnabled = true;

	@Override
	public Object createContainer() {
		myItem = new TipiSwingMenuItem();
		// TipiHelper th = new TipiSwingHelper();
		// th.initHelper(this);
		// addHelper(th);
		myItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setWaitCursor(true);
				disableAction(); // Disable immediately
				
				performTipiEvent("onActionPerformed", null, false,
						new Runnable() {
							@Override
							public void run() {
								setWaitCursor(false);
							}
						});

			}
		});
		return myItem;
	}

	@Override
	protected void addedToParent() {
		JMenuItem swingContainer = (JMenuItem) getSwingContainer();
		logger.debug("Toplevel:" + swingContainer.getTopLevelAncestor());
		super.addedToParent();
	}

	public void setWaitCursor(final boolean b) {
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				Container cc = getSwingContainer();
				if (!(cc instanceof JComponent)) {
					return;
				}
				JMenuItem jj = (JMenuItem) cc;
				// jj.getTopLevelAncestor()
				if (jj.getTopLevelAncestor() != null) {

					jj.getTopLevelAncestor().setCursor(
							b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)
									: Cursor.getDefaultCursor());
				}
			}
		});

	}

	/**
	 * Sort of legacy. Don't really know what to do with this one.
	 */
	@Override
	public void eventStarted(TipiExecutable te, Object event) {
	    // We should already be disabled, but just to be sure we do it again
		disableAction();
	}

    protected void disableAction() {
        if (Container.class.isInstance(getContainer())) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					// enabled = ( (Container) getContainer()).isEnabled();
					getSwingContainer().setEnabled(false);
				}
			});
		}
    }

	@Override
	public void eventFinished(TipiExecutable te, Object event) {
		if (Container.class.isInstance(getContainer())) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					((Container) getContainer()).setEnabled(iAmEnabled);
				}
			});
		}
	}
}
