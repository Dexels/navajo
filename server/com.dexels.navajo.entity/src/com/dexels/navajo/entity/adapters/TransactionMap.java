package com.dexels.navajo.entity.adapters;

import com.dexels.navajo.entity.transactions.NavajoTransactionManager;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

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
