package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Border;
import nextapp.echo2.app.Component;

import com.dexels.navajo.echoclient.components.Styles;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.core.TipiComponentImpl;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;

import echopointng.able.Borderable;

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

public abstract class TipiEchoComponentImpl extends TipiComponentImpl {

	private static final long serialVersionUID = 2792520352274586810L;


	public TipiEchoComponentImpl() {
        TipiHelper th = new EchoTipiHelper();
        th.initHelper(this);
        addHelper(th);
    }


    protected void setComponentValue(final String name, final Object object) {
        
    	runSyncInEventThread(new Runnable(){

			public void run() {
			       if ("border".equals(name)) {
		            if (getContainer()!=null && getContainer() instanceof Borderable) {
		                Borderable comp = (Borderable)getContainer();
		                comp.setBorder((Border)object);
//		                comp.set
		            }
	             }
		            if ("style".equals(name)) {
					if (getContainer() instanceof Component) {
						Component c = (Component) getContainer();
						c.setStyle(Styles.DEFAULT_STYLE_SHEET.getStyle(c.getClass(), (String) object));

					} else {
						myContext.showInternalError("NO echo component: " + getContainer());
					}
				}

			}
    	});
        
        super.setComponentValue(name, object);

    }

}

