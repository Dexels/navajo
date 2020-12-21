/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.entity.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private final static Logger logger = LoggerFactory
			.getLogger(ExampleTransactionalAdapter.class);
	
	public void setAction(String a) {
		this.action = a;
	}

	@Override
	public void commit() {
		logger.info("Committing action via NavajoTransaction: " + action);
	}

	@Override
	public void rollback() {
		logger.info("Rollback action via NavajoTransaction: " + action);
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		try {
			if ( NavajoTransactionManager.getInstance().getTransaction() != null ) {
				myTransaction = (NavajoTransaction) NavajoTransactionManager.getInstance().getTransaction();
				myTransaction.addTransactionalResource(this);
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	@Override
	public void store() throws MappableException, UserException {
		if ( myTransaction != null ) {
			logger.info("Do not commit " + action + " yet.");
		} else {
			logger.info("Committing " + action);
		}
	}

	@Override
	public void kill() {

	}

}
