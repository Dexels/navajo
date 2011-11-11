package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingLabel;

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
public class TipiLabel extends TipiSwingComponentImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7871861091449007485L;

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
				System.err.println("Binary icon found");
				return ii;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void setComponentValue(final String name, final Object object) {
		if (name.equals("text")) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					((TipiSwingLabel) getContainer()).setText("" + object);
				}
			});
			((TipiSwingLabel) getContainer()).revalidate();
			return;
		}
		if (name.equals("icon")) {
			runSyncInEventThread(new Runnable() {
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

	public Object getComponentValue(String name) {
		if (name.equals("text")) {
			return ((TipiSwingLabel) getContainer()).getText();
		}
		if (name.equals("icon")) {
			return ((TipiSwingLabel) getContainer()).getIcon();
		}
		return super.getComponentValue(name);
	}
}
