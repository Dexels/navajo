package com.dexels.navajo.mapping;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;

public interface CompiledScript {

  public void execute(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws Exception;

}