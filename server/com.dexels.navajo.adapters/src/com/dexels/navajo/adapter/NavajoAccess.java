package com.dexels.navajo.adapter;

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
 * @version $Id$
 */

public class NavajoAccess implements Mappable {

    private Access access;

    public String rpcName;
    public String rpcUser;
    public String rpcPwd;
    public String configPath;

    public NavajoAccess() {}

    @Override
	public void load(Access access) throws MappableException, UserException {
        this.access = access;
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

    @Override
	public void store() throws MappableException, UserException {}

    @Override
	public void kill() {}
}
