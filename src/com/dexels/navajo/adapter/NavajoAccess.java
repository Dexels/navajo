package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class NavajoAccess implements Mappable {

    private Access access;
    private NavajoConfigInterface config;

    public String rpcName;
    public String rpcUser;
    public String rpcPwd;
    public String configPath;

    public NavajoAccess() {}

    public void load(Access access) throws MappableException, UserException {
        this.access = access;
        this.config = config;
    }

    public String getRpcName() {
        return access.rpcName;
    }

    public String getRpcPwd() {
      return access.rpcPwd;
    }

    public String getRpcUser() {
        //System.out.println("in NavajoAccess, getRpcUser()");
        //System.out.println("Access = " + access);
        //System.out.println("user = " + access.rpcUser);
        return access.rpcUser;
    }

//    public String getConfigPath() {
//      return config.getConfigPath();
//    }
//

    public void store() throws MappableException, UserException {}

    public void kill() {}
}
