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
import com.dexels.navajo.persistence.*;
import java.util.HashMap;


public abstract class ServiceHandler implements Constructor {

    protected Navajo requestDocument;
    protected Parameters parms;
    protected NavajoConfig properties;
    protected Access access;
    // protected Repository repository;
    // protected NavajoClassLoader loader;

    /**
     *
     * @param doc
     * @param access
     * @param parms
     * @param properties
     */
    public void setInput(Navajo doc, Access access, Parameters parms, NavajoConfig properties) {
        this.requestDocument = doc;
        this.parms = parms;
        this.properties = properties;
        this.access = access;
        // this.repository = repository;
        // this.loader = loader;
    }

    /**
     * @return
     * @throws NavajoException
     * @throws UserException
     * @throws SystemException
     */
    public abstract Navajo doService() throws NavajoException, UserException, SystemException;

    /**
     *
     * @return
     * @throws Exception
     */
    public Persistable construct() throws Exception {
        return doService();
    }

}
