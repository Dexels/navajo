package com.dexels.navajo.tipi.vaadin;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;
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
 
public class TipiScreen extends TipiDataComponentImpl{
    
	private static final long serialVersionUID = -6832475289705107584L;

	public TipiScreen(TipiContext tc) {
    	setContext(tc);
        setId("init");
    }
   
    @Override
	public Object createContainer() {
        myContext.setDefaultTopLevel(this);
         return null;
    }
    @Override
	public Object getContainer() {
        return getTopLevel();
    }

    @Override
	public void addToContainer(final Object current, final Object constraints) {
    }

    @Override
	public void removeFromContainer(Object c) {

    }

    @Override
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

    @Override
	public String getPath(String typedef) {
        return typedef;
    }




}
