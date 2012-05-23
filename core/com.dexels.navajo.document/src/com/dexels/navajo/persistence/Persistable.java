package com.dexels.navajo.persistence;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

/**
 * The Navajo class should implement this interface.
 */
public interface Persistable extends java.io.Serializable {

    /**
     * Generate a unique key.
     */
    public String persistenceKey();

}
