package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.core.*;
import nextapp.echo.TextArea;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiTextArea extends TipiEchoComponentImpl {
  public TipiTextArea() {
  }
  public Object createContainer() {
    TextArea rta = new TextArea();
    return rta;
  }

  public Object getComponentValue(String id){
    if("text".equals(id)){
      TextArea t = (TextArea)getContainer();
      return t.getText().trim();
    }
    return super.getComponentValue(id);
  }

}
