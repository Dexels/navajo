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
package com.dexels.navajo.plugin.workflow;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.jface.resource.ImageDescriptor;

import com.dexels.navajo.plugin.workflow.model.StateElement;
import com.dexels.navajo.plugin.workflow.model.TransitionElement;

/**
 * Utility class that can create a GEF Palette.
 * 
 * @see #createPalette()
 * @author Elias Volanakis
 */
final class ShapesEditorPaletteFactory {


	/** Create the "Shapes" drawer. */
	private static PaletteContainer createShapesDrawer() {
		PaletteDrawer componentsDrawer = new PaletteDrawer("State");

		CombinedTemplateCreationEntry component = new CombinedTemplateCreationEntry(
				"State", "Create a new State", StateElement.class,
				new SimpleFactory(StateElement.class),
				ImageDescriptor.createFromFile(ShapesPlugin.class,
						"icons/ellipse16.gif"), ImageDescriptor.createFromFile(
						ShapesPlugin.class, "icons/ellipse24.gif"));
		componentsDrawer.add(component);
		return componentsDrawer;
	}

	/**
	 * Creates the PaletteRoot and adds all palette elements. Use this factory
	 * method to create a new palette for your graphical editor.
	 * 
	 * @return a new PaletteRoot
	 */
	static PaletteRoot createPalette() {
		PaletteRoot palette = new PaletteRoot();
		palette.add(createToolsGroup(palette));
		palette.add(createShapesDrawer());
		return palette;
	}

	/** Create the "Tools" group. */
	private static PaletteContainer createToolsGroup(PaletteRoot palette) {
		PaletteGroup toolGroup = new PaletteGroup("Transitions");

		// Add a selection tool to the group
		ToolEntry tool = new PanningSelectionToolEntry();
		toolGroup.add(tool);
		palette.setDefaultEntry(tool);

		// Add a marquee tool to the group
		toolGroup.add(new MarqueeToolEntry());

		// Add a (unnamed) separator to the group
		toolGroup.add(new PaletteSeparator());

		// Add (solid-line) connection tool
		tool = new ConnectionCreationToolEntry("Transition",
				"Create a Transition", new CreationFactory() {
					@Override
					public Object getNewObject() {
						return null;
					}

					// see ShapeEditPart#createEditPolicies()
					// this is abused to transmit the desired line style
					@Override
					public Object getObjectType() {
						return TransitionElement.SOLID_CONNECTION;
					}
				}, ImageDescriptor.createFromFile(ShapesPlugin.class,
						"icons/connection_s16.gif"),
				ImageDescriptor.createFromFile(ShapesPlugin.class,
						"icons/connection_s24.gif"));
		toolGroup.add(tool);

		// Add (dashed-line) connection tool
		// tool = new ConnectionCreationToolEntry(
		// "Dashed connection",
		// "Create a dashed-line connection",
		// new CreationFactory() {
		// public Object getNewObject() { return null; }
		// // see ShapeEditPart#createEditPolicies()
		// // this is abused to transmit the desired line style
		// public Object getObjectType() { return
		// TransitionElement.DASHED_CONNECTION; }
		// },
		// ImageDescriptor.createFromFile(ShapesPlugin.class,
		// "icons/connection_d16.gif"),
		// ImageDescriptor.createFromFile(ShapesPlugin.class,
		// "icons/connection_d24.gif"));
		// toolGroup.add(tool);

		return toolGroup;
	}

	/** Utility class. */
	private ShapesEditorPaletteFactory() {
		// Utility class
	}

}