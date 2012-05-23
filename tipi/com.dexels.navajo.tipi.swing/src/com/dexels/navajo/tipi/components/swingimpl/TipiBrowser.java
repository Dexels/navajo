package com.dexels.navajo.tipi.components.swingimpl;

import java.io.IOException;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiEditorPane;

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
 * @deprecated Use the Cobra browser. It has much better js and css support
 * @version 1.0
 */
@Deprecated
public class TipiBrowser extends TipiSwingComponentImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7908348920868634250L;
	private TipiEditorPane myItem;

	public Object createContainer() {
		myItem = new TipiEditorPane();
		myItem.setEditable(false);
		return myItem;
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if (name.equals("binary")) {
			System.err.println("Setting to binary: " + object.toString());
			try {
				myItem.setBinary((Binary) object);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (name.equals("url")) {
			try {
				myItem.setPage((String) object);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.setComponentValue(name, object);

	}

}
