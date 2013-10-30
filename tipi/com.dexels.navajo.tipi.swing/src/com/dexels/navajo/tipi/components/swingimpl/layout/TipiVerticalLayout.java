package com.dexels.navajo.tipi.components.swingimpl.layout;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingGridBagConstraints;
import com.dexels.navajo.tipi.tipixml.XMLElement;

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
public class TipiVerticalLayout extends TipiLayoutImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -875883352618523091L;

	@Override
	// GridBagLayout layout = null;
	protected void setValue(String name, TipiValue tv) {
		/**
		 * @todo Implement this com.dexels.navajo.tipi.TipiLayout abstract method
		 */
	}

	@Override
	public Object parseConstraint(String text, int index) {
		// TipiSwingGridBagConstraints gt = new
		// TipiSwingGridBagConstraints(text);
		return createDefaultConstraint(index);
	}

	@Override
	public void createLayout() {
		GridBagLayout lay = new GridBagLayout();
		setLayout(lay);

	}

	@Override
	public void loadLayout(XMLElement def, final TipiComponent t)
			throws TipiException {
		super.loadLayout(def, t);
		myContext.runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				Container c = ((Container) t.getContainer());
				c.add(new JLabel(""), new TipiSwingGridBagConstraints(0, 999,
						1, 1, 0, 1, GridBagConstraints.NORTHWEST,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0),
						0, 0));
			}
		});

	}

	@Override
	public Object createDefaultConstraint(int index) {

		return new TipiSwingGridBagConstraints(0, index, 1, 1, 0, 0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
	}
}
