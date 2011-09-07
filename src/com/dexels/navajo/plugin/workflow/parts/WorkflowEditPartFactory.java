/*******************************************************************************
 * Copyright (c) 2004, 2005 Elias Volanakis and others.
�* All rights reserved. This program and the accompanying materials
�* are made available under the terms of the Eclipse Public License v1.0
�* which accompanies this distribution, and is available at
�* http://www.eclipse.org/legal/epl-v10.html
�*
�* Contributors:
�*����Elias Volanakis - initial API and implementation
�*******************************************************************************/
package com.dexels.navajo.plugin.workflow.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.dexels.navajo.plugin.workflow.model.StateElement;
import com.dexels.navajo.plugin.workflow.model.TaskElement;
import com.dexels.navajo.plugin.workflow.model.TransitionElement;
import com.dexels.navajo.plugin.workflow.model.WorkflowElement;

/**
 * Factory that maps model elements to edit parts.
 * 
 * @author Elias Volanakis
 */
public class WorkflowEditPartFactory implements EditPartFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
	 * java.lang.Object)
	 */
	@Override
	public EditPart createEditPart(EditPart context, Object modelElement) {
		// get EditPart for model element
		EditPart part = getPartForElement(modelElement);
		// store model element in EditPart
		part.setModel(modelElement);
		return part;
	}

	/**
	 * Maps an object to an EditPart.
	 * 
	 * @throws RuntimeException
	 *             if no match was found (programming error)
	 */
	private EditPart getPartForElement(Object modelElement) {
		if (modelElement instanceof WorkflowElement) {
			return new DiagramEditPart();
		}
		if (modelElement instanceof StateElement) {
			return new StateEditPart();
		}
		if (modelElement instanceof TransitionElement) {
			return new TransitionEditPart();
		}
		if (modelElement instanceof TaskElement) {
			return new TaskEditPart();
		}

		throw new RuntimeException("Can't create part for model element: "
				+ ((modelElement != null) ? modelElement.getClass().getName()
						: "null"));
	}

}