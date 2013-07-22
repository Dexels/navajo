package com.dexels.navajo.entity.adapters;

import com.dexels.navajo.entity.transactions.NavajoTransaction;
import com.dexels.navajo.entity.transactions.NavajoTransactionManager;
import com.dexels.navajo.entity.transactions.TransactionalAdapter;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class ExampleTransactionalAdapter implements TransactionalAdapter, Mappable {

	private String action;
	private NavajoTransaction myTransaction = null;

	public void setAction(String a) {
		this.action = a;
	}

	@Override
	public void commit() {
		System.err.println("Committing action via NavajoTransaction: " + action);
	}

	@Override
	public void rollback() {
		System.err.println("Rollback action via NavajoTransaction: " + action);
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		try {
			if ( NavajoTransactionManager.getInstance().getTransaction() != null ) {
				myTransaction = (NavajoTransaction) NavajoTransactionManager.getInstance().getTransaction();
				myTransaction.addTransactionalResource(this);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	@Override
	public void store() throws MappableException, UserException {
		if ( myTransaction != null ) {
			System.err.println("Do not commit " + action + " yet.");
		} else {
			System.err.println("Committing " + action);
		}
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub

	}

}
