package com.dexels.navajo.entity.transactions;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.entity.EntityException;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;

public class RollbackOperation {

	private ServiceEntityOperation rollbackOp;
	private Navajo rollbackRequest;
	
	public RollbackOperation(ServiceEntityOperation s, Navajo request, Navajo original) throws EntityException {
	
		if ( s.getMyOperation().getMethod().equals(Operation.POST) ) {
			Operation o = EntityManager.getInstance().getOperation(s.getMyEntity().getName(), Operation.DELETE);
			rollbackOp = s.cloneServiceEntityOperation(o);
			rollbackRequest = s.getMyKey().generateRequestMessage(request);
		}
		
		if ( s.getMyOperation().getMethod().equals(Operation.PUT) ) {
			Operation o = EntityManager.getInstance().getOperation(s.getMyEntity().getName(), Operation.PUT);
			rollbackRequest = original;
			rollbackOp = s.cloneServiceEntityOperation(o);
		}
		
		if ( s.getMyOperation().getMethod().equals(Operation.DELETE) ) {
			Operation o = EntityManager.getInstance().getOperation(s.getMyEntity().getName(), Operation.POST);
			rollbackRequest = original;
			rollbackOp = s.cloneServiceEntityOperation(o);
		}
	}
	
	public void rollback() throws EntityException {

		if ( rollbackOp != null ) {
			rollbackOp.perform(rollbackRequest);
		}

	}
	
}
