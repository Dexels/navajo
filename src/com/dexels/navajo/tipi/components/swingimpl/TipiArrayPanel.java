package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

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
public class TipiArrayPanel extends TipiSwingDataComponentImpl {

	private String messagePath = null;
	
	public Object createContainer() {
		JPanel myPanel = new JPanel();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myPanel;
	}

	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		loadArrayData(n, method, messagePath);
		super.loadData(n, method);
		doLayout();
	}

	@Override
	protected Object getComponentValue(String name) {
		if(name.equals("messagePath")) {
			return messagePath;
		}
		return super.getComponentValue(name);
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if(name.equals("messagePath")) {
			messagePath = (String)object;
			return;
		}
		super.setComponentValue(name, object);
	}

	protected void cascadeLoad(Navajo n, String method) throws TipiException {
		System.err.println("ArrayPanel: Not cascading");
	}
	
}
