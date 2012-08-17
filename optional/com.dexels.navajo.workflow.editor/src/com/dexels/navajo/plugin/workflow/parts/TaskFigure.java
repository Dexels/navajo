package com.dexels.navajo.plugin.workflow.parts;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Display;

import com.dexels.navajo.plugin.workflow.model.TaskElement;

public class TaskFigure extends Figure {
	protected Label label = new Label();
	protected boolean singleBorder = true;
	protected static Color initialFgColor;
	protected static Color initialBgColor;
	protected static Color normalFgColor;
	protected static Color normalBgColor;
	protected static Color finalFgColor;
	protected static Color finalBgColor;
	protected static Color shadowColor;
	protected String text;
	protected boolean initialState = false;
	protected boolean finalState = false;

	public TaskFigure(TaskElement s) {
		super();

		// Add the label
		BorderLayout layout = new BorderLayout();
		layout.setHorizontalSpacing(4);
		layout.setVerticalSpacing(4);
		setLayoutManager(layout);
		label.setForegroundColor(ColorConstants.black);
		add(label, BorderLayout.BOTTOM);

		// Lazily instanciate the shared colors

		Device device = Display.getCurrent();
		fgColor = new Color(device, 170, 170, 170);
		bgColor = new Color(device, 250, 250, 250);

		// Init
		// setInitialState(initialState);
		// setFinalState(finalState);
		// setTaskElement(s);
	}

	public void setTaskElement(TaskElement se) {
		label.setText(se.getRequest() + " - " + se.getService());

	}

	@Override
	protected void paintFigure(Graphics graphics) {
		// Inits
		super.paintFigure(graphics);
		Rectangle bounds = getBounds().getCopy().resize(-9, -9).translate(4, 4);
		final int round = 5;

		// Shadow / experimental
		graphics.setBackgroundColor(shadowColor);
		// graphics.fillRoundRectangle(bounds.getTranslated(4, 4), sround,
		// sround);

		// Drawings

		graphics.setForegroundColor(fgColor);
		graphics.setBackgroundColor(bgColor);
		graphics.fillRoundRectangle(bounds, round, round);
		graphics.drawRoundRectangle(bounds, round, round);
		if (!singleBorder) {
			bounds.expand(-4, -4);
			graphics.drawRoundRectangle(bounds, round, round);
		}

		// Cleanups
		graphics.restoreState();
	}

}
