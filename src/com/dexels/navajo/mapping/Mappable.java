

/**
 * Title:        Thispas navajo server<p>
 * Description:  This project aims to develop the neccessary logic
 * to access the Thispas WEB-application via the Navajo
 * model (see project Navajo)<p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.mapping;


import java.util.ArrayList;
import javax.naming.*;

import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;

public interface Mappable {

    /**
     * A Mappable class is executed by the Navajo Mapping Environment.
     * the load() method is called the first time an instance is accessed.
     * the store() method is called at the end of the mapping.
     * the kill() method is called in case of an exception.
     */
    public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException;   // At the beginning of MAP.
    public void store() throws MappableException, UserException;
    public void kill();

    /**
     * Classes that implement the Mappable interface should also implement setXXX() getXXX() accessors
     * to access public fields.
     * The setXXX() and getXXX() methods can be used to trigger additional computations.
     */
}
