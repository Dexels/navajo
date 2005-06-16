/*
 * Created on Jun 14, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.mapping.compiler.meta;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface MetaDataListener {
    public void scriptCalls(String source, String destination, String[] requires);
    public void scriptIncludes(String source, String destination);
    public void scriptUsesAdapter(String source, String adaptername);
    public void scriptUsesField(String source, String adaptername, String fieldName);
    public void resetMetaData();
}
