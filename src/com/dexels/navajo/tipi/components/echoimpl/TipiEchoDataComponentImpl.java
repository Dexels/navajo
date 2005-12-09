package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.LayoutData;
import nextapp.echo2.app.Window;
import nextapp.echo2.app.WindowPane;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.impl.TipiLayoutManager;

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

public abstract class TipiEchoDataComponentImpl extends TipiDataComponentImpl {

	protected Object layoutComponent = null;

	public TipiEchoDataComponentImpl() {
		TipiHelper th = new EchoTipiHelper();
		th.initHelper(this);
		addHelper(th);
	}

	public Object getLayoutComponent() {
		return layoutComponent;
	}

	public void removeFromContainer(Object c) {
		Component cc = (Component) getContainer();
		Component child = (Component) c;
		cc.remove(child);
	}

	public void addToContainer(Object c, Object constraints) {
		Component cc;
		cc = (Component) getContainer();
		Component child = (Component) c;
		System.err.println("addContainer: Adding: " + child + " to " + cc);
		if (child instanceof WindowPane) {
			TipiScreen s = (TipiScreen) getContext().getDefaultTopLevel();
			final Window w = (Window) s.getTopLevel();
			w.getContent().add(child);
		} else {
			cc.add(child);
			if (constraints != null && constraints instanceof LayoutData) {
				child.setLayoutData((LayoutData) constraints);
				System.err.println(">>>>>>>>>>>" + (LayoutData) constraints);
			}
			if (getLayout() != null) {
				getLayout().childAdded(c);
			}

		}
	}

	public void setContainerLayout(Object layout) {

		layoutComponent = layout;

		if (layout instanceof TipiLayoutManager) {
			/* 'Real layout' */
			// layoutComponent = (TipiLayoutManager)layout;
		} else {
			if (layout instanceof Component) {

			} else {
				System.err
						.println("*********************\nStrange layout found!\n*********************");
			}

		}

	}

	/**
	 * loadData
	 * 
	 * @param n
	 *            Navajo
	 * @param context
	 *            TipiContext
	 * @throws TipiException
	 * @todo Implement this com.dexels.navajo.tipi.TipiDataComponent method
	 */
	public void loadData(Navajo n, TipiContext context, String method)
			throws TipiException {
		super.loadData(n, context, method);
	}

}
