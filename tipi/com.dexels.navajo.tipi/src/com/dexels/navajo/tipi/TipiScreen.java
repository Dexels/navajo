package com.dexels.navajo.tipi;

import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiScreen extends TipiDataComponentImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8296245541334132196L;

	public TipiScreen(TipiContext tc) {
		setContext(tc);
		setId("init");
	}

	public Object createContainer() {
		myContext.setDefaultTopLevel(this);
		return null;
	}

	public Object getContainer() {
		return getTopLevel();
	}

	public void addToContainer(final Object current, final Object constraints) {
	}

	public void removeFromContainer(Object c) {

	}

	public TipiComponent getTipiComponentByPath(String path) {
		if (path.equals(".")) {
			return this;
		}
		if (path.equals("..")) {
			return null;
		}
		if (path.startsWith("..")) {
			return null;
		}
		if (path.indexOf("/") == 0) {
			path = path.substring(1);
		}
		int s = path.indexOf("/");
		if (s == -1) {
			if (path.equals("")) {
				return myContext.getDefaultTopLevel();
			}
			return getTipiComponent(path);
		} else {
			String name = path.substring(0, s);
			String rest = path.substring(s);
			TipiComponent t = getTipiComponent(name);
			if (t == null) {
				throw new NullPointerException("Did not find Tipi: " + name);
			}
			return t.getTipiComponentByPath(rest);
		}
	}

	// For now, always return the first frame. Maybe enhance later or something
	public Object getTopLevel() {
		for (int i = 0; i < getChildCount(); i++) {
			TipiComponent current = getTipiComponent(i);
			if (current.isTopLevel()) {
				return current.getContainer();
			}
		}
		return null;
	}

	public String getPath(String typedef) {
		return typedef;
	}

}
