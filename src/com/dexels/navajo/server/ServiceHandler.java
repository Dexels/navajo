package com.dexels.navajo.server;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */
import com.dexels.navajo.document.*;
import com.dexels.navajo.loader.NavajoClassLoader;
import java.util.*;

public interface ServiceHandler {

  public Navajo doService(Navajo doc, Access access, Parameters parms, ResourceBundle properties, Repository repository, NavajoClassLoader loader)
                           throws NavajoException, UserException, SystemException;
}