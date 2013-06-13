package com.dexels.navajo.adapter.sqlmap;


import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class RecordMap implements Mappable {

  public Object recordValue;
  public String recordName = "";

  public void load(Access access)
        throws MappableException, UserException {
  }

  public Object getRecordValue() {
    return recordValue;
  }

  public String getRecordName() {
    return recordName;
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {
  }
}
