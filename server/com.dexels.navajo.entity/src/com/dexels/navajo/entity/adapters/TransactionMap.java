/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.entity.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.entity.transactions.NavajoTransactionManager;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class TransactionMap implements Mappable {

	
	private final static Logger logger = LoggerFactory.getLogger(TransactionMap.class);

	@Override
	public void load(Access access) throws MappableException, UserException {
		try {
			NavajoTransactionManager.getInstance().begin();
		} catch (Exception e) {
			throw new UserException(-1, e.getMessage(), e);
		}
	}

	@Override
	public void store() throws MappableException, UserException {
		try {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> In TransactionMap.store()");
			NavajoTransactionManager.getInstance().commit();
		} catch (Exception e) {
			throw new UserException(-1, e.getMessage(), e);
		}
	}

	@Override
	public void kill() {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> In TransactionMap.kill()");
		try {
			NavajoTransactionManager.getInstance().rollback();
		} catch (Exception e) {
		    //throw new UserException(-1, e.getMessage(), e);
		}
	}

	
}
