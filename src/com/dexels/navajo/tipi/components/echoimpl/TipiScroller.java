package com.dexels.navajo.tipi.components.echoimpl;

import java.awt.*;

import nextapp.echo2.app.*;

import echopointng.*;
import echopointng.able.*;

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
 * @author not attributable
 * @version 1.0
 */

public class TipiScroller extends TipiPanel {
    public TipiScroller() {
    }

     public void setComponentValue(final String name, final Object object) {

          if ("w".equals(name)) {
          ContainerEx cont = (ContainerEx) getContainer();
          cont.setWidth( new Extent(( (Integer) object).intValue(),Extent.PX));
          }
          if ("h".equals(name)) {
          ContainerEx cont = (ContainerEx) getContainer();
          cont.setHeight(  new Extent(( (Integer) object).intValue(),Extent.PX));
          }
         super.setComponentValue(name, object);
     }

}
