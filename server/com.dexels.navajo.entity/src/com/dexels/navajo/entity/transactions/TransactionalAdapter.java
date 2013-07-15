package com.dexels.navajo.entity.transactions;

public interface TransactionalAdapter {

	public void commit();
	
	public void rollback();
	
}
