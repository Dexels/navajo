package com.dexels.navajo.tipi.components.echoimpl;

import java.util.Set;

import nextapp.echo2.app.ApplicationInstance;

import com.dexels.navajo.tipi.TipiContext;

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

public class EchoTipiContext extends TipiContext {
	private ApplicationInstance myServerContext;

	public EchoTipiContext() {
	}

	public Set getRequiredIncludes() {
		Set s = super.getRequiredIncludes();
		s.add("com/dexels/navajo/tipi/components/echoimpl/echoclassdef.xml");
		s.add("com/dexels/navajo/tipi/actions/echoactiondef.xml");
		return s;
	}

	public void setSplashInfo(String s) {
		/**
		 * @todo Implement this com.dexels.navajo.tipi.TipiContext abstract
		 *       method
		 */
	}

	public void setSplashVisible(boolean b) {
		/**
		 * @todo Implement this com.dexels.navajo.tipi.TipiContext abstract
		 *       method
		 */
	}

	public void setSplash(Object s) {
		/**
		 * @todo Implement this com.dexels.navajo.tipi.TipiContext abstract
		 *       method
		 */
	}

	public void clearTopScreen() {
		/**
		 * @todo Implement this com.dexels.navajo.tipi.TipiContext abstract
		 *       method
		 */
	}

	public int getPoolSize() {
		return 0;
	}

	public void setServerContext(ApplicationInstance sc) {
		myServerContext = sc;
	}

	public void exit(String destination) {
		// myServerContext. exit();
		System.err.println("in tipicontiext exit");
		((TipiEchoInstance)myServerContext).exitToUrl(destination);
//		System.err
//				.println("---------------------------------------------------------------------------------------> EXITED");
	}

}
