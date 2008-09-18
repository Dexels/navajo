package com.dexels.navajo.rich.components;

import java.awt.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TipiGradientPaint extends GradientPaint {
	private final String NORTH = "north";
	private final String SOUTH = "south";
	private final String EAST = "east";
	private final String WEST = "west";
	private final String NORTHEAST = "northeast";
	private final String NORTHWEST = "northwest";
	private final String SOUTHEAST = "southeast";
	private final String SOUTHWEST = "southwest";

	private String direction;
	private Color colorOne, colorTwo;
	private Rectangle bounds = new Rectangle(0, 0, 0, 0);

	public TipiGradientPaint(String direction, Color colorOne, Color colorTwo) {
		super(0, 0, colorOne, 0, 0, colorTwo);
		this.colorOne = colorOne;
		this.colorTwo = colorTwo;
		this.direction = direction;
	}

	public void setBounds(Rectangle r) {
		bounds = r;
	}

	public GradientPaint getPaint() {
		GradientPaint gp = null;
		if (NORTH.equals(direction)) {
			gp = new GradientPaint(0, 0, colorTwo, 0, bounds.height, colorOne);
			return gp;
		}
		if (SOUTH.equals(direction)) {
			gp = new GradientPaint(0, 0, colorOne, 0, bounds.height, colorTwo);
			return gp;
		}
		if (EAST.equals(direction)) {
			gp = new GradientPaint(0, 0, colorOne, bounds.width, 0, colorTwo);
			return gp;
		}
		if (WEST.equals(direction)) {
			gp = new GradientPaint(0, 0, colorTwo, bounds.width, 0, colorOne);
			return gp;
		}
		if (NORTHEAST.equals(direction)) {
			gp = new GradientPaint(0, bounds.height, colorOne, bounds.width, 0, colorTwo);
			return gp;
		}
		if (NORTHWEST.equals(direction)) {
			gp = new GradientPaint(bounds.width, bounds.height, colorOne, 0, 0, colorTwo);
			return gp;
		}
		if (SOUTHEAST.equals(direction)) {
			gp = new GradientPaint(0, 0, colorOne, bounds.width, bounds.height, colorTwo);
			return gp;
		}
		if (SOUTHWEST.equals(direction)) {
			gp = new GradientPaint(bounds.width, 0, colorOne, 0, bounds.height, colorTwo);
			return gp;
		}
		return gp;
	}

}
