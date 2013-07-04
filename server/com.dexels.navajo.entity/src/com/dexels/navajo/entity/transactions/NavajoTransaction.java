package com.dexels.navajo.entity.transactions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.entity.EntityException;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;

public class NavajoTransaction implements Transaction {

	public final static int RUNNING = 1;
	public final static int COMMITED = 2;
	public final static int ROLED_BACK = 3;

	private int state = RUNNING;

	private Stack<RollbackOperation> rollbackStack = new Stack<RollbackOperation>();
	private Set<TransactionalAdapter> transactionalResources = new HashSet<TransactionalAdapter>();
	private Map<String,Object> transactionAttributes = new HashMap<String, Object>();
	
	/**
	 * Add an attribute specific to this transaction context. For example: database connection identifier.
	 * 
	 * @param key
	 * @param value
	 */
	public void addTransactionAttribute(String key, Object value) {
		transactionAttributes.put(key, value);
	}
	
	public Object getTransactionAttribute(String key) {
		return transactionAttributes.get(key);
	}
	
	/**
	 * Transactional resources can be added to a NavajoTransaction context.
	 * 
	 * @param resource
	 */
	public void addTransactionalResource(TransactionalAdapter resource) {
		transactionalResources.add(resource);
	}

	/**
	 * Add non-transactional resource, i.e. supports compensation based rollback.
	 * 
	 * @param input
	 * @param original
	 * @param seo
	 * @throws EntityException
	 */
	public void addNonTransactionalEntityResource(Navajo input, Navajo original, ServiceEntityOperation seo) throws EntityException {

		if (    seo.getMyOperation().getMethod().equals(Operation.POST) || 
				seo.getMyOperation().getMethod().equals(Operation.PUT) ||
				seo.getMyOperation().getMethod().equals(Operation.DELETE) ) {

			RollbackOperation ro = new RollbackOperation(seo, input, original);
			rollbackStack.add(ro);

		}

	}

	@Override
	public void commit() throws HeuristicMixedException,
	HeuristicRollbackException, RollbackException, SecurityException,
	SystemException {
		state = COMMITED;
		// Commit transactional resources.
		for ( TransactionalAdapter tr : transactionalResources ) {
			tr.commit();
		}
	}

	@Override
	public boolean delistResource(XAResource arg0, int arg1)
			throws IllegalStateException, SystemException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean enlistResource(XAResource arg0)
			throws IllegalStateException, RollbackException, SystemException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getStatus() throws SystemException {
		return state;
	}

	@Override
	public void registerSynchronization(Synchronization arg0)
			throws IllegalStateException, RollbackException, SystemException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback() throws IllegalStateException, SystemException {

		state = ROLED_BACK;
		try {
			RollbackOperation ro = null;
			while ( (ro = rollbackStack.pop() ) != null ) {
				try {
					ro.rollback();
				} catch (EntityException e) {
					System.err.println("Could not perform rollback: " + e.getMessage());
					//throw new SystemException("Could not perform rollback:" + e.getMessage());
				}
			}
		} finally {
			// Rollback transactional resources.
			for ( TransactionalAdapter tr : transactionalResources ) {
				tr.rollback();
			}
		}
	}

	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		// TODO Auto-generated method stub

	}

}
