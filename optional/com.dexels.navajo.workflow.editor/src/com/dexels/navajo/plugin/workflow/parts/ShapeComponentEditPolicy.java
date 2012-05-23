/*******************************************************************************
 * Copyright (c) 2004, 2005 Elias Volanakis and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Elias Volanakis - initial API and implementation
 *******************************************************************************/
package com.dexels.navajo.plugin.workflow.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.dexels.navajo.plugin.workflow.model.StateElement;
import com.dexels.navajo.plugin.workflow.model.WorkflowElement;
import com.dexels.navajo.plugin.workflow.model.commands.ShapeDeleteCommand;

/**
 * This edit policy enables the removal of a Shapes instance from its container.
 * 
 * @see StateEditPart#createEditPolicies()
 * @see StateTreeEditPart#createEditPolicies()
 * @author Elias Volanakis
 */
class ShapeComponentEditPolicy extends ComponentEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(
	 * org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Object parent = getHost().getParent().getModel();
		Object child = getHost().getModel();
		if (parent instanceof WorkflowElement && child instanceof StateElement) {
			return new ShapeDeleteCommand((WorkflowElement) parent,
					(StateElement) child);
		}
		return super.createDeleteCommand(deleteRequest);
	}

	@Override
	public EditPart getTargetEditPart(Request request) {
		// TODO Auto-generated method stub
		return super.getTargetEditPart(request);
	}

	@Override
	public boolean understandsRequest(Request req) {
		// TODO Auto-generated method stub
		System.err.println("Understands targed: " + req.getType());
		System.err.println("Req: " + req.getClass());
		return true;
		// return super.understandsRequest(req);
	}

}