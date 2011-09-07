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
import org.eclipse.gef.EditPartFactory;

import com.dexels.navajo.plugin.workflow.model.StateElement;
import com.dexels.navajo.plugin.workflow.model.TransitionElement;
import com.dexels.navajo.plugin.workflow.model.WorkflowElement;

/**
 * Factory that maps model elements to TreeEditParts. TreeEditParts are used in
 * the outline view of the ShapesEditor.
 * 
 * @author Elias Volanakis
 */
public class WorkflowTreeEditPartFactory implements EditPartFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
	 * java.lang.Object)
	 */
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof StateElement) {
			return new StateTreeEditPart((StateElement) model);
		}
		if (model instanceof TransitionTreeEditPart) {
			return new TransitionTreeEditPart((TransitionElement) model);
		}

		if (model instanceof WorkflowElement) {
			return new DiagramTreeEditPart((WorkflowElement) model);
		}
		return null; // will not show an entry for the corresponding model
						// instance
	}

}
