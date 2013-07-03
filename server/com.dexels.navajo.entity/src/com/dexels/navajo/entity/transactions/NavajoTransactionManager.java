package com.dexels.navajo.entity.transactions;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

/**
 * The NavajoTransactionManager support transactions over Navajo entity operations.
 * Changes (PUT, POST, DELETE) created during a Transation can always be roll-backed.
 * 
 * Example:
 * 
 * begin()
 * 
 * PUT entity1
 * POST entity2
 * DELETE entity3
 * GET entity2
 * POST entity1
 * 
 * commit(): insert entity1, update entity2, delete entity3
 * 
 * rollback(): delete entity1, restore entity2, restore entity3
 * 
 * ISOLATION LEVELS:
 * 
 *  TRANSACTION_READ_UNCOMMITTED: The transaction can read uncommitted data, i.e., data being changed by another transaction concurrently.
    TRANSACTION_READ_COMMITTED: This level results in the prevention of a transaction from reading uncommitted changes in other concurrent transactions. This level ensures that dirty reads are not possible.
    TRANSACTION_REPEATABLE_READ: In addition to the prevention associated with TRANSACTION_READ_COMMITTED, this level ensures that reading the same data multiple times will receive the same value even if another transaction modifies the data. Methods with this isolation level, besides having the same behavior as TRANSACTION_READ_COMMITTED, can only execute repeatable reads.
    TRANSACTION_SERIALIZABLE: The transaction has exclusive read and update privileges to data by locking it; other transactions can neither write nor read the same data. It is the most restrictive transaction isolation level and it ensures that if a query retrieves a result set based on a predicate condition and another transaction inserts data that satisfy the predicate condition, re-execution of the query will return the same result set.
 * @author arjenschoneveld
 *
 */
public class NavajoTransactionManager implements TransactionManager {

	private static NavajoTransactionManager myInstance = new NavajoTransactionManager();
	
	private Map<Thread,NavajoTransaction> allTransactions = new HashMap<Thread,NavajoTransaction>();
	
	public static NavajoTransactionManager getInstance() {
		return myInstance;
	}
	
	/**
	 * Starts a fresh Transaction associated with the current thread.
	 * 
	 */
	@Override
	public void begin() throws NotSupportedException, SystemException {
		if ( allTransactions.get(Thread.currentThread()) == null ) {
			NavajoTransaction transaction = new NavajoTransaction();
			allTransactions.put(Thread.currentThread(), transaction);
		}
	}

	@Override
	public void commit() throws HeuristicMixedException,
	HeuristicRollbackException, IllegalStateException,
	RollbackException, SecurityException, SystemException {
		try {
			NavajoTransaction nt = allTransactions.get(Thread.currentThread());
			if ( nt == null ) {
				throw new SystemException("No transaction to commit.");
			} else {
				nt.commit();
			}
		} finally {
			allTransactions.remove(Thread.currentThread());
		}
	}

	@Override
	public int getStatus() throws SystemException {
		NavajoTransaction nt = allTransactions.get(Thread.currentThread());
		if ( nt == null ) {
			throw new SystemException("No transaction present.");
		} else {
			return nt.getStatus();
		}
	}

	/**
	 * Returns the Transaction object associated with the current thread.
	 * 
	 */
	@Override
	public Transaction getTransaction() throws SystemException {
		NavajoTransaction nt = allTransactions.get(Thread.currentThread());
		if ( nt != null && nt.getStatus() != NavajoTransaction.RUNNING ) {
			throw new SystemException("Invalid state of Transaction: " + nt.getStatus());
		}
		return nt;
	}

	@Override
	public void resume(Transaction arg0) throws IllegalStateException,
			InvalidTransactionException, SystemException {
		
	}

	@Override
	public void rollback() throws IllegalStateException, SecurityException, SystemException {
		try {
			NavajoTransaction nt = allTransactions.get(Thread.currentThread());
			if ( nt == null ) {
				throw new SystemException("No transaction to rollback.");
			} else {
				// Remove Transaction Context first to make sure that rollback operations are NOT performed in transaction.
				allTransactions.remove(Thread.currentThread());
				nt.rollback();
			}
		} finally {
			allTransactions.remove(Thread.currentThread());
		}
	}

	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTransactionTimeout(int arg0) throws SystemException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Transaction suspend() throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

}
