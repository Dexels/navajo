package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.core.*;
import echopoint.*;

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
    RichTextArea rta = new RichTextArea();
    return rta;
  }

}
