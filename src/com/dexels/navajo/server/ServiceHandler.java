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
import java.util.*;

public interface ServiceHandler {
  public Navajo doService(Navajo doc, Access access, Parameters parms, ResourceBundle properties)
                           throws NavajoException, UserException, SystemException;
}