package com.dexels.navajo.tipi;

import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
/**
 * This is an interface to identify executable tipi elements, typically either TipiAction derived classes, or TipiActionBlock blocks
 */
public interface TipiExecutable {
//  public void performAction(TipiEvent te) throws TipiBreakException, TipiException;
  public void performAction(TipiEvent te,TipiExecutable parent, int index) throws TipiBreakException, TipiException;

  public XMLElement store();

  public int getExecutableChildCount();

  public TipiExecutable getExecutableChild(int index);

  public TipiComponent getComponent();

  public TipiEvent getEvent();

  public void setEvent(TipiEvent e);
  //  public TipiEvent getEvent();

}
