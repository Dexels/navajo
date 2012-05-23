/*******************************************************************************
 * Copyright (c) 2008, Original authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR <angelo.zerr@gmail.com>
 *******************************************************************************/
package org.akrogen.tkui.css.tipi.helpers;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import org.akrogen.tkui.css.core.utils.Platform;
import org.akrogen.tkui.css.tipi.resources.SwingResourcesRegistry;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

/**
 * CSS Swing Cursor Helper to :
 * <ul>
 * <li>convert CSSValue cursor to Swing Cursor.</li>
 * <li>convert Swing Cursor to CSSValue cursor as string.</li>
 * </ul>
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSSwingCursorHelper {

	/**
	 * Maps Cursor CSSValue to Swing Cursors
	 */
	protected static Map cursorMap;

	/**
	 * Static initialization of the cursorMap
	 */
	static {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		cursorMap = new HashMap();
		// default : The default cursor (often an arrow)
		cursorMap.put("default", Cursor
				.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		// crosshair : The cursor render as a crosshair
		cursorMap.put("crosshair", Cursor
				.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		// auto : TODO : manage auto
		cursorMap
				.put("auto", Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		// pointer : The cursor render as a pointer (a hand) that indicates a
		// link
		cursorMap
				.put("pointer", Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// e-resize : The cursor indicates that an edge of a box is to be moved
		// right
		// (east)
		cursorMap.put("e-resize", Cursor
				.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
		// ne-resize : The cursor indicates that an edge of a box is to be moved
		// up and right (north/east)
		cursorMap.put("ne-resize", Cursor
				.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
		// nw-resize : The cursor indicates that an edge of a box is to be moved
		// up and left (north/west)
		cursorMap.put("nw-resize", Cursor
				.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
		// n-resize : The cursor indicates that an edge of a box is to be moved
		// up (north)
		cursorMap.put("n-resize", Cursor
				.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
		// se-resize : The cursor indicates that an edge of a box is to be moved
		// down and right (south/east)
		cursorMap.put("se-resize", Cursor
				.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
		// sw-resize : The cursor indicates that an edge of a box is to be moved
		// down and left (south/west)
		cursorMap.put("sw-resize", Cursor
				.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
		// s-resize : The cursor indicates that an edge of a box is to be moved
		// down (south)
		cursorMap.put("s-resize", Cursor
				.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
		// w-resize : The cursor indicates that an edge of a box is to be moved
		// left (west)
		cursorMap.put("w-resize", Cursor
				.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		// text : The cursor indicates text
		cursorMap.put("text", Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		// wait : The cursor indicates that the program is busy (often a watch
		// or an hourglass)
		cursorMap.put("wait", Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		// move : The cursor indicates something that should be moved
		Cursor moveCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
		if (Platform.isOSX) {
			try {
				Image img = toolkit.createImage(SwingResourcesRegistry.class
						.getResource("cursor/move.gif"));
				moveCursor = toolkit.createCustomCursor(img, new Point(11, 11),
						"move");
			} catch (Exception ex) {
			}
		}
		cursorMap.put("move", moveCursor);
		// help : The cursor indicates that help is available (often a question
		// mark or a balloon)
		Cursor helpCursor;
		try {
			Image img = toolkit.createImage(SwingResourcesRegistry.class
					.getResource("cursor/help.gif"));
			helpCursor = toolkit.createCustomCursor(img, new Point(1, 3),
					"help");
		} catch (Exception ex) {
			helpCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		}
		cursorMap.put("help", helpCursor);
	}

	/**
	 * Convert CSSValue cursor to Swing cursor.
	 * 
	 * @param value
	 * @return
	 */
	public static Cursor getCursor(CSSValue value) {
		if (!(value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE))
			return null;
		String cursorName = ((CSSPrimitiveValue) value).getStringValue();
		return (Cursor) cursorMap.get(cursorName);
	}

	/**
	 * Convert Swing cursor to CSSValue cursor as string.
	 * 
	 * @param cursor
	 * @return
	 */
	public static String getCursor(Cursor cursor) {
		if (cursor == null)
			return "auto";
		switch (cursor.getType()) {
		case Cursor.DEFAULT_CURSOR:
			// The default cursor (often an arrow)
			return "default";
		case Cursor.CROSSHAIR_CURSOR:
			// The cursor render as a crosshair
			return "crosshair";
		case Cursor.HAND_CURSOR:
			// The cursor render as a pointer (a hand) that indicates a link
			return "pointer";
		case Cursor.MOVE_CURSOR:
			// The cursor indicates something that should be moved
			return "move";
		case Cursor.E_RESIZE_CURSOR:
			// The cursor indicates that an edge of a box is to be moved
			// right (east)
			return "e-resize";
		case Cursor.NE_RESIZE_CURSOR:
			// The cursor indicates that an edge of a box is to be moved up
			// and right (north/east)
			return "ne-resize";
		case Cursor.NW_RESIZE_CURSOR:
			// The cursor indicates that an edge of a box is to be moved up
			// and left (north/west)
			return "nw-resize";
		case Cursor.N_RESIZE_CURSOR:
			// The cursor indicates that an edge of a box is to be moved up
			// (north)
			return "n-resize";
		case Cursor.SE_RESIZE_CURSOR:
			// The cursor indicates that an edge of a box is to be moved
			// down and right (south/east)
			return "se-resize";
		case Cursor.SW_RESIZE_CURSOR:
			// The cursor indicates that an edge of a box is to be moved
			// down and left (south/west)
			return "sw-resize";
		case Cursor.S_RESIZE_CURSOR:
			// The cursor indicates that an edge of a box is to be moved
			// down (south)
			return "s-resize";
		case Cursor.W_RESIZE_CURSOR:
			// The cursor indicates that an edge of a box is to be moved
			// left (west)
			return "w-resize";
		case Cursor.TEXT_CURSOR:
			// The cursor indicates text
			return "text";
		case Cursor.WAIT_CURSOR:
			// The cursor indicates that the program is busy (often a watch
			// or an hourglass)
			return "wait";
		}
		if ("help".equals(cursor.getName()))
			return "help";
		if ("move".equals(cursor.getName()))
			return "move";
		return "auto";
	}

}
