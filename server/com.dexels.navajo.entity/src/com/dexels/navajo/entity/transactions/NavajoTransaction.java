package com.dexels.navajo.entity.transactions;

import java.util.ArrayList;
import java.util.List;
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
	
	public void logEntityService(Navajo input, Navajo original, ServiceEntityOperation seo) throws EntityException {

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
		RollbackOperation ro = null;
		while ( (ro = rollbackStack.pop() ) != null ) {
			try {
				ro.rollback();
			} catch (EntityException e) {
				throw new SystemException("Could not perform rollback:" + e.getMessage());
			}
		}
	}

	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		// TODO Auto-generated method stub
		
	}

}
