package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 * @deprecated
 */
@Deprecated
public class TipiSwingErrorHandler
    extends BaseTipiErrorHandler {
  public TipiSwingErrorHandler() {
    //setContainer(createContainer);
  }

  public void showError() {
    TipiContext c = getContext();
    if (c != null) {
      showErrorDialog(getErrorMessage());
    }
    else {
      System.err.println("DefaultTipiErrorHandler, context not set!! ");
    }
  }

  public void showError(String text) {
    showErrorDialog(text);
  }

  public void showError(Exception e) {
    TipiContext c = getContext();
    c.showError(e.getMessage(), "");
  }

  public void showErrorDialog(final String error) {
	  getContext().showError(error, "");
  }
//  public Object createContainer(){
//    return new JPanel();
//  }
}
