package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiQuestionEditor extends TipiSwingDataComponentImpl {

  private TipiSwingQuestionEditor myEditor = null;

  public TipiQuestionEditor() {
  }
  public Object createContainer() {
    myEditor = new TipiSwingQuestionEditor();
    return myEditor;
  }

  public void loadData(Navajo n, TipiContext context) throws TipiException {
    myEditor.loadData(n);
  }
}
