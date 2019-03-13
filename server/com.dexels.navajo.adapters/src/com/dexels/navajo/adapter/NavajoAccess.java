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
    public String tenant;

    public NavajoAccess() {}

    @Override
	public void load(Access access) throws MappableException, UserException {
        this.access = access;
    }

    public String getTenant() {
    	return access.getTenant();
    }
    
    public String getRpcName() {
        return access.rpcName;
    }

    public String getRpcPwd() {
      return access.rpcPwd;
    }

    public String getRpcUser() {

        return access.rpcUser;
    }

    @Override
	public void store() throws MappableException, UserException {}

    @Override
	public void kill() {}
}
