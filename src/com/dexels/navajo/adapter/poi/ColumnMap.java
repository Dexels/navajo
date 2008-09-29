package com.dexels.navajo.adapter.poi;


import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.mapping.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class ColumnMap implements Mappable {

  public CellMap [] row;

  public void load(Access access) throws MappableException, UserException {

  }

  public void store() throws MappableException, UserException {

  }

  public CellMap [] getRow() {
    return row;
  }

  public void kill() {

  }
}
