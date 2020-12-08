/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingLabel;


public class TipiLabel extends TipiSwingComponentImpl {

	private static final long serialVersionUID = -7871861091449007485L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiLabel.class);
	
	@Override
	public Object createContainer() {
		TipiSwingLabel myLabel = new TipiSwingLabel(this);
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myLabel;
	}

	protected ImageIcon getIcon(Object u) {
		if (u == null) {
			return null;
		}
		if (u instanceof URL) {
			return new ImageIcon((URL) u);
		}
		if (u instanceof Binary) {
			Image i;
			try {
				i = ImageIO.read(((Binary) u).getDataAsStream());
				ImageIcon ii = new ImageIcon(i);
				logger.debug("Binary icon found");
				return ii;
			} catch (IOException e) {
				logger.error("Error detected",e);
			}
		}
		return null;
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
		if (name.equals("text")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					((TipiSwingLabel) getContainer()).setText("" + object);
				}
			});
			((TipiSwingLabel) getContainer()).revalidate();
			return;
		}
		if (name.equals("opaque")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					((TipiSwingLabel) getContainer()).setOpaque((boolean) object);
				}
			});
			((TipiSwingLabel) getContainer()).revalidate();
			return;
		}
		if (name.equals("icon")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					((TipiSwingLabel) getContainer()).setIcon(getIcon(object));
					((TipiSwingLabel) getContainer()).revalidate();
				}
			});
			((TipiSwingLabel) getContainer()).getParent();
			return;
		}
		super.setComponentValue(name, object);
	}

	@Override
	public Object getComponentValue(String name) {
		if (name.equals("text")) {
			return ((TipiSwingLabel) getContainer()).getText();
		}
		if (name.equals("opaque")) {
			return ((TipiSwingLabel) getContainer()).isOpaque();
		}
		if (name.equals("icon")) {
			return ((TipiSwingLabel) getContainer()).getIcon();
		}
		return super.getComponentValue(name);
	}
}
