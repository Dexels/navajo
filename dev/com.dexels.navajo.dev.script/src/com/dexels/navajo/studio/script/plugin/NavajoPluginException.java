/*
 * Created on Jun 7, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoPluginException extends Exception {
	private static final long serialVersionUID = 8278748643970626504L;
	public NavajoPluginException(String msg) {
        super(msg);
    }
    public NavajoPluginException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
