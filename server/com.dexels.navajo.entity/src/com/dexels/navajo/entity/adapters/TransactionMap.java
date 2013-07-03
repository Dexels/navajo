package com.dexels.navajo.entity.adapters;

import com.dexels.navajo.entity.transactions.NavajoTransactionManager;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class TransactionMap implements Mappable {

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
			System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> In TransactionMap.store()");
			NavajoTransactionManager.getInstance().commit();
		} catch (Exception e) {
			throw new UserException(-1, e.getMessage(), e);
		}
	}

	@Override
	public void kill() {
		System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> In TransactionMap.kill()");
		try {
			NavajoTransactionManager.getInstance().rollback();
		} catch (Exception e) {
			//throw new UserException(-1, e.getMessage(), e);
		}
	}

	
}
