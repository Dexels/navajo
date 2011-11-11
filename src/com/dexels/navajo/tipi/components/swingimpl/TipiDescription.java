package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiEventListener;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingLabel;
import com.dexels.navajo.tipi.internal.PropertyComponent;

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
public class TipiDescription extends TipiSwingComponentImpl implements
		PropertyComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2476219276411155608L;
	private TipiSwingLabel myLabel;
	private String myPropertyName;
	private Property myProperty;

	public Object createContainer() {
		myLabel = new TipiSwingLabel(this);
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
		if (name.equals("propertyName")) {
			myPropertyName = (String) object;
			return;
		}

		super.setComponentValue(name, object);
	}

	public void addTipiEventListener(TipiEventListener listener) {
		// TODO Auto-generated method stub

	}

	public Property getProperty() {
		return myProperty;
	}

	public String getPropertyName() {
		return myPropertyName;
	}

	public void setProperty(Property p) {
		myProperty = p;
		myLabel.setText(p.getDescription());
	}
}
