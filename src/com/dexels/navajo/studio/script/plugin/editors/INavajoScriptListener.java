/*
 * Created on Aug 1, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.editors;

import com.dexels.navajo.document.*;

public interface INavajoScriptListener {
    public void callingScript(String name);
    public void gotoScript(String name, Navajo n);
}
