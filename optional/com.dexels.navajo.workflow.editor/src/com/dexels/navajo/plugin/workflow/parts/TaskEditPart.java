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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.dexels.navajo.plugin.workflow.model.StateElement;
import com.dexels.navajo.plugin.workflow.model.TaskElement;
import com.dexels.navajo.plugin.workflow.model.WorkflowModelElement;

/**
 * EditPart used for Shape instances (more specific for EllipticalShape and
 * RectangularShape instances).
 * <p>
 * This edit part must implement the PropertyChangeListener interface, so it can
 * be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
class TaskEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener, NodeEditPart {

	private ConnectionAnchor anchor;

	// private StateFigure myFigure = null;
	/**
	 * Upon activation, attach to the model element as a property change
	 * listener.
	 */

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			((WorkflowModelElement) getModel()).addPropertyChangeListener(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	// protected void createEditPolicies() {
	// // allow removal of the associated model element
	// installEditPolicy(EditPolicy.COMPONENT_ROLE,
	// new ShapeComponentEditPolicy());
	// // allow the creation of connections and
	// // and the reconnection of connections between Shape instances
	//
	// }
	@Override
	protected void createEditPolicies() {
		// allow removal of the associated model element
		// installEditPolicy(EditPolicy.COMPONENT_ROLE, new
		// ShapeComponentEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new ContainerHighlightEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		if (getModel() instanceof TaskElement) {
			TaskElement task = (TaskElement) getModel();
			System.err.println("Creating taskeditpart");
			TaskFigure tf = new TaskFigure(task);
			tf.setSize(new Dimension(100, 30));
			return tf;
		}

		// IFigure f = createFigureForModel();
		// f.setOpaque(false); // non-transparent figure
		// // f.setBackgroundColor(ColorConstants.yellow);
		// //f.setToolTip(new Label("Hoei"));
		// return f;
		Ellipse ellipse = new Ellipse();
		ellipse.setSize(100, 30);
		return ellipse;
	}

	/**
	 * Upon deactivation, detach from the model element as a property change
	 * listener.
	 */
	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			((WorkflowModelElement) getModel())
					.removePropertyChangeListener(this);
		}
	}


	protected ConnectionAnchor getConnectionAnchor() {
		if (anchor == null) {
			if (getModel() instanceof StateElement) {
				anchor = new ChopboxAnchor(getFigure());
			} else {
				// if Shapes gets extended the conditions above must be updated
				throw new IllegalArgumentException("unexpected model");
			}
		}
		return anchor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef
	 * .ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef
	 * .Request)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef
	 * .ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef
	 * .Request)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
	 * PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (StateElement.SIZE_PROP.equals(prop)
				|| StateElement.LOCATION_PROP.equals(prop)) {
			refreshVisuals();
		} else if (StateElement.SOURCE_CONNECTIONS_PROP.equals(prop)) {
			refreshSourceConnections();
		} else if (StateElement.TARGET_CONNECTIONS_PROP.equals(prop)) {
			refreshTargetConnections();
		} else if (StateElement.ID_PROP.equals(prop)) {
			// do something
			// if(myFigure!=null) {
			// myFigure.setStateElement((StateElement) getModel());
			// refreshVisuals();
			//
			// }
		}
	}

	@Override
	protected void refreshVisuals() {
		// notify parent container of changed position & location
		// if this line is removed, the XYLayoutManager used by the parent
		// container
		// (the Figure of the ShapesDiagramEditPart), will not know the bounds
		// of this figure
		// and will not draw it correctly.
		// Rectangle bounds = new
		// Rectangle(getCastedModel().getLocation(),getCastedModel().getSize());
		// ((GraphicalEditPart)
		// getParent()).setLayoutConstraint(this,getFigure(), bounds);
	}
}