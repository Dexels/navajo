package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiEventListener;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingLabel;
import com.dexels.navajo.tipi.internal.PropertyComponent;


public class TipiDescription extends TipiSwingComponentImpl implements
		PropertyComponent {
	private static final long serialVersionUID = -2476219276411155608L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiDescription.class);
	
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
				logger.debug("Binary icon found");
				return ii;
			} catch (IOException e) {
				logger.error("Error detected",e);
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

	@Override
	public Boolean isDirty() {
		// always not dirty
		return Boolean.FALSE;
	}

	@Override
	public void setDirty(Boolean b) {
		// ignore dirty
		
	}
}
