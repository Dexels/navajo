/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Dimension;

import javax.swing.SwingConstants;

import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingToolBar;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiToolBar extends TipiSwingDataComponentImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 135588077316182824L;

	@Override
	public Object createContainer() {
		TipiSwingToolBar ts = new TipiSwingToolBar();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		ts.setFloatable(false);
		ts.setMinimumSize(new Dimension(0, 0));
		ts.setOpaque(false);
		ts.setBorderPainted(false);
		// EffectsManager.setEffect(ts, new FadeIn(), TransitionType.APPEARING);
		// EffectsManager.setEffect(ts, new FadeOut(),
		// TransitionType.DISAPPEARING);
		return ts;
	}

	private final void setOrientation(String o) {
		if ("horizontal".equals(o)) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					((TipiSwingToolBar) getContainer())
							.setOrientation(SwingConstants.HORIZONTAL);
				}
			});
		}
		if ("vertical".equals(o)) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					((TipiSwingToolBar) getContainer())
							.setOrientation(SwingConstants.VERTICAL);
				}
			});
		}
	}

	private final void setFloatable(Boolean b) {
		((TipiSwingToolBar) getContainer()).setFloatable(b.booleanValue());
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
		super.setComponentValue(name, object);
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				if ("orientation".equals(name)) {
					setOrientation((String) object);
				}
				if ("floatable".equals(name)) {
					setFloatable((Boolean) object);
				}
			}
		});
	}
}
