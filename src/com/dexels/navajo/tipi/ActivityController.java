package com.dexels.navajo.tipi;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ActivityController {

      public void performedEvent(TipiComponent tc, TipiEvent e) throws BlockActivityException;
      public void performedBlock(TipiComponent tc, TipiActionBlock tab, String expression, String expressionPath, boolean passed) throws BlockActivityException;
      public void performedAction(TipiComponent tc, TipiAction ta) throws BlockActivityException;

}