package com.dexels.navajo.tipi.swing.calendar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.miginfocom.beans.ActivityAShapeBean;
import com.miginfocom.beans.DateAreaBean;
import com.miginfocom.beans.DateHeaderBean;
import com.miginfocom.beans.GridDimensionLayoutBean;
import com.miginfocom.calendar.header.CellDecorationRow;
import com.miginfocom.calendar.layout.ActivityLayout;
import com.miginfocom.calendar.layout.FlexGridLayout;
import com.miginfocom.calendar.layout.TimeBoundsLayout;
import com.miginfocom.util.dates.BoundaryRounder;
import com.miginfocom.util.dates.DateFormatList;
import com.miginfocom.util.dates.DateRangeI;
import com.miginfocom.util.gfx.geometry.AbsRect;
import com.miginfocom.util.gfx.geometry.SizeSpec;
import com.miginfocom.util.gfx.geometry.numbers.AtEnd;
import com.miginfocom.util.gfx.geometry.numbers.AtFixed;
import com.miginfocom.util.gfx.geometry.numbers.AtFraction;
import com.miginfocom.util.gfx.geometry.numbers.AtStart;
import com.miginfocom.util.repetition.DefaultRepetition;

/**
 * Overrides updateUI() to reset certain properties
 * @author Dexels
 *
 */
public class DateAreaImpl extends DateAreaBean{
	
	private static final long serialVersionUID = 18915125586093099L;

	private final static Logger logger = LoggerFactory
			.getLogger(DateAreaImpl.class);

	private ActivityAShapeBean myShape = null;
	
	public DateAreaImpl()
	{
		super();
		initialize();
	}
	
	private void initialize()
	{
		//define base colors and fonts
		Color windowBackground = getLafColor("window");
		Color gridLines = getLessExtremeColor(windowBackground, windowBackground);
		Color dividerLine = getLessExtremeColor(gridLines, windowBackground);
		Color activityTextColor = getBrightness(windowBackground) < 128 ? Color.WHITE : Color.BLACK; 
		Color textColor = getLafColor("Label.foreground");
		Font  labelFont = getLafFont("Label.font");

		setActivityLayouts(new ActivityLayout[] {
				new TimeBoundsLayout(new AtFixed(1.0f), new AtStart(0.0f), new AtEnd(0.0f), new AtStart(14.0f), new AtEnd(0.0f), 0, new AtFixed(12.0f), new AtFixed(8.0f), null, new String[] {"TimeBounds"}, new BoundaryRounder(DateRangeI.RANGE_TYPE_DAY, true, true, false, null, null, new Integer(0))),	
	            new FlexGridLayout(new AtStart(0.0f), new AtStart(1.0f), true, true, new Integer(1), null, new Integer(13), 1, 0, new AbsRect(new AtStart(0.0f), new AtStart(14.0f), new AtEnd(0.0f), new AtEnd(0.0f), null, null, null), new String[] {"FlexGrid"})
			});
		setActivityPaintContext("week");
		setHorizontalGridLineShowFirst(true);
		setHorizontalGridLineShowLast(true);
		setVerticalGridLineShowFirst(true);
		setVerticalGridLineShowLast(true);
		setLayoutOptimizeBoundary(DateRangeI.RANGE_TYPE_WEEK);
		setDividerPaint(dividerLine);
		setLabelDateFormat("d");
		setLabelPlaceRect(new AbsRect(new AtStart(0.0f), new AtStart(2.0f), new AtEnd(-3.0f), new AtStart(15.0f), null, null, null));
		setLabelAlignX(new AtFraction(1.0f));
		setLabelAlignY(new AtStart(0.0f));
		setLabelFont(labelFont.deriveFont(9F));
		setLabelForeground(textColor);
		setBackground(windowBackground);
		setHorizontalGridLinePaintEven(gridLines);
		setHorizontalGridLinePaintOdd(gridLines);
		setVerticalGridLinePaintEven(gridLines);
		setVerticalGridLinePaintOdd(gridLines);
		
		myShape = new ActivityAShapeBean();
		myShape.setBackgroundPlaceRect(new AbsRect(new AtStart(0.0f), new AtStart(0.0f), new AtEnd(0.0f), new AtEnd(0.0f), null, null, null));
		myShape.setCornerRadius(16.0);
		myShape.setOutlinePaint(dividerLine);
		myShape.setOutlineStrokeWidth(0.0F);
		myShape.setPaintContext("week");
		myShape.setResizeHandles(SwingConstants.HORIZONTAL);
		myShape.setShapeNamePrefix("month_");
		myShape.setTextFont(labelFont.deriveFont(9F)); 
		myShape.setTextTemplate("");
		myShape.setTitleAlignX(new AtStart(5.0f));
		myShape.setTitleAlignY(new AtStart(0.0f));
		myShape.setTitleFont(labelFont.deriveFont(9F));
		myShape.setTitleForeground(activityTextColor);
		myShape.setTitlePlaceRect(new AbsRect(new AtStart(0.0f), new AtStart(0.0f), new AtEnd(0.0f), new AtEnd(0.0f), null, null, null));
		myShape.setTitleTemplate("$summary$");
		myShape.setBackground(gridLines);

		GridDimensionLayoutBean weekLayout = new GridDimensionLayoutBean();
	    weekLayout.setRowSizeNormal(new SizeSpec(new AtFixed(80.0f), null, null));

		setSecondaryDimensionLayout(weekLayout);

		DateHeaderBean weekDateHeader = new DateHeaderBean();
	    weekDateHeader.setHeaderRows(new CellDecorationRow[] {
	            new CellDecorationRow(
	                DateRangeI.RANGE_TYPE_CUSTOM,
	                new DateFormatList("MMMM yyyy", null),
	                new AtFixed(25.0f),
	                new AbsRect(new AtStart(0.0f), new AtStart(0.0f), new AtEnd(0.0f), new AtEnd(0.0f), null, null, null),
	                (Paint[]) null,
	                new Paint[] {textColor},
	                null,
	                new Font[] {labelFont},
	                new Integer[] {null},
	                new AtFraction(0.5f),
	                new AtFraction(0.5f)),
	            new CellDecorationRow(
	                DateRangeI.RANGE_TYPE_DAY,
	                new DateFormatList("EEEE", null),
	                new AtFixed(21.0f),
	                new AbsRect(new AtStart(0.0f), new AtStart(0.0f), new AtEnd(0.0f), new AtEnd(0.0f), null, null, null),
	                (Paint[]) null,
	                new Paint[] {textColor},
	                new DefaultRepetition(0, 1, null, null),
	                new Font[] {labelFont},
	                new Integer[] {null},
	                new AtFraction(0.5f),
	                new AtFraction(0.5f))});
		
		setNorthDateHeader(weekDateHeader);
		
	}
	
	private Font getLafFont(String key)
	{
		Font font = UIManager.getFont(key);
		if (font == null)
		{  // slightly random default
			logger.warn("Can't get font with key " + key + " from the laf, using hardcoded default.");
			font = new Font("SansSerif", 0, 10);
		}
		return font;
	}
	
	private Color getLafColor(String key)
	{
		Color color = UIManager.getColor(key);
		if (color == null)
		{  // slightly random default
			logger.warn("Can't get color with key " + key + " from the laf, using hardcoded default.");
			color = new Color(48, 48, 48);
		}
		return color;
	}
	
	/*
	 * Returns either a darker or a more lighter version of the colorToChange, base on whether the baseColor is more like white or black.
	 * If more like white - darker and the other way around.
	 */
	private Color getLessExtremeColor(Color colorToChange, Color baseColor)
	{
		logger.info("Original color: " + colorToChange + ", red: " + colorToChange.getRed() + ", green: " + colorToChange.getGreen() + ", blue: " + colorToChange.getBlue());
		logger.info("Original brightness: " + getBrightness(colorToChange) + ", darker bright: " + getBrightness(colorToChange.darker()) + ", brighter bright: " + getBrightness(colorToChange.brighter())); 
		if (getBrightness(baseColor) < 384)
		{
			return brightenColor(colorToChange, 0.25f);
		}
		else
		{
			return darkenColor(colorToChange, 0.25f);
		}
		
	}
	
	/**
	 * Brightens the given color by the given fraction, having a bigger effect on black than on light gray
	 * f should be between 0.0 and 1.0 (1.0 results in white regardless of the given color)
	 */
	private Color brightenColor(Color c, float f)
	{
		int r = 255 - ((int) Math.round((255-c.getRed()) * (1-f)));
		int b = 255 - ((int) Math.round((255-c.getBlue()) * (1-f)));
		int g = 255 - ((int) Math.round((255-c.getGreen()) * (1-f)));
		return new Color(r, g, b, c.getAlpha());
	}
	
	/**
	 * Darkens the given color by the given fraction, having a bigger effect on white than on dark gray
	 * f should be between 0.0 and 1.0 (1.0 results in black regardless of the given color)
	 */
	private Color darkenColor(Color c, float f)
	{
		int r = (int) Math.round(c.getRed() * (1 - f));
		int b = (int) Math.round(c.getBlue() * (1 - f));
		int g = (int) Math.round(c.getGreen() * (1 - f));
		return new Color(r, g, b, c.getAlpha());
	}
	
	private Integer getBrightness(Color c)
	{
		return c.getBlue() +  c.getGreen() + c.getRed();
	}
}
