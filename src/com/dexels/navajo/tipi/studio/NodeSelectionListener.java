package com.dexels.navajo.tipi.studio;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface NodeSelectionListener {
//  public void documentModified();
//  public void errorFound(String errorMessage, int line, boolean temporary);
//  public void newDocument();
//  public void noErrorFound(boolean temporary);
//  public void locationChanged(LocationEvent e);

  public void nodeSelected(Object node, String xpath, String type, Object editor);
}
