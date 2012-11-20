package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingScrollPane;

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
public class TipiScroller extends TipiPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 157352588550461697L;
	private JScrollPane jp;
	private JPanel pp = null;

	public Object createContainer() {
		jp = new TipiSwingScrollPane();
		pp = new JPanel();
		pp.setLayout(new BorderLayout());
		jp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		jp.getViewport().add(pp);
		return jp;
	}

	public void addToContainer(Object c, Object constraints) {
		// jp.getViewport().add((Component)c);
		pp.add((Component) c, BorderLayout.CENTER);
	}

	public void removeFromContainer(Object c) {
		// jp.getViewport().remove((Component)c);
		pp.remove((Component) c);
	}

	// public TipiComponent getTipiComponentByPath(String path) {
	// TipiComponent tc = super.getTipiComponentByPath(path);
	// logger.debug("TipiScroller.getTipiComponentByPath: Looking for: "+path+" children # "+getChildCount());
	// logger.debug("I am: "+toString());
	// logger.debug(">> CHILDDUMP: "+childDump());
	// return tc;
	// }

	/**
	 * getComponentValue
	 * 
	 * @param name
	 *            String
	 * @return Object
	 * @todo Implement this
	 *       com.dexels.navajo.tipi.components.core.TipiComponentImpl method
	 */
	protected Object getComponentValue(String name) {
		JScrollPane jp = (JScrollPane) getContainer();
		if (name.equals("horizontal_policy")) {
			switch (jp.getHorizontalScrollBarPolicy()) {
			case ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS:
				return "always";
			case ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED:
				return "as_needed";
			case ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER:
				return "always";
			}

		}
		if (name.equals("vertical_policy")) {
			switch (jp.getHorizontalScrollBarPolicy()) {
			case ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS:
				return "always";
			case ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED:
				return "as_needed";
			case ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER:
				return "always";
			}

		}

		return "";
	}

	/**
	 * setComponentValue
	 * 
	 * @param name
	 *            String
	 * @param object
	 *            Object
	 * @todo Implement this
	 *       com.dexels.navajo.tipi.components.core.TipiComponentImpl method
	 *       <value direction="inout" name="horizontal_policy" type="selection">
	 *       <option description="As needed" value="needed"/> <option
	 *       description="Always" value="always"/> <option description="Never"
	 *       value="never"/> </value> <value direction="inout"
	 *       name="vertical_policy" type="selection"> <option
	 *       description="As needed" value="needed"/> <option
	 *       description="Always" value="always"/> <option description="Never"
	 *       value="never"/> </value>
	 */
	public void setComponentValue(String name, Object object) {
		JScrollPane jp = (JScrollPane) getContainer();
		if (name.equals("horizontal_policy")) {
			if (object.toString().equals("always")) {
				jp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			}
			if (object.toString().equals("never")) {
				jp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			}
			if (object.toString().equals("needed")) {
				jp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			}
		}
		if (name.equals("vertical_policy")) {
			if (object.toString().equals("always")) {
				jp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			}
			if (object.toString().equals("never")) {
				jp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			}
			if (object.toString().equals("needed")) {
				jp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			}
		}
	}

}
