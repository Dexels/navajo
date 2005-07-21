package com.dexels.navajo.adapter.crystal;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.document.types.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CrystalReport implements Mappable {
  public Binary data;

  public CrystalReport() {
  }

  public void setData(Binary b){
    data = b;
  }

  public Binary getData(){
    return data;
  }

  public void store() throws MappableException, UserException {
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
  }

  public void kill() {
  }

}
