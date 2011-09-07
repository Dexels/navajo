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

import com.dexels.navajo.plugin.workflow.model.StateElement;

public class StateFigure extends Figure {
	protected Label label = new Label();
	protected Color fgColor;
	protected Color bgColor;
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

	public StateFigure(StateElement s) {
		super();

		// Add the label
		BorderLayout layout = new BorderLayout();
		layout.setHorizontalSpacing(4);
		layout.setVerticalSpacing(4);
		setLayoutManager(layout);
		label.setForegroundColor(ColorConstants.black);
		add(label, BorderLayout.CENTER);

		// Lazily instanciate the shared colors
		if (initialFgColor == null) {
			Device device = Display.getCurrent();
			initialFgColor = new Color(device, 170, 170, 170);
			initialBgColor = new Color(device, 250, 250, 250);
			normalFgColor = new Color(device, 175, 175, 149);
			normalBgColor = new Color(device, 255, 255, 229);
			finalFgColor = new Color(device, 149, 175, 151);
			finalBgColor = new Color(device, 229, 255, 231);
			shadowColor = new Color(device, 230, 230, 230);
		}

		// Init
		// setInitialState(initialState);
		// setFinalState(finalState);
		setStateElement(s);
	}

	public void setStateElement(StateElement se) {
		initialState = "init".equals(se.getId());
		finalState = "null".equals(se.getId());

		if (finalState) {
			label.setText("Finished");
		} else {
			label.setText(se.getId());
		}

		if (initialState) {
			fgColor = initialFgColor;
			bgColor = initialBgColor;
		} else {
			if (finalState) {
				fgColor = finalFgColor;
				bgColor = finalBgColor;
			} else {
				fgColor = normalFgColor;
				bgColor = normalBgColor;

			}
		}
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		// Inits
		super.paintFigure(graphics);
		Rectangle bounds = getBounds().getCopy().resize(-9, -9).translate(4, 4);
		final int round = 25;
		final int sround = 30;

		// Shadow / experimental
		graphics.setBackgroundColor(shadowColor);
		graphics.fillRoundRectangle(bounds.getTranslated(4, 4), sround, sround);

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
