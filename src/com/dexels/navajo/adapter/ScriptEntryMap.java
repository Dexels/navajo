package com.dexels.navajo.adapter;

import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.Mappable;
import java.util.*;

/**
 * <p>Title: ScriptEntryMap </p>
 * <p>Description: Map to provide information about a script in the scriptlist used bij the ScriptListMap.
 *    This Map is part of the Navajo Studio environment.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV </p>
 * @author not attributable
 * @version 1.0
 */

public class ScriptEntryMap implements Mappable {
  public String name = "-";
  public Date date = new Date();
  public String status = "-";
  public String revision = "-";
  public boolean isinit;

  public ScriptEntryMap() {
  }

  public String getName(){
    return name;
  }

  public Date getDate(){
    return date;
  }

  public String getStatus(){
    return status;
  }

  public String getRevision(){
    return revision;
  }

  public boolean getIsinitscript(){
    return isinit;
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    //nip
  }
  public void store() throws MappableException, UserException {
    //nip
  }
  public void kill() {
    //nip
  }

}