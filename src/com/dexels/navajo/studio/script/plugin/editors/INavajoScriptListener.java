/*
 * Created on Aug 1, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.editors;

import com.dexels.navajo.document.Navajo;

public interface INavajoScriptListener {
    public void gotoScript(String name, Navajo n);
    public void callScript(String name, Navajo n);
}
